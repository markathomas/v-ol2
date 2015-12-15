/**
 *
 */
package org.vaadin.vol;

import org.vaadin.vol.client.XYZLayerState;

public class XYZLayer extends AbstractLayerBase implements Layer {

    @Override
    public XYZLayerState getState() {
        return (XYZLayerState)super.getState();
    }

    public void setUri(String uri) {
        this.getState().uri = uri;
        markAsDirty();
    }

    @Override
    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
        setCaption(displayName);
    }

    public String getUri() {
        return getState().uri;
    }

    public boolean isSphericalMercator() {
        return getState().sphericalMercator;
    }

    public void setSphericalMercator(boolean sphericalMercator) {
        this.getState().sphericalMercator = sphericalMercator;
        markAsDirty();
    }
}
