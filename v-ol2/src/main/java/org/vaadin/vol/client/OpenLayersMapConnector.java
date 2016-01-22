package org.vaadin.vol.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.Profiler;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.client.ui.Action;
import com.vaadin.client.ui.ActionOwner;
import com.vaadin.client.ui.PostLayoutListener;
import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.Connect;

import java.util.logging.Logger;

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.client.ui.VLayer;
import org.vaadin.vol.client.ui.VOpenLayersMap;
import org.vaadin.vol.client.wrappers.Bounds;
import org.vaadin.vol.client.wrappers.GwtOlHandler;
import org.vaadin.vol.client.wrappers.JsObject;
import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.Pixel;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.layer.Layer;


@Connect(OpenLayersMap.class)
public class OpenLayersMapConnector extends AbstractComponentContainerConnector implements PostLayoutListener, ActionOwner {

    private final transient Logger logger = Logger.getLogger(getClass().getName());

    private final OpenLayersMapServerRpc openLayersMapServerRpc = RpcProxy.create(OpenLayersMapServerRpc.class, this);

    private GwtOlHandler extentChangeListener;
    private GwtOlHandler clickListener;
    private GwtOlHandler changeBaseLayer;

    private boolean initialized = false;
    private LonLat clickedLonLat;

    @Override
    protected void init() {
        super.init();
        getWidget().addDomHandler(new ContextMenuHandler() {
            public void onContextMenu(ContextMenuEvent event) {
                handleContextMenu(event);
            }
        }, ContextMenuEvent.getType());
    }

    private void handleContextMenu(ContextMenuEvent event) {
        if (!getState().actions.isEmpty()) {
            clickedLonLat = getWidget().getMap().getLonLatFromPixel(
                    Pixel.create(getWidget().getMapClickLeftPosition(event),
                            getWidget().getMapClickTopPosition(event)));
            Projection projection = getWidget().getMap().getBaseLayer().getProjection();
            Projection apiProjection = getWidget().getProjection();
            clickedLonLat.transform(projection, apiProjection);
            getConnection().getContextMenu().showAt(this,
                    getWidget().getWindowClickLeftPosition(event),
                    getWidget().getWindowClickTopPosition(event));

            event.stopPropagation();
            event.preventDefault();
        }
    }

    @Override
    public VOpenLayersMap getWidget() {
        return (VOpenLayersMap)super.getWidget();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
        Profiler.enter("OpenLayersMapConnector.onConnectorHierarchyChange");
        Profiler.enter(
                "OpenLayersMapConnector.onConnectorHierarchyChange add children");

        getWidget().getFakePaintables().clear();
        for (ComponentConnector child : getChildComponents()) {
            getWidget().getFakePaintables().add(child.getWidget());
        }
        Profiler.leave(
                "OpenLayersMapConnector.onConnectorHierarchyChange add children");
        Profiler.leave("OpenLayersMapConnector.onConnectorHierarchyChange");
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public OpenLayersMapState getState() {
        return (OpenLayersMapState)super.getState();
    }

    @Override
    public void onStateChanged(final StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        final boolean hasListeners = getState().registeredEventListeners != null;
        if (hasListeners && hasEventListener("extentChange")) {
            if (this.extentChangeListener == null) {
                this.extentChangeListener = new GwtOlHandler() {
                    @SuppressWarnings("rawtypes")
                    public void onEvent(JsArray arguments) {

                        Map map = getWidget().getMap();
                        int zoom = map.getZoom();
                        Bounds extent = map.getExtent();
                        if (extent == null) {
                            logger.info(" extent null");
                            return;
                        }
                        Projection projection = map.getProjection();
                        extent.transform(projection, getWidget().getProjection());

                        org.vaadin.vol.client.Bounds b = new org.vaadin.vol.client.Bounds(
                          new Point(extent.getLeft(), extent.getBottom()),
                          new Point(extent.getRight(), extent.getTop())
                        );
                        openLayersMapServerRpc.extentChanged(b, zoom);
                    }
                };
                getWidget().getMap().registerEventHandler("moveend", extentChangeListener);
            }
        }

        if (hasListeners && hasEventListener("baseLayerChange")) {
            if (changeBaseLayer == null) {
                changeBaseLayer = new GwtOlHandler() {
                    public void onEvent(JsArray arguments) {
                        Layer baseLayer = getWidget().getMap().getBaseLayer();
                        for (Connector c : getState().layers) {
                            Widget widget = ((ComponentConnector)c).getWidget();
                            if (widget instanceof VLayer) {
                                VLayer vlayer = (VLayer)widget;
                                if (baseLayer == vlayer.getLayer()) {
                                    openLayersMapServerRpc.baseLayerChanged(c.getConnectorId());
                                    return;
                                }
                            }
                        }
                    }
                };
                getWidget().getMap().registerEventHandler("changebaselayer", changeBaseLayer);
            }
        }

        if (hasListeners && hasEventListener("click")) {
            if (clickListener == null) {
                clickListener = new GwtOlHandler() {

                    public void onEvent(JsArray arguments) {
                        JsObject event = arguments.get(0).cast();
                        Pixel pixel = event.getFieldByName("xy").cast();
                        Map map = getWidget().getMap();
                        LonLat lonlat = map.getLonLatFromPixel(pixel);
                        // TODO : we better create a mechanism to define base
                        // projection in this class according to our base layer
                        // selection
                        //Projection sourceProjection = Projection.get("EPSG:900913");
                        //lonlat.transform(sourceProjection, getWidget().getProjection());
                        PointInformation pi = new PointInformation();
                        pi.setX(pixel.getX());
                        pi.setY(pixel.getY());
                        pi.setHeight(map.getOffsetHeight());
                        pi.setWidth(map.getOffsetWidth());
                        pi.setLon(lonlat.getLon());
                        pi.setLat(lonlat.getLon());
                        openLayersMapServerRpc.mapClicked(pi);
                    }
                };
                getWidget().getMap().registerEventHandler("click", clickListener);
            }
        }

        if (stateChangeEvent.hasPropertyChanged("jsMapOptions")) {
            getWidget().getMap().setMapInitOptions(getState().jsMapOptions);
        }

        if (stateChangeEvent.hasPropertyChanged("controls")) {
            getWidget().updateControls(getState().controls);
        }

        if (stateChangeEvent.hasPropertyChanged("restrictedExtent") && getState().restrictedExtent != null) {
            Bounds bounds = Bounds.create(
              getState().restrictedExtent.getLeft(),
              getState().restrictedExtent.getBottom(),
              getState().restrictedExtent.getRight(),
              getState().restrictedExtent.getTop()
            );
            Map map = getWidget().getMap();
            bounds.transform(getWidget().getProjection(), map.getProjection());
            map.setRestrictedExtent(bounds);
        }

        if (stateChangeEvent.hasPropertyChanged("baseLayer") && getState().baseLayer != null) {
            VLayer layer = (VLayer)(((ComponentConnector)getState().baseLayer).getWidget());
            getWidget().getMap().setBaseLayer(layer.getLayer());
        }

        if (stateChangeEvent.hasPropertyChanged("projection")) {
            getWidget().setProjection(Projection.get(getState().projection));
        }

        if (stateChangeEvent.hasPropertyChanged("zoomToExtent") && getState().zoomToExtent != null) {
            getWidget().zoomToExtent(Bounds.create(
              getState().zoomToExtent.getLeft(),
              getState().zoomToExtent.getBottom(),
              getState().zoomToExtent.getRight(),
              getState().zoomToExtent.getTop()
            ));
        } else if (stateChangeEvent.hasPropertyChanged("zoom") || stateChangeEvent.hasPropertyChanged("center")) {
            LonLat center = null;
            if (stateChangeEvent.hasPropertyChanged("center") && getState().center != null) {
                center = LonLat.create(getState().center.getLon(), getState().center.getLat());
            }
            getWidget().updateZoomAndCenter(getState().zoom, center);
        }
    }

    @Override
    public void postLayout() {
        if (!initialized) {
            // make the map visible initially. Most like not correct fix
            // without this double clicking to map is needed to make it visible
            LonLat center = null;
            if (getState().center != null) {
                center = LonLat.create(getState().center.getLon(), getState().center.getLat());
            }
            getWidget().updateZoomAndCenter(getState().zoom, center);
            initialized  = true;
        }
    }

    @Override
    public Action[] getActions() {
        if (getState().actions.isEmpty()) {
            return new Action[0];
        } else {
            final Action[] actions = new Action[getState().actions.size()];
            for (int i = 0; i < getState().actions.size(); i++) {
                OpenLayersMapAction action = new OpenLayersMapAction(this, getState().actions.get(i));
                actions[i] = action;
            }
            return actions;
        }
    }

    @Override
    public ApplicationConnection getClient() {
        return getConnection();
    }

    @Override
    public String getPaintableId() {
        return getConnectorId();
    }

    private class OpenLayersMapAction extends Action {

        private final ContextMenuAction contextMenuAction;

        public OpenLayersMapAction(ActionOwner owner, ContextMenuAction contextMenuAction) {
            super(owner);
            this.contextMenuAction = contextMenuAction;
            setCaption(contextMenuAction.caption);
            setIconUrl(getResourceUrl(Constants.CONTEXT_MENU_ICON_RESOURCE_KEY + contextMenuAction.key));
        }

        @Override
        public void execute() {
            String key = contextMenuAction.key;
            Point point = new Point(clickedLonLat.getLon(), clickedLonLat.getLat());
            openLayersMapServerRpc.contextMenuClicked(key, point);
            getClient().getContextMenu().hide();
        }
    }
}
