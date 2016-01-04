package org.vaadin.vol;

import org.vaadin.vol.client.BingMapLayerState;

/**
 * BingMap layer that can be added to {@link OpenLayersMap}. Require API key
 * from bingmapportal.com
 *
 * <p>
 * Note that no settings can be changed after the layer has been drawn for the
 * first time.
 */
public class BingMapLayer extends AbstractLayerBase implements Layer {

    public enum Type {
        Road, Aerial, AerialWithLabels
        // , Birdseye, BirdseyeWithLabels
    }

    public BingMapLayer() {
    }

    public BingMapLayer(String apikey) {
        setApikey(apikey);
    }

    @Override
    public BingMapLayerState getState() {
        return (BingMapLayerState)super.getState();
    }

    public void setApikey(String apikey) {
        this.getState().apiKey = apikey;
    }

    public String getApikey() {
        return getState().apiKey;
    }

    public void setType(Type t) {
        getState().type = t.toString();
    }

    public Type getType() {
        return Type.valueOf(getState().type);
    }

}
