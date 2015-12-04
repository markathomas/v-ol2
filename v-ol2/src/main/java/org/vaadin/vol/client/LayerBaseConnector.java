package org.vaadin.vol.client;

import com.google.gwt.core.client.JsArray;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;

import org.vaadin.vol.client.ui.VAbstracMapLayer;
import org.vaadin.vol.client.wrappers.GwtOlHandler;

public abstract class LayerBaseConnector extends AbstractComponentConnector {

    private final LayerBaseServerRpc layerBaseServerRpc = RpcProxy.create(LayerBaseServerRpc.class, this);

    private GwtOlHandler loadStartHandler;
    private GwtOlHandler loadEndHandler;
    private GwtOlHandler visibilityChangedHandler;

    @Override
    public VAbstracMapLayer getWidget() {
        return (VAbstracMapLayer)super.getWidget();
    }

    @Override
    public LayerBaseState getState() {
        return (LayerBaseState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        this.getWidget().attachLayerToMap();
    }

    @OnStateChange("hasLoadStartHandlers")
    void hasLoadStartHandlers() {
        if (getState().hasLoadStartListeners && this.loadStartHandler == null) {
            this.loadStartHandler = new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    layerBaseServerRpc.loadStarted(getState().displayName);
                }
            };
            this.getWidget().getLayer().registerHandler("loadstart", this.loadStartHandler);
        }
    }

    @OnStateChange("hasLoadEndHandlers")
    void hasLoadEndHandlers() {
        if (getState().hasLoadEndListeners && this.loadEndHandler == null) {
            this.loadEndHandler = new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    layerBaseServerRpc.loadEnded(getState().displayName);
                }
            };
            this.getWidget().getLayer().registerHandler("loadend", this.loadEndHandler);
        }
    }

    @OnStateChange("hasVisibilityChangedHandlers")
    void hasVisibilityChangedHandlers() {
        if (getState().hasVisibilityChangedListeners && this.visibilityChangedHandler == null) {
            this.visibilityChangedHandler = new GwtOlHandler() {
                @SuppressWarnings("rawtypes")
                public void onEvent(JsArray arguments) {
                    layerBaseServerRpc.visibilityChanged(getState().displayName, getWidget().getLayer().isVisible());
                }
            };
            this.getWidget().getLayer().registerHandler("visibilitychanged", this.visibilityChangedHandler);
        }
    }
}

