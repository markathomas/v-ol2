package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.BingMapLayer;
import org.vaadin.vol.client.ui.VBingMapLayer;

@Connect(BingMapLayer.class)
public class BingMapLayerConnector extends LayerBaseConnector {

    @Override
    public BingMapLayerState getState() {
        return (BingMapLayerState)super.getState();
    }

    @Override
    public VBingMapLayer getWidget() {
        return (VBingMapLayer)super.getWidget();
    }
}
