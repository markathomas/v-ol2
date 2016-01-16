package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.ValueMap;
import com.vaadin.shared.Connector;
import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.VectorLayerServerRpc;
import org.vaadin.vol.client.VectorLayerState;
import org.vaadin.vol.client.wrappers.*;
import org.vaadin.vol.client.wrappers.control.Control;
import org.vaadin.vol.client.wrappers.control.DrawFeature;
import org.vaadin.vol.client.wrappers.control.ModifyFeature;
import org.vaadin.vol.client.wrappers.control.SelectFeature;
import org.vaadin.vol.client.wrappers.geometry.Geometry;
import org.vaadin.vol.client.wrappers.geometry.LineString;
import org.vaadin.vol.client.wrappers.geometry.Point;
import org.vaadin.vol.client.wrappers.handler.PathHandler;
import org.vaadin.vol.client.wrappers.handler.PointHandler;
import org.vaadin.vol.client.wrappers.handler.PolygonHandler;
import org.vaadin.vol.client.wrappers.handler.RegularPolygonHandler;
import org.vaadin.vol.client.wrappers.layer.VectorLayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class VVectorLayer extends FlowPanel implements VLayer {

    private VectorLayer vectors;
    private VectorLayerState.DrawingMode drawingMode = VectorLayerState.DrawingMode.NONE;
    private Control df;
    private GwtOlHandler _fAddedListener;
    private boolean updating;
    private VectorLayerServerRpc vectorLayerServerRpc;
    private VectorLayerState vectorLayerState;
    private String displayName;
    private GwtOlHandler _fModifiedListener;

    private Vector lastNewDrawing;
    private boolean added;
    private VectorLayerState.SelectionMode currentSelectionMode;
    private SelectFeature selectFeature;
    private String selectionCtrlId;             // Common SelectFeature control identifier

    public VectorLayer getLayer() {
        if (vectors == null) {
            vectors = VectorLayer.create(displayName);
            vectors.registerHandler("featureadded", getFeatureAddedListener());
            vectors.registerHandler("featuremodified", getFeatureModifiedListener());
            /*vectors.registerHandler("afterfeaturemodified", new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    if (updating) {
                        // ignore selections that happend during update, those
                        // should be already known and notified by the server
                        // side
                        return;
                    }
                    client.sendPendingVariableChanges();
                }
            });*/
            vectors.registerHandler("featureselected", new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    if (updating) {
                        // ignore selections that happend during update, those
                        // should be already known and notified by the server
                        // side
                        return;
                    }
                    if (vectorLayerState.registeredEventListeners.contains("vsel")) {
                        ValueMap javaScriptObject = arguments.get(0).cast();
                        Vector vector = javaScriptObject.getValueMap("feature").cast();
                        vectorLayerServerRpc.select(getConnectorIdForVector(vector));
                    }
                }
            });

            vectors.registerHandler("featureunselected", new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    ValueMap javaScriptObject = arguments.get(0).cast();
                    Vector vector = javaScriptObject.getValueMap("feature").cast();
                    for (Connector c : vectorLayerState.vectors) {
                        Widget w = ((ComponentConnector)c).getWidget();
                        VAbstractVector v = (VAbstractVector)w;
                        if (v.getVector() == vector) {
                            v.revertDefaultIntent();
                            // ignore selections that happened during update, those
                            // should be already known and notified by the server
                            // side
                            if (!updating && vectorLayerState.registeredEventListeners.contains("vusel")) {
                                vectorLayerServerRpc.unselect(c.getConnectorId());
                                break;
                            }
                        }
                    }
                }
            });

        }
        return vectors;
    }

    private String getConnectorIdForVector(Vector vector) {
        for (Connector c : vectorLayerState.vectors) {
            Widget w = ((ComponentConnector)c).getWidget();
            VAbstractVector v = (VAbstractVector)w;
            if (v.getVector() == vector) {
                return c.getConnectorId();
            }
        }
        return null;
    }

    private GwtOlHandler getFeatureModifiedListener() {
        if (_fModifiedListener == null) {
            _fModifiedListener = new GwtOlHandler() {

                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    if (!updating && drawingMode != VectorLayerState.DrawingMode.NONE) {
                        // use vaadin js object helper to get the actual feature
                        // from ol event
                        // TODO improve typing of OL events
                        JsObject event = arguments.get(0).cast();
                        Vector feature = event.getFieldByName("feature").cast();
                        Geometry geometry = feature.getGeometry();

                        boolean isLineString = true; // TODO
                        if (isLineString) {
                            LineString ls = geometry.cast();
                            JsArray<Point> allVertices = ls.getAllVertices();
                            String[] points = new String[allVertices.length()];
                            for (int i = 0; i < allVertices.length(); i++) {
                                Point point = allVertices.get(i);
                                point = point.nativeClone();
                                point.transform(getMap().getProjection(), getProjection());
                                points[i] = point.toString();
                            }
                            // VConsole.log("modified");
                            // communicate points to server and mark the
                            // new geometry to be removed on next update.

                            Vector modifiedFeature = ((ModifyFeature)df.cast()).getModifiedFeature();
                            if (modifiedFeature != null) {
                                vectorLayerServerRpc.modify(points, getConnectorIdForVector(modifiedFeature));
                            }

                            // client.sendPendingVariableChanges();
                            // lastNewDrawing = feature;
                        }
                    }
                }
            };
        }
        return _fModifiedListener;
    }

    private GwtOlHandler getFeatureAddedListener() {
        if (_fAddedListener == null) {
            _fAddedListener = new GwtOlHandler() {

                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    if (!updating && drawingMode != VectorLayerState.DrawingMode.NONE) {
                        // use vaadin js object helper to get the actual feature
                        // from ol event
                        // TODO improve typing of OL events
                        JsObject event = arguments.get(0).cast();
                        Vector feature = event.getFieldByName("feature").cast();
                        Geometry geometry = feature.getGeometry();

                        if (drawingMode == VectorLayerState.DrawingMode.AREA
                          || drawingMode == VectorLayerState.DrawingMode.LINE
                          || drawingMode == VectorLayerState.DrawingMode.RECTANGLE
                          || drawingMode == VectorLayerState.DrawingMode.CIRCLE) {
                            LineString ls = geometry.cast();
                            JsArray<Point> allVertices = ls.getAllVertices();
                            String[] points = new String[allVertices.length()];
                            for (int i = 0; i < allVertices.length(); i++) {
                                Point point = allVertices.get(i);
                                point.transform(getMap().getProjection(), getProjection());
                                points[i] = point.toString();
                            }
                            vectorLayerServerRpc.draw(points, drawingMode);
                        } else if (drawingMode == VectorLayerState.DrawingMode.POINT) {
                            // point
                            Point point = geometry.cast();
                            point.transform(getMap().getProjection(),
                                    getProjection());
                            double x = point.getX();
                            double y = point.getY();
                            String[] points = new String[] { Point.create(x, y).toString() };
                            vectorLayerServerRpc.draw(points, drawingMode);
                        }
                        // VConsole.log("drawing done");
                        // communicate points to server and mark the
                        // new geometry to be removed on next update.
                        if (drawingMode != VectorLayerState.DrawingMode.MODIFY) {
                            lastNewDrawing = feature;
                        }
                    }
                }
            };
        }
        return _fAddedListener;
    }

    public void update(VectorLayerState state, VectorLayerServerRpc vectorLayerServerRpc) {
        this.updating = true;
        this.displayName = state.displayName;
        this.vectorLayerServerRpc = vectorLayerServerRpc;
        this.vectorLayerState = state;

        if (!this.added) {
            getMap().addLayer(getLayer());
            this.added = true;
        }

        // Last new drawing only visible to next update. If used by server side
        // handler, we probably have it as a child component
        if (lastNewDrawing != null) {
            getLayer().removeFeature(lastNewDrawing);
            lastNewDrawing = null;
        }

        updateStyleMap(state.styleMap, state.uniqueValueRules, state.extendDefault);
        setDrawingMode(state.drawingMode);

        // Identifier for SelectFeature control to use ... layers specifying the
        // the same identifier can all listen for their own Select events on the map.
        selectionCtrlId = state.selectionCtrlId;

        setSelectionMode();

        HashSet<Widget> orphaned = new HashSet<Widget>();
        for (Iterator<Widget> iterator = iterator(); iterator.hasNext();) {
            orphaned.add(iterator.next());
        }

        int childCount = state.vectors.size();
        for (int i = 0; i < childCount; i++) {
            VAbstractVector vector = (VAbstractVector) ((ComponentConnector) state.vectors.get(i)).getWidget();
            boolean isNew = !hasChildComponent(vector);
            if (isNew) {
                add(vector);
            }
            //vector.updateFromUIDL(childUIDL, client);
            orphaned.remove(vector);
        }
        for (Widget widget : orphaned) {
            widget.removeFromParent();
        }
        updating = false;
    }

    private void setSelectionMode() {
        VectorLayerState.SelectionMode newSelectionMode = this.vectorLayerState.selectionMode;
        if (currentSelectionMode != newSelectionMode) {
            if (selectFeature != null) {
                /* remove this layer from the SelectFeature instead of removing the control
                selectFeature.deActivate();
                getMap().removeControl(selectFeature);
                */
                SelectFeatureFactory.getInst().removeLayer(selectFeature, selectionCtrlId, getMap(), vectors);
                selectFeature = null;
            }

            if (newSelectionMode != VectorLayerState.SelectionMode.NONE) {
                /* delegate responsibility for managing the SelectFeature to the factory;
                 * just let it know we want to register this vectorlayer in the SelectFeature.
                selectFeature = SelectFeature.create(vectors);
                getMap().addControl(selectFeature);
                */
                selectFeature = SelectFeatureFactory.getInst().getOrCreate(selectionCtrlId, getMap(), vectors);
                selectFeature.activate();
            }

            currentSelectionMode = newSelectionMode;
        }
        if (currentSelectionMode != VectorLayerState.SelectionMode.NONE || drawingMode == VectorLayerState.DrawingMode.MODIFY) {
            if (this.vectorLayerState.selectedVector != null) {
                Scheduler.get().scheduleFinally(new ScheduledCommand() {

                    public void execute() {
                        Widget w = ((ComponentConnector)vectorLayerState.selectedVector).getWidget();
                        VAbstractVector selectedVector = (VAbstractVector)w;
                        if (selectedVector != null) {
                            updating = true;
                            // ensure selection
                            if (drawingMode == VectorLayerState.DrawingMode.MODIFY) {
                                ModifyFeature mf = (ModifyFeature) df.cast();
                                if (mf.getModifiedFeature() != null) {
                                    mf.unselect(mf.getModifiedFeature());
                                }
                                mf.select(selectedVector.getVector());
                            } else {
                                selectFeature.select(selectedVector.getVector());
                            }
                            updating = false;
                        }

                    }
                });
            } else {
                // remove selection
                if (drawingMode == VectorLayerState.DrawingMode.MODIFY) {
                    ModifyFeature modifyFeature = (ModifyFeature) df.cast();
                    if (modifyFeature.getModifiedFeature() != null) {
                        modifyFeature.unselect(modifyFeature
                                .getModifiedFeature());
                    }
                } else {
                    try {
                        selectFeature.unselectAll();

                    } catch (Exception e) {
                        // NOP, may throw exception if selected vector gets
                        // deleted
                    }
                }
            }
        }
    }

    private void setDrawingMode(VectorLayerState.DrawingMode newDrawingMode) {
        if (drawingMode != newDrawingMode) {
            if (drawingMode != VectorLayerState.DrawingMode.NONE) {
                // remove old drawing feature
                if (drawingMode == VectorLayerState.DrawingMode.MODIFY) {
                    ModifyFeature mf = df.cast();
                    if (mf.getModifiedFeature() != null) {
                        mf.unselect(mf.getModifiedFeature());
                    }
                }
                df.deActivate();
                getMap().removeControl(df);
            }
            drawingMode = newDrawingMode;
            df = null;

            if (drawingMode == VectorLayerState.DrawingMode.AREA) {
                df = DrawFeature.create(getLayer(), PolygonHandler.get());
            } else if (drawingMode == VectorLayerState.DrawingMode.LINE) {
                df = DrawFeature.create(getLayer(), PathHandler.get());
            } else if (drawingMode == VectorLayerState.DrawingMode.MODIFY) {
                df = ModifyFeature.create(getLayer());
            } else if (drawingMode == VectorLayerState.DrawingMode.POINT) {
                df = DrawFeature.create(getLayer(), PointHandler.get());
            } else if (drawingMode == VectorLayerState.DrawingMode.RECTANGLE) {
                df = DrawFeature.create(getLayer(), RegularPolygonHandler.get(), RegularPolygonHandler.getRectangleOptions());
            } else if (drawingMode == VectorLayerState.DrawingMode.CIRCLE) {
                df = DrawFeature.create(getLayer(), RegularPolygonHandler.get(), RegularPolygonHandler.getCircleOptions());
            }
            if (df != null) {
                getMap().addControl(df);
                df.activate();
            }

        }
    }

    private void updateStyleMap(java.util.Map<String, String> styleMap, List<String> uniqueValueRules, boolean extendDefault) {
        StyleMap sm = getStyleMap(styleMap, uniqueValueRules, extendDefault);
        if (sm == null) {
            sm = StyleMap.create();
        }

        getLayer().setStyleMap(sm);
    }



    public static StyleMap getStyleMap(java.util.Map<String, String> styleMap, List<String> uniqueValueRules, boolean extendDefault) {
        if (styleMap == null) {
            return null;
        }
        StyleMap sm;
        if (styleMap.size() == 1 && styleMap.get(0).equals("default")) {
            sm = StyleMap.create();
            sm.setStyle("default", Style.create(Util.parse(styleMap.get(0))));
        } else {
            sm = StyleMap.create();
            if (extendDefault) {
                sm.setExtendDefault(true);
            }
            for (java.util.Map.Entry<String, String> entry : styleMap.entrySet()) {
                Style style = Style.create(Util.parse(entry.getValue()));
                sm.setStyle(entry.getKey(), style);
            }
        }

        if (uniqueValueRules != null) {
            for (String uniqueValueRuleJson : uniqueValueRules) {
                JavaScriptObject uniqueValueRule = Util.parse(uniqueValueRuleJson);
                addUniqueValueRules(sm, uniqueValueRule);
            }
        }
        return sm;
    }

    private static native void addUniqueValueRules(StyleMap sm, JavaScriptObject uniqueValueRules) /*-{
        var intent = uniqueValueRules.intent.value
        var property = uniqueValueRules.property
        var lookup = uniqueValueRules.lookup.map

        // fix JSON structure a bit
        for (l in lookup) {
            lookup[l] = lookup[l].map
        }

        sm.addUniqueValueRules(intent, property, lookup, null)
    }-*/;

    @Override
    protected void onDetach() {
        getMap().removeLayer(getLayer());
        added = false;
        super.onDetach();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (!added && vectors != null) {
            getMap().addLayer(getLayer());
        }
    }

    private Map getMap() {
        return getVMap().getMap();
    }

    private VOpenLayersMap getVMap() {
        return MapUtil.findParent(VOpenLayersMap.class, getParent());
    }

    protected Projection getProjection() {
        return getVMap().getProjection();
    }

    public boolean hasChildComponent(Widget component) {
        return getWidgetIndex(component) != -1;
    }

    public void vectorUpdated(VAbstractVector vAbstractVector) {
        // redraw
        getLayer().drawFeature(vAbstractVector.getVector());
        if (df != null) {
            String id = df.getId();
            if (id.contains("ModifyFeature")) {
                ModifyFeature mf = df.cast();
                Vector modifiedFeature = mf.getModifiedFeature();
                if (modifiedFeature == vAbstractVector.getVector()) {
                    // VConsole.log("Whoops, modified on the server side " +
                    // "while currently being modified on the client side. " +
                    // "This may cause issues for OL unleass we notify " +
                    // "the ModifyFeature about the change.");
                    mf.resetVertices();
                }
            }
        }
    }
}
