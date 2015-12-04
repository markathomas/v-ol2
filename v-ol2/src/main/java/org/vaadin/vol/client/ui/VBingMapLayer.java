package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.BingMapLayer;

public class VBingMapLayer extends VAbstracMapLayer<BingMapLayer> {

    private String apiKey;
    private String type;

    @Override
    BingMapLayer createLayer() {
        return BingMapLayer.create(getDisplayName(), getApiKey(), getType());
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String getApiKey() {
        return apiKey;
    }

    private String getType() {
        return type;
    }
}
