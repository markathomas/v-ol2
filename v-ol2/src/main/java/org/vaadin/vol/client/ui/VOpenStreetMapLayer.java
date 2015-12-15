package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.OpenStreetMapLayer;

public class VOpenStreetMapLayer extends VAbstracMapLayer<OpenStreetMapLayer> {

    private String url;

    @Override
    OpenStreetMapLayer createLayer() {
        if (url != null) {
            return OpenStreetMapLayer.createWithUrl(getDisplayName(), getProjection(), url);
        } else {
            return OpenStreetMapLayer.create(getDisplayName(), getProjection());
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
