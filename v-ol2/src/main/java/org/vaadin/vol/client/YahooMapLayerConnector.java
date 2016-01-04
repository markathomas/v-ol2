package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.YahooMapLayer;
import org.vaadin.vol.client.ui.VYahooMapLayer;

@Connect(YahooMapLayer.class)
public class YahooMapLayerConnector extends LayerBaseConnector {

    @Override
    public YahooMapLayerState getState() {
        return (YahooMapLayerState)super.getState();
    }

    @Override
    public VYahooMapLayer getWidget() {
        return (VYahooMapLayer)super.createWidget();
    }
}
