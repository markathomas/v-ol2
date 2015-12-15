package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.client.ValueMap;
import com.vaadin.client.communication.StateChangeEvent;

import org.vaadin.vol.Attributes;
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

        JavaScriptObject style = null;
        Attributes attributes = getAttributes(); // TODO: how do i convert this to a ValueMap or a JavaScriptObject???
        if (vector == null) {
            // TODO: FIX ME!!
            //vector = Vector.create(p, attributes, style);
            vector = Vector.create(p, JavaScriptObject.createObject(), style);
        } else {
            vector.setGeometry(p);
            vector.setStyle(style);
            // TODO: FIX ME!!
            //vector.setAttributes(attributes);
            ValueMap attr = JavaScriptObject.createObject().cast();
            vector.setAttributes(attr);
        }
    }
}
