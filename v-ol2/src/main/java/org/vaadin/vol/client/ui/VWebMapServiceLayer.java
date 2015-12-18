package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.WebMapServiceLayer;

public class VWebMapServiceLayer extends VAbstracMapLayer<WebMapServiceLayer> {

    private String uri;
    private String layers;
    private Boolean baseLayer;
    private Boolean singleTile;
    private Double opacity;
    private String format;
    private boolean transparent;
    private String cqlFilter;
    private String styles;
    private String viewparams;
    private boolean inLayerSwitcher;
    private boolean visibility;

    @Override
    protected WebMapServiceLayer createLayer() {
        return WebMapServiceLayer.create(getDisplayName(), uri, layers, format,
          cqlFilter, styles, baseLayer, transparent, opacity, singleTile,
          getProjection(), viewparams, inLayerSwitcher, visibility);
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLayers() {
        return this.layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public Boolean isBaseLayer() {
        return this.baseLayer;
    }

    public void setBaseLayer(boolean baseLayer) {
        this.baseLayer = baseLayer;
    }

    public Boolean isSingleTile() {
        return this.singleTile;
    }

    public void setSingleTile(boolean singleTile) {
        this.singleTile = singleTile;
    }

    public Double getOpacity() {
        return this.opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public String getCqlFilter() {
        return this.cqlFilter;
    }

    public void setCqlFilter(String cqlFilter) {
        this.cqlFilter = cqlFilter;
    }

    public String getStyles() {
        return this.styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public String getViewparams() {
        return this.viewparams;
    }

    public void setViewparams(String viewparams) {
        this.viewparams = viewparams;
    }

    public boolean isInLayerSwitcher() {
        return this.inLayerSwitcher;
    }

    public void setInLayerSwitcher(boolean inLayerSwitcher) {
        this.inLayerSwitcher = inLayerSwitcher;
    }

    public boolean isVisibility() {
        return this.visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
