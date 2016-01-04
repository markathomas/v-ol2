package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.EmptyLayer;
import org.vaadin.vol.client.ui.VEmptyLayer;

@Connect(EmptyLayer.class)
public class EmptyLayerConnector extends WebMapServiceLayerConnector {

    @Override
    public VEmptyLayer getWidget() {
        return (VEmptyLayer)super.getWidget();
    }
}
