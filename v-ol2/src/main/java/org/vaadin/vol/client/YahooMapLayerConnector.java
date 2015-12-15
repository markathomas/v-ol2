package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.YahooMapLayer;
import org.vaadin.vol.client.ui.VYahooMapLayer;

@Connect(YahooMapLayer.class)
public class YahooMapLayerConnector extends AbstractComponentConnector {

    @Override
    public YahooMapLayerState getState() {
        return (YahooMapLayerState)super.getState();
    }

    @Override
    public VYahooMapLayer getWidget() {
        return (VYahooMapLayer)super.createWidget();
    }

    @Override
    public VYahooMapLayer createWidget() {
        return GWT.create(VYahooMapLayer.class);
    }
}
