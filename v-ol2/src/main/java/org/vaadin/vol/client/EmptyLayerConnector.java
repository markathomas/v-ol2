package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.EmptyLayer;
import org.vaadin.vol.client.ui.VEmptyLayer;

@Connect(EmptyLayer.class)
public class EmptyLayerConnector extends WebMapServiceLayerConnector {

    @Override
    public VEmptyLayer createWidget() {
        return GWT.create(VEmptyLayer.class);
    }

    @Override
    public VEmptyLayer getWidget() {
        return (VEmptyLayer)super.getWidget();
    }
}
