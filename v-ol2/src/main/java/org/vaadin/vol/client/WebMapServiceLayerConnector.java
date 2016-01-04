package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.WebMapServiceLayer;
import org.vaadin.vol.client.ui.VWebMapServiceLayer;

@Connect(WebMapServiceLayer.class)
public class WebMapServiceLayerConnector extends LayerBaseConnector {

    @Override
    public WebMapServiceLayerState getState() {
        return (WebMapServiceLayerState)super.getState();
    }

    @Override
    public VWebMapServiceLayer getWidget() {
        return (VWebMapServiceLayer)super.getWidget();
    }
}
