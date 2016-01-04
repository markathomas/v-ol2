package org.vaadin.vol.client;

import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.GoogleTerrainMapLayer;
import org.vaadin.vol.client.ui.VGoogleTerrainMapLayer;

@Connect(GoogleTerrainMapLayer.class)
public class GoogleTerrainMapLayerConnector extends AbstractComponentConnector {

    @Override
    public VGoogleTerrainMapLayer getWidget() {
        return (VGoogleTerrainMapLayer)super.getWidget();
    }

    @Override
    public GoogleMapLayerState getState() {
        return (GoogleMapLayerState)super.getState();
    }
}
