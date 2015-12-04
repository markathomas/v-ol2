package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.client.ui.VGoogleStreetMapLayer;

@Connect(GoogleStreetMapLayer.class)
public class GoogleStreetMapLayerConnector extends AbstractComponentConnector {

    @Override
    public VGoogleStreetMapLayer createWidget() {
        return GWT.create(VGoogleStreetMapLayer.class);
    }

    @Override
    public VGoogleStreetMapLayer getWidget() {
        return (VGoogleStreetMapLayer)super.getWidget();
    }

    @Override
    public GoogleMapLayerState getState() {
        return (GoogleMapLayerState)super.getState();
    }
}
