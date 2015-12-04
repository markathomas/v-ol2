package org.vaadin.vol;

import java.util.Objects;

import org.vaadin.vol.client.WellKnownTextLayerState;

public class WellKnownTextLayer extends AbstractAutoPopulatedVectorLayer implements Layer {


    public WellKnownTextLayer() {
        setDisplayName("WKT");
    }

    public WellKnownTextLayer(String wkt) {
        this();
        setWellKnownText(wkt);
    }

    @Override
    public WellKnownTextLayerState getState() {
        return (WellKnownTextLayerState)super.getState();
    }

    public void setWellKnownText(String wkt) {
        if (!Objects.equals(getState().wkt, wkt)) {
            getState().wkt = wkt;
            markAsDirty();
        }
    }

    public String getWellKnownText() {
        return getState().wkt;
    }

}
