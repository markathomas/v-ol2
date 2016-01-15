package org.vaadin.vol;

import com.google.gson.Gson;
import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.Attributes;
import org.vaadin.vol.client.Point;
import org.vaadin.vol.client.Style;
import org.vaadin.vol.client.StyleMap;
import org.vaadin.vol.client.VectorState;

public abstract class Vector extends AbstractComponent {

    private Style customStyle;
    private Attributes attributes;

    @Override
    public VectorState getState() {
        return (VectorState)super.getState();
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
        Gson gson = new Gson();
        getState().styleJson = gson.toJson(customStyle);
        getState().attributesJson = gson.toJson(attributes);
    }

    public void setPoints(Point... points) {
        setPointsWithoutRepaint(points);
        markAsDirty();
    }

    protected void setPointsWithoutRepaint(Point... points) {
        this.getState().points = points;
    }

    public Point[] getPoints() {
        return getState().points;
    }

    public void setProjection(String projection) {
        this.getState().projection = projection;
    }

    public String getProjection() {
        if (getState().projection == null && getUI() != null) {
            OpenLayersMap parent2 = (OpenLayersMap)getParent().getParent();
            return parent2.getApiProjection();
        }
        return getState().projection;
    }

    /**
     * @return the custom style declaration assosicated with this Vector
     */
    public Style getCustomStyle() {
        return customStyle;
    }

    /**
     * @param style
     *            the custom style declaration to be used for rendering this
     *            Vector
     */
    public void setCustomStyle(Style style) {
        this.customStyle = style;
        markAsDirty();
    }

    public void select() {
        if (getParent() != null) {
            ((VectorLayer)getParent()).setSelectedVector(this);
        }
    }

    /**
     * Vectors styleName does not modify CSS style name as the method does for
     * standard Components. Instead the style name defines rendered intent that
     * will be used by OpenLayers to style the Vector. Rendered intents can be
     * configured with {@link StyleMap}s.
     *
     * @see com.vaadin.ui.AbstractComponent#setStyleName(java.lang.String)
     */
    @Override
    public void setStyleName(String style) {
        super.setStyleName(style);
        getState().intent = style;
    }

    /**
     * Sets a custom renderer intent that OpenLayers should use to render the
     * vector. The default is 'default'.
     *
     *
     * @see StyleMap
     *
     * @param style
     *            the name of renderer intent.
     */
    public void setRenderIntent(String style) {
        setStyleName(style);
        getState().intent = style;
    }

    /**
     * @return the vectAttributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @param attributes
     *            the vectAttributes to set
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
