package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.Bounds;
import org.vaadin.vol.client.wrappers.Size;
import org.vaadin.vol.client.wrappers.layer.ImageLayer;

public class VImageLayer extends VAbstracMapLayer<ImageLayer> {

    private String uri;
    private Boolean isBaseLayer;
    private Bounds bounds;
    private int width;
    private int height;

    @Override
    ImageLayer createLayer() {
        return ImageLayer.create(getDisplayName(), uri, getBounds(), getSize(),
                isBaseLayer);
    }

    private Size getSize() {
        return Size.create(width, height);
    }

    private Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean isBaseLayer() {
        return this.isBaseLayer;
    }

    public void setBaseLayer(Boolean baseLayer) {
        isBaseLayer = baseLayer;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
