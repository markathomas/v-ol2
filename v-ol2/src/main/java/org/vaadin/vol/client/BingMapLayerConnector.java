package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.BingMapLayer;
import org.vaadin.vol.client.ui.VBingMapLayer;

@Connect(BingMapLayer.class)
public class BingMapLayerConnector extends AbstractComponentConnector {

    @Override
    public BingMapLayerState getState() {
        return (BingMapLayerState)super.getState();
    }

    @Override
    public VBingMapLayer createWidget() {
        return GWT.create(VBingMapLayer.class);
    }

    @Override
    public VBingMapLayer getWidget() {
        return (VBingMapLayer)super.getWidget();
    }
}
