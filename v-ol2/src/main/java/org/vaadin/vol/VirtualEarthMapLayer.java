package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.VirtualEarthMapLayerState;

/**
 * Virtual Earth layer that can be added to {@link OpenLayersMap}.
 * <p>
  */
public class VirtualEarthMapLayer extends AbstractComponent implements Layer {

    @Override
    public VirtualEarthMapLayerState getState() {
        return (VirtualEarthMapLayerState)super.getState();
    }

    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
        markAsDirty();
    }

    public String getDisplayName() {
        return getState().displayName;
    }

}
