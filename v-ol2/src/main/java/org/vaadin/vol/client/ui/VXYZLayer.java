package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.XYZLayer;

public class VXYZLayer extends VAbstracMapLayer<XYZLayer> {

    private String uri;
    private boolean spheriacalMercator;

    @Override
    XYZLayer createLayer() {
        return XYZLayer.create(getDisplayName(), uri, spheriacalMercator, getAttribution());
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSpheriacalMercator() {
        return this.spheriacalMercator;
    }

    public void setSpheriacalMercator(boolean spheriacalMercator) {
        this.spheriacalMercator = spheriacalMercator;
    }
}
