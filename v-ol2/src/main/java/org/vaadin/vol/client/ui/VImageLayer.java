package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.Bounds;
import org.vaadin.vol.client.wrappers.Size;
import org.vaadin.vol.client.wrappers.layer.ImageLayer;

public class VImageLayer extends VAbstracMapLayer<ImageLayer> {

    private String uri;
    private Boolean baseLayer;
    private Double[] bounds;
    private int width;
    private int height;

    @Override
    ImageLayer createLayer() {
        return ImageLayer.create(getDisplayName(), uri, Bounds.create(bounds[0], bounds[1], bounds[2], bounds[3]), getSize(),
          baseLayer);
    }

    private Size getSize() {
        return Size.create(width, height);
    }

    public Double[] getBounds() {
        return this.bounds;
    }

    public void setBounds(Double[] bounds) {
        this.bounds = bounds;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean isBaseLayer() {
        return this.baseLayer;
    }

    public void setBaseLayer(Boolean baseLayer) {
        this.baseLayer = baseLayer;
    }

    public int getImageWidth() {
        return this.width;
    }

    public void setImageWidth(int width) {
        this.width = width;
    }

    public int getImageHeight() {
        return this.height;
    }

    public void setImageHeight(int height) {
        this.height = height;
    }
}
