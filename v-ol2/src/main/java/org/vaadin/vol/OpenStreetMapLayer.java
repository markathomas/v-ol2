/**
 *
 */
package org.vaadin.vol;

import org.vaadin.vol.client.OpenStreetMapLayerState;

/**
 * OpenStreeMap layer that can be added to {@link OpenLayersMap}.
 */
public class OpenStreetMapLayer extends AbstractLayerBase implements Layer {

    @Override
    public OpenStreetMapLayerState getState() {
        return (OpenStreetMapLayerState)super.getState();
    }

    public String getUrl() {
        return getState().url;
    }

    public void setUrl(String url) {
        getState().url = url;
        markAsDirty();
    }

}
