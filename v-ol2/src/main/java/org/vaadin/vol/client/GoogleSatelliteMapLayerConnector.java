package org.vaadin.vol.client;

import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.GoogleSatelliteMapLayer;
import org.vaadin.vol.client.ui.VGoogleSatelliteMapLayer;

@Connect(GoogleSatelliteMapLayer.class)
public class GoogleSatelliteMapLayerConnector extends AbstractComponentConnector {

    @Override
    public VGoogleSatelliteMapLayer getWidget() {
        return (VGoogleSatelliteMapLayer)super.getWidget();
    }

    @Override
    public GoogleMapLayerState getState() {
        return (GoogleMapLayerState)super.getState();
    }
}
