package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.YahooMapLayerState;

/**
 * Yahoo layer that can be added to {@link OpenLayersMap}.
 * <p>
  */
public class YahooMapLayer extends AbstractComponent implements Layer {

    @Override
    public YahooMapLayerState getState() {
        return (YahooMapLayerState)super.getState();
    }

    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
        markAsDirty();
    }

    public String getDisplayName() {
        return getState().displayName;
    }

}
