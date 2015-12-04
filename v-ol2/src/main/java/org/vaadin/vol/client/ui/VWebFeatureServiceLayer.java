package org.vaadin.vol.client.ui;


import org.vaadin.vol.client.wrappers.layer.WebFeatureServiceLayer;

public class VWebFeatureServiceLayer extends VAbstractAutopopulatedVectorLayer<WebFeatureServiceLayer> {

    private String uri;
    private String featureType;
    private String featureNS;

    @Override
    WebFeatureServiceLayer createLayer() {
        if (layer == null) {
            layer = WebFeatureServiceLayer.create(getDisplayName(), uri, featureType, featureNS, getProjection(), getStyleMap());
        }
        return layer;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFeatureType() {
        return this.featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeatureNS() {
        return this.featureNS;
    }

    public void setFeatureNS(String featureNS) {
        this.featureNS = featureNS;
    }
}
