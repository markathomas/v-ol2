package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.client.communication.StateChangeEvent;
import org.vaadin.vol.client.VectorState;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.geometry.Point;

public class VPointVector extends VAbstractVector {

    @Override
    public void createOrUpdateVector(StateChangeEvent event, VectorState state) {
        Projection mapProjection = getMap().getProjection();
        Point p = Point.create(state.points[0].getLon(), state.points[0].getLat());
        p.transform(getProjection(), mapProjection);

        if (vector == null) {
            vector = Vector.create(p, getAttributes(), JavaScriptObject.createObject());
        } else {
            vector.setGeometry(p);
            vector.setAttributes(getAttributes());
        }
    }
}
