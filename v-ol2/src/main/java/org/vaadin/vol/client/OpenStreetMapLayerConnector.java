package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.client.ui.VOpenStreetMapLayer;

@Connect(OpenStreetMapLayer.class)
public class OpenStreetMapLayerConnector extends LayerBaseConnector {

    @Override
    public OpenStreetMapLayerState getState() {
        return (OpenStreetMapLayerState)super.getState();
    }

    @Override
    public VOpenStreetMapLayer getWidget() {
        return (VOpenStreetMapLayer)super.getWidget();
    }
}
