package org.vaadin.vol.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.Icon;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.Marker;
import org.vaadin.vol.client.ui.VMarker;
import org.vaadin.vol.client.wrappers.GwtOlHandler;
import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Pixel;
import org.vaadin.vol.client.wrappers.Projection;

@Connect(Marker.class)
public class MarkerConnector extends AbstractComponentConnector {

    private final MarkerServerRpc markerServerRpc = RpcProxy.create(MarkerServerRpc.class, this);

    private boolean rightMouseButtonDown = false;

    @Override
    public VMarker getWidget() {
        return (VMarker)super.getWidget();
    }

    @Override
    public MarkerState getState() {
        return (MarkerState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        Icon icon = getIcon();
        getWidget().updateFromStateChange(getState(), icon);
        if (getState().registeredEventListeners != null && getState().registeredEventListeners.contains("click")) {
            getWidget().getMarker().addClickHandler(new GwtOlHandler() {
                public void onEvent(JsArray arguments) {
                    markerServerRpc.markerClicked();
                }
            });
        }

        if (getState().registeredEventListeners != null && getState().registeredEventListeners.contains(EventId.CONTEXT_CLICK)) {
            getWidget().getMarker().addRightMouseButtonDownHandler(new GwtOlHandler() {
                @Override
                public void onEvent(JsArray arguments) {
                    rightMouseButtonDown = true;
                }
            });

            MapUtil.getVMap(getWidget().getParent()).addDomHandler(new ContextMenuHandler() {
                @Override
                public void onContextMenu(ContextMenuEvent event) {
                    if (!rightMouseButtonDown) {
                        return;
                    }
                    rightMouseButtonDown = false;

                    final MouseEventDetails mouseEventDetails = MouseEventDetailsBuilder.buildMouseEventDetails(
                            event.getNativeEvent(), getWidget()
                                    .getElement());

                    event.preventDefault();
                    event.stopPropagation();
                    sendContextClickEvent(mouseEventDetails, event
                            .getNativeEvent().getEventTarget());

                }
            }, ContextMenuEvent.getType());
        }
    }

    @Override
    protected void sendContextClickEvent(MouseEventDetails details, EventTarget eventTarget) {
        LonLat clickedLonLat = MapUtil.getMap(getWidget().getParent()).getLonLatFromPixel(
                Pixel.create(details.getRelativeX(), details.getRelativeY()));
        Projection projection = MapUtil.getMap(getWidget().getParent()).getBaseLayer().getProjection();
        Projection apiProjection = MapUtil.getVMap(getWidget().getParent()).getProjection();
        clickedLonLat.transform(projection, apiProjection);
        Point point = new Point(clickedLonLat.getLon(), clickedLonLat.getLat());
        getRpcProxy(ContextClickRpc.class).contextClick(details, point);
    }
}
