package org.vaadin.vol.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;

import org.vaadin.vol.client.Attributes;
import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.VectorState;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.layer.VectorLayer;

public abstract class VAbstractVector<T extends VectorState> extends Widget {

    protected Vector vector;
    protected Attributes vectAttributes;
    private Projection projection;
    private String intent;

    public VAbstractVector() {
        setElement(Document.get().createDivElement());
    }

    protected Attributes getAttributes() {
        return vectAttributes;
    }

    public void setAttributes(Attributes vectAttributes) {
        this.vectAttributes = vectAttributes;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Projection getProjection() {
        if (projection == null) {
            VVectorLayer parent2 = (VVectorLayer) getParent();
            return parent2.getProjection();
        }
        return projection;
    }

    public String getIntent() {
        return this.intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public abstract void createOrUpdateVector(StateChangeEvent event, T state);

    public VectorLayer getLayer() {
        return ((VVectorLayer) getParent()).getLayer();
    }

    protected Map getMap() {
        return MapUtil.getMap(getParent());
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        getLayer().removeFeature(vector);
    }

    public Vector getVector() {
        return vector;
    }

    public void revertDefaultIntent() {
        getVector().setRenderIntent(intent);
        getVector().redraw();
    }

}
