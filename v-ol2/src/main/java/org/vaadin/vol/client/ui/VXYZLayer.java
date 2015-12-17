package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.XYZLayer;

public class VXYZLayer extends VAbstracMapLayer<XYZLayer> {

    private String uri;
    private boolean sphericalMercator;

    @Override
    XYZLayer createLayer() {
        return XYZLayer.create(getDisplayName(), uri, sphericalMercator, getAttribution());
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSphericalMercator() {
        return this.sphericalMercator;
    }

    public void setSphericalMercator(boolean sphericalMercator) {
        this.sphericalMercator = sphericalMercator;
    }
}
