package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.WebMapServiceLayerStyled;
import org.vaadin.vol.client.ui.VWebMapServiceLayerStyled;

@Connect(WebMapServiceLayerStyled.class)
public class WebMapServiceLayerStyledConnector extends WebMapServiceLayerConnector {

    @Override
    public WebMapServiceLayerStyledState getState() {
        return (WebMapServiceLayerStyledState)super.getState();
    }

    @Override
    public VWebMapServiceLayerStyled getWidget() {
        return (VWebMapServiceLayerStyled)super.getWidget();
    }
}
