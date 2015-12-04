package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.layer.VectorLayer;

public class VWellKnownTextLayer extends VAbstractAutopopulatedVectorLayer<VectorLayer> {

    @Override
    VectorLayer createLayer() {
        if (layer == null) {
            layer = VectorLayer.create(getDisplayName(), getStyleMap());
        }
        return layer;
    }
}
