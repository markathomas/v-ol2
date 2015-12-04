/**
 *
 */
package org.vaadin.vol;

import java.util.Objects;

import org.vaadin.vol.client.WebFeatureServiceLayerState;

public class WebFeatureServiceLayer extends AbstractAutoPopulatedVectorLayer implements Layer {

    @Override
    public WebFeatureServiceLayerState getState() {
        return (WebFeatureServiceLayerState)super.getState();
    }

    public void setUri(String uri) {
        if (!Objects.equals(getState().uri, uri)) {
            this.getState().uri = uri;
            markAsDirty();
        }
    }

    public String getUri() {
        return this.getState().uri;
    }

    public void setFeatureType(String featureType) {
        if (!Objects.equals(getState().featureType, featureType)) {
            this.getState().featureType = featureType;
            markAsDirty();
        }
    }

    public String getFeatureType() {
        return this.getState().featureType;
    }

    public void setFeatureNS(String ns) {
        if (!Objects.equals(getState().featureNS, ns)) {
            this.getState().featureNS = ns;
            markAsDirty();
        }
    }

    public String getFeatureNS() {
        return this.getState().featureNS;
    }
}
