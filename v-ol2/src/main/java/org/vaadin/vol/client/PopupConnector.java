package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.Popup;
import org.vaadin.vol.client.ui.VPopup;
import org.vaadin.vol.client.wrappers.GwtOlHandler;

@Connect(Popup.class)
public class PopupConnector extends AbstractComponentContainerConnector {

    private final PopupServerRpc popupServerRpc = RpcProxy.create(PopupServerRpc.class, this);

    private GwtOlHandler closeEventHandler = new GwtOlHandler() {
        @SuppressWarnings("rawtypes")
        public void onEvent(JsArray arguments) {
            getWidget().hide();
            popupServerRpc.popupClosed();
        }
    };

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        this.getWidget().updatePopup(getState(), this.closeEventHandler);
    }

    @Override
    public VPopup getWidget() {
        return (VPopup)super.getWidget();
    }

    @Override
    public VPopup createWidget() {
        return GWT.create(VPopup.class);
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
        /*Profiler.enter("PopupConnector.onConnectorHierarchyChange");
        Profiler.enter("PopupConnector.onConnectorHierarchyChange add children");
        int index = 0;
        for (ComponentConnector child : getChildComponents()) {
            getWidget().addOrMove(child.getWidget(), index++);
        }
        Profiler.leave("PopupConnector.onConnectorHierarchyChange add children");

        // Detach old child widgets and possibly their caption
        Profiler.enter("PopupConnector.onConnectorHierarchyChange remove old children");
        for (ComponentConnector child : event.getOldChildren()) {
            if (child.getParent() == this) {
                // Skip current children
                continue;
            }
            getWidget().remove(child.getWidget());
        }
        Profiler.leave("PopupConnector.onConnectorHierarchyChange remove old children");
        Profiler.leave("PopupConnector.onConnectorHierarchyChange");*/
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public PopupState getState() {
        return (PopupState)super.getState();
    }
}
