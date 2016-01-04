package org.vaadin.vol.client;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.Profiler;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.client.ui.VMarkerLayer;

@Connect(MarkerLayer.class)
public class MarkerLayerConnector extends AbstractComponentContainerConnector {

    @Override
    public VMarkerLayer getWidget() {
        return (VMarkerLayer)super.getWidget();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {
        Profiler.enter("MarkerLayerConnector.onConnectorHierarchyChange");
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
        Profiler.leave("MarkerLayerConnector.onConnectorHierarchyChange");
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public MarkerLayerState getState() {
        return (MarkerLayerState)super.getState();
    }
}
