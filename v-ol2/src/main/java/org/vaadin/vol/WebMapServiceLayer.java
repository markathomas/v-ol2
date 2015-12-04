/**
 *
 */
package org.vaadin.vol;

import org.vaadin.vol.client.WebMapServiceLayerState;

public class WebMapServiceLayer extends AbstractLayerBase implements Layer {

    @Override
    public WebMapServiceLayerState getState() {
        return (WebMapServiceLayerState)super.getState();
    }

    public String getStyles() {
        return getState().styles;
    }

    public void setStyles(String styles) {
        this.getState().styles = styles;
        markAsDirty();
    }

    public void setUri(String uri) {
        this.getState().uri = uri;
        markAsDirty();
    }

    public void setBaseLayer(boolean isBaseLayer) {
        this.getState().isBaseLayer = isBaseLayer;
        markAsDirty();
    }

    public boolean isBaseLayer() {
        return getState().isBaseLayer;
    }

    public void setOpacity(Double opacity) {
        this.getState().opacity = opacity;
        markAsDirty();
    }

    public Double getOpacity() {
        return getState().opacity;
    }

    public String getUri() {
        return getState().uri;
    }

    public void setServiceType(String type) {
        this.getState().type = type;
        markAsDirty();
    }

    public String getServiceType() {
        return getState().type;
    }

    public String getFeatureID() {
        return getState().feature_id;
    }

    public void setLayers(String layers) {
        this.getState().layers = layers;
        markAsDirty();
    }

    public void resetFeatures() {
        this.getState().feature_id = "";
    }

    public void addFeatureID(String featureid) {
        if ("".equals(getState().feature_id)) {
            this.getState().feature_id = featureid;
        } else {
            StringBuilder buf = new StringBuilder(getState().feature_id);
            buf.append(",");
            buf.append(featureid);
            this.getState().feature_id = null;
            this.getState().feature_id = buf.toString();
        }
    }

    public String getLayer() {
        return getState().layers;
    }

    public void setFormat(String format) {
        this.getState().format = format;
        markAsDirty();
    }

    public String getFormat() {
        return getState().format;
    }

    public void setTransparent(Boolean transparent) {
        this.getState().transparent = transparent;
        markAsDirty();
    }

    public Boolean getTransparent() {
        return getState().transparent;
    }

    public void setCqlFilter(String cqlFilter) {
        this.getState().cqlFilter = cqlFilter;
        markAsDirty();
    }

    public String getCqlFilter() {
        return getState().cqlFilter;
    }

    public String getProjection() {
        return getState().projection;
    }

    public void setProjection(String projection) {
        this.getState().projection = projection;
    }

    public Boolean isSingleTile() {
        return getState().isSingleTile;
    }

    public void setSingleTile(Boolean isSingleTile) {
        this.getState().isSingleTile = isSingleTile;
    }

    public String getViewparams()
    {
       return getState().viewparams;
    }

    public void setViewparams(String viewparams)
    {
      this.getState().viewparams = viewparams;
      markAsDirty();
    }

    public boolean isInLayerSwitcher() {
        return this.getState().inLayerSwitcher;
    }

    public void setInLayerSwitcher(boolean inLayerSwitcher) {
        this.getState().inLayerSwitcher = inLayerSwitcher;
    }

    public boolean isVisibility() {
        return this.getState().visibility;
    }

    public void setVisibility(boolean visibility) {
        this.getState().visibility = visibility;
    }
}
