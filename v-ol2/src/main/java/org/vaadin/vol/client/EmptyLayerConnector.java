package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.EmptyLayer;
import org.vaadin.vol.client.ui.VEmptyLayer;
import org.vaadin.vol.client.wrappers.layer.WebMapServiceLayer;

@Connect(EmptyLayer.class)
public class EmptyLayerConnector extends WebMapServiceLayerConnector {

    @Override
    public WebMapServiceLayerState getState() {
        return (WebMapServiceLayerState)super.getState();
    }

    @Override
    public VEmptyLayer createWidget() {
        return GWT.create(VEmptyLayer.class);
    }

    @Override
    public VEmptyLayer getWidget() {
        return (VEmptyLayer)super.getWidget();
    }
}
