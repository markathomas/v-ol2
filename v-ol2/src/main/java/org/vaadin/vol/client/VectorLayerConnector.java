package org.vaadin.vol.client;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.Profiler;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.VectorLayer;
import org.vaadin.vol.client.ui.VVectorLayer;

@Connect(VectorLayer.class)
public class VectorLayerConnector extends AbstractComponentContainerConnector {

    private final VectorLayerServerRpc vectorLayerServerRpc = RpcProxy.create(VectorLayerServerRpc.class, this);

    @Override
    public VVectorLayer getWidget() {
        return (VVectorLayer)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        this.getWidget().update(getState(), this.vectorLayerServerRpc);
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
        Profiler.enter("VectorLayerConnector.onConnectorHierarchyChange");
        Profiler.enter("VectorLayerConnector.onConnectorHierarchyChange add children");
        for (ComponentConnector child : getChildComponents()) {
            if (child.getParent() != this) {
                getWidget().add(child.getWidget());
            }
        }
        for (ComponentConnector child : event.getOldChildren()) {
            if (child.getParent() != this) {
                getWidget().remove(child.getWidget());
            }
        }
        Profiler.leave("VectorLayerConnector.onConnectorHierarchyChange add children");
        Profiler.leave("VectorLayerConnector.onConnectorHierarchyChange");
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public VectorLayerState getState() {
        return (VectorLayerState)super.getState();
    }
}
