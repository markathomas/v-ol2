package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.Icon;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.Marker;
import org.vaadin.vol.client.ui.VMarker;
import org.vaadin.vol.client.wrappers.GwtOlHandler;

@Connect(Marker.class)
public class MarkerConnector extends AbstractComponentConnector {

    private final MarkerServerRpc markerServerRpc = RpcProxy.create(MarkerServerRpc.class, this);

    @Override
    public VMarker getWidget() {
        return (VMarker)super.getWidget();
    }

    @Override
    public VMarker createWidget() {
        return GWT.create(VMarker.class);
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
    }
}
