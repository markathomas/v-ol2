package org.vaadin.vol;

import org.vaadin.vol.client.YahooMapLayerState;

/**
 * Yahoo layer that can be added to {@link OpenLayersMap}.
 * <p>
  */
public class YahooMapLayer extends AbstractLayerBase implements Layer {

    @Override
    public YahooMapLayerState getState() {
        return (YahooMapLayerState)super.getState();
    }
}
