package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.VirtualEarthMapLayer;
import org.vaadin.vol.client.ui.VVirtualEarthMapLayer;

@Connect(VirtualEarthMapLayer.class)
public class VirtualEarthMapLayerConnector extends AbstractComponentConnector {

    @Override
    public VirtualEarthMapLayerState getState() {
        return (VirtualEarthMapLayerState)super.getState();
    }

    @Override
    public VVirtualEarthMapLayer getWidget() {
        return (VVirtualEarthMapLayer)super.getWidget();
    }

    @Override
    public VVirtualEarthMapLayer createWidget() {
        return GWT.create(VVirtualEarthMapLayer.class);
    }
}
