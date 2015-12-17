package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.WebMapServiceLayerStyled;

public class VWebMapServiceLayerStyled extends VWebMapServiceLayer
{

    private String sld;
    private WebMapServiceLayerStyled wms;

    @Override
    protected WebMapServiceLayerStyled createLayer() {
        wms = WebMapServiceLayerStyled.create(super.getDisplayName(), super.getUri(), super.getLayers(), super.getFormat(),
          super.isBaseLayer(), super.isTransparent(), super.getOpacity(), super.isSingleTile(), super.isInLayerSwitcher(),
          super.isVisibility(), sld);
        return wms;
    }

    public String getSld() {
        return this.sld;
    }

    public void setSld(String sld) {
        this.sld = sld;
    }

    @Override
    public void setOpacity(double opacity) {
        super.setOpacity(opacity);
        if (wms != null) {
            wms.setOpacity(opacity);
        }
    }
}
