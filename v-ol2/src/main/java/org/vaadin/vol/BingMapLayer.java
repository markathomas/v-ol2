package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.BingMapLayerState;

/**
 * BingMap layer that can be added to {@link OpenLayersMap}. Require API key
 * from bingmapportal.com
 *
 * <p>
 * Note that no settings can be changed after the layer has been drawn for the
 * first time.
 */
public class BingMapLayer extends AbstractComponent implements Layer {

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


    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
    }

    public String getDisplayName() {
        return getState().displayName;
    }

    public void setApikey(String apikey) {
        this.getState().apikey = apikey;
    }

    public String getApikey() {
        return getState().apikey;
    }

    public void setType(Type t) {
        getState().type = t.toString();
    }

    public Type getType() {
        return Type.valueOf(getState().type);
    }

}
