package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.client.ui.VGoogleStreetMapLayer;

@Connect(GoogleStreetMapLayer.class)
public class GoogleStreetMapLayerConnector extends LayerBaseConnector {

    @Override
    public VGoogleStreetMapLayer getWidget() {
        return (VGoogleStreetMapLayer)super.getWidget();
    }

    @Override
    public GoogleMapLayerState getState() {
        return (GoogleMapLayerState)super.getState();
    }
}
