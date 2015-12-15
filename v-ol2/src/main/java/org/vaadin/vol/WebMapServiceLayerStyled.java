package org.vaadin.vol;

import org.vaadin.vol.client.WebMapServiceLayerStyledState;

public class WebMapServiceLayerStyled extends WebMapServiceLayer {

    @Override
    public WebMapServiceLayerStyledState getState() {
        return (WebMapServiceLayerStyledState)super.getState();
    }

    public String getSld() {
        return getState().sld;
    }

    public void setSld(String sld) {
        this.getState().sld = sld;
        markAsDirty();
    }
}
