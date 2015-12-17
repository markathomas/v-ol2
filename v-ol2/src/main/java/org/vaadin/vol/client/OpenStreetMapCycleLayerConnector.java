package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.OpenStreetMapCycleLayer;
import org.vaadin.vol.client.ui.VOpenStreetMapCycleLayer;

@Connect(OpenStreetMapCycleLayer.class)
public class OpenStreetMapCycleLayerConnector extends LayerBaseConnector {

    @Override
    public VOpenStreetMapCycleLayer getWidget() {
        return (VOpenStreetMapCycleLayer)super.getWidget();
    }

    @Override
    public VOpenStreetMapCycleLayer createWidget() {
        return GWT.create(VOpenStreetMapCycleLayer.class);
    }
}
