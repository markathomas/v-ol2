/**
 *
 */
package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.ImageLayerState;

public class ImageLayer extends AbstractComponent implements Layer {


    public ImageLayer(String url, int width, int height) {
        setUri(url);
        this.getState().width = width;
        this.getState().height = height;
    }

    @Override
    public ImageLayerState getState() {
        return (ImageLayerState)super.getState();
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

    public String getDisplayName() {
        return getState().displayName;
    }

    public void setDisplayName(String displayName) {
        getState().displayName = displayName;
        markAsDirty();
    }

    public String getUri() {
        return getState().uri;
    }

    public void setTransparent(Boolean transparent) {
        this.getState().transparent = transparent;
        markAsDirty();
    }

    public Boolean getTransparent() {
        return getState().transparent;
    }

    public void setBounds(Double... bounds) {
        this.getState().bounds = bounds;
        markAsDirty();
    }

    public Double[] getBounds() {
        return getState().bounds;
    }

}
