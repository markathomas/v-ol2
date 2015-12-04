package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.MapTilerLayer;
import org.vaadin.vol.client.ui.VMapTilerLayer;

@Connect(MapTilerLayer.class)
public class MapTilerLayerConnector extends AbstractComponentConnector {

    @Override
    public VMapTilerLayer createWidget() {
        return GWT.create(VMapTilerLayer.class);
    }

    @Override
    public VMapTilerLayer getWidget() {
        return (VMapTilerLayer)super.getWidget();
    }

    @Override
    public MapTilerLayerState getState() {
        return (MapTilerLayerState)super.getState();
    }
}
