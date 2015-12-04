package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.ImageLayer;
import org.vaadin.vol.client.ui.VImageLayer;

@Connect(ImageLayer.class)
public class ImageLayerConnector extends AbstractComponentConnector {

    @Override
    public VImageLayer createWidget() {
        return GWT.create(VImageLayer.class);
    }

    @Override
    public VImageLayer getWidget() {
        return (VImageLayer)super.getWidget();
    }

    @Override
    public ImageLayerState getState() {
        return (ImageLayerState)super.getState();
    }
}
