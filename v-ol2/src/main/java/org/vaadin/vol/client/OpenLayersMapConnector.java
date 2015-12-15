package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.Connect;

import java.util.logging.Logger;

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.Point;
import org.vaadin.vol.PointInformation;
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
public class OpenLayersMapConnector extends AbstractComponentContainerConnector {

    private final transient Logger logger = Logger.getLogger(getClass().getName());

    private final OpenLayersMapServerRpc openLayersMapServerRpc = RpcProxy.create(OpenLayersMapServerRpc.class, this);

    private GwtOlHandler extentChangeListener;
    private GwtOlHandler clickListener;
    private GwtOlHandler changeBaseLayer;

    @Override
    public VOpenLayersMap getWidget() {
        return (VOpenLayersMap)super.getWidget();
    }

    @Override
    public VOpenLayersMap createWidget() {
        return GWT.create(VOpenLayersMap.class);
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
        /*Profiler.enter("MarkerLayerConnector.onConnectorHierarchyChange");
        Profiler.enter("MarkerLayerConnector.onConnectorHierarchyChange add children");
        int index = 0;
        for (ComponentConnector child : getChildComponents()) {
            getWidget().addOrMove(child.getWidget(), index++);
        }
        Profiler.leave("MarkerLayerConnector.onConnectorHierarchyChange add children");

        // Detach old child widgets and possibly their caption
        Profiler.enter("MarkerLayerConnector.onConnectorHierarchyChange remove old children");
        for (ComponentConnector child : event.getOldChildren()) {
            if (child.getParent() == this) {
                // Skip current children
                continue;
            }
            getWidget().remove(child.getWidget());
        }
        Profiler.leave("MarkerLayerConnector.onConnectorHierarchyChange remove old children");
        Profiler.leave("MarkerLayerConnector.onConnectorHierarchyChange");*/
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public OpenLayersMapState getState() {
        return (OpenLayersMapState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getState().registeredEventListeners.contains("click");

        if (getState().registeredEventListeners.contains("extentChange")) {
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

                        org.vaadin.vol.Bounds b = new org.vaadin.vol.Bounds(
                          new Point(extent.getLeft(), extent.getBottom()),
                          new Point(extent.getRight(), extent.getTop())
                        );
                        openLayersMapServerRpc.extentChanged(b, zoom);
                    }
                };
                getWidget().getMap().registerEventHandler("moveend", extentChangeListener);
            }
        }

        if (getState().registeredEventListeners.contains("baseLayerChange")) {
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

        if (getState().registeredEventListeners.contains("click")) {
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
                        Projection sourceProjection = Projection.get("EPSG:900913");
                        lonlat.transform(sourceProjection, getWidget().getProjection());
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

        if (stateChangeEvent.hasPropertyChanged("projection")) {
            getWidget().setProjection(Projection.get(getState().projection));
        }
    }

    @OnStateChange("jsMapOptions")
    void mapOptionsChanged() {
        getWidget().getMap().setMapInitOptions(getState().jsMapOptions);
    }

    @OnStateChange("projection")
    void projectionChanged() {
        getWidget().setProjection(Projection.get(getState().projection));
    }

    @OnStateChange("restrictedExtent")
    void restrictedExtentChanged() {
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

    @OnStateChange("controls")
    void controlsChanged() {
        getWidget().updateControls(getState().controls);
    }
}
