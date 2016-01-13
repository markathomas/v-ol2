package org.vaadin.vol.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.layer.Layer;

public abstract class VAbstracMapLayer<T extends Layer> extends Widget implements VLayer {

    public VAbstracMapLayer() {
        setElement(Document.get().createDivElement());
    }

    protected T layer;
    protected boolean layerAttached = false;
    private String displayName;
    private String projection;
    private String attribution;


    public T getLayer() {
        if (layer == null) {
            layer = createLayer();
        }
        return layer;
    }

    abstract T createLayer();

    public void attachLayerToMap() {
        if (!layerAttached) {
            getMap().addLayer(getLayer());
            layerAttached = true;
        }
    }

    public Map getMap() {
        return MapUtil.getMap(getParent());
    }

    @Override
    protected void onDetach() {
        if (layerAttached && layer != null) {
            getMap().removeLayer(layer);
            layerAttached = false;
        }
        super.onDetach();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if(!layerAttached && layer != null) {
            attachLayerToMap();
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProjection() {
        return this.projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getAttribution() {
        return this.attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
}
