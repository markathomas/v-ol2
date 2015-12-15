package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
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

    @Override
    public VOpenStreetMapLayer createWidget() {
        return GWT.create(VOpenStreetMapLayer.class);
    }
}
