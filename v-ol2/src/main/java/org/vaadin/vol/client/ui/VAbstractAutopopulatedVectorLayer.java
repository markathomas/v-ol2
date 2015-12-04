package org.vaadin.vol.client.ui;

import org.vaadin.vol.client.wrappers.SelectFeatureFactory;
import org.vaadin.vol.client.wrappers.StyleMap;
import org.vaadin.vol.client.wrappers.control.SelectFeature;
import org.vaadin.vol.client.wrappers.layer.VectorLayer;

public abstract class VAbstractAutopopulatedVectorLayer<T extends VectorLayer> extends VAbstracMapLayer<T> {

    private SelectFeature control;
    private StyleMap styleMap;
    private String selectionCtrlId;

    public VAbstractAutopopulatedVectorLayer() {
        super();
    }

    public boolean hasControl() {
        return control != null;
    }

    public void createControl() {
        control = SelectFeatureFactory.getInst().getOrCreate(selectionCtrlId,getMap(),getLayer());
    }

    public void activateControl() {
        if (hasControl())
            control.activate();
    }

    public void destroyControl() {
        if (hasControl()) {
            SelectFeatureFactory.getInst().removeLayer(control,selectionCtrlId,getMap(),getLayer());
            control = null;
        }
    }

    public StyleMap getStyleMap() {
        return this.styleMap;
    }

    public void setStyleMap(StyleMap styleMap) {
        this.styleMap = styleMap;
    }

    public String getSelectionCtrlId() {
        return this.selectionCtrlId;
    }

    public void setSelectionCtrlId(String selectionCtrlId) {
        this.selectionCtrlId = selectionCtrlId;
    }
}
