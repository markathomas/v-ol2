package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.vaadin.client.ValueMap;
import com.vaadin.client.communication.StateChangeEvent;

import org.vaadin.vol.client.Attributes;
import org.vaadin.vol.client.VectorState;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.geometry.LinearRing;
import org.vaadin.vol.client.wrappers.geometry.Point;

public class VArea extends VAbstractVector<VectorState> {

    @SuppressWarnings("unchecked")
    @Override
    public void createOrUpdateVector(StateChangeEvent stateChangeEvent, VectorState state) {
        Projection mapProjection = getMap().getProjection();
        JsArray<Point> points = (JsArray<Point>) JsArray.createArray();
        for (int i = 0; i < state.points.length; i++) {
            Point p = Point.create(state.points[i].getLon(), state.points[i].getLat());
            p.transform(getProjection(), mapProjection);
            points.push(p);
        }

        LinearRing lr = LinearRing.create(points);

        JavaScriptObject style = null;
        Attributes attributes = getAttributes();  // TODO: how do i convert this to a ValueMap or a JavaScriptObject???
        if (vector == null) {
            // TODO: FIX ME!
            //vector = Vector.create(lr, attributes, style);
            vector = Vector.create(lr, JavaScriptObject.createObject(), style);
        } else {
            vector.setGeometry(lr);
            vector.setStyle(style);
            // TODO: FIX ME!
            // vector.setAttributes(attributes);
            ValueMap attr = JavaScriptObject.createObject().cast();
            vector.setAttributes(attr);
        }
    }

}
