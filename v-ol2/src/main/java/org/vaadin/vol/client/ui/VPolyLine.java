package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.vaadin.client.communication.StateChangeEvent;

import org.vaadin.vol.client.VectorState;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.geometry.LineString;
import org.vaadin.vol.client.wrappers.geometry.Point;

public class VPolyLine extends VAbstractVector {

    @SuppressWarnings("unchecked")
    @Override
    public void createOrUpdateVector(StateChangeEvent event, VectorState state) {
        Projection mapProjection = getMap().getProjection();
        JsArray<Point> points = (JsArray<Point>) JsArray.createArray();
        for (int i = 0; i < state.points.length; i++) {
            Point p = Point.create(state.points[i].getLon(), state.points[i].getLat());
            p.transform(getProjection(), mapProjection);
            points.push(p);
        }

        LineString lr = LineString.create(points);

        if (vector == null) {
            vector = Vector.create(lr, getAttributes(), JavaScriptObject.createObject());
        } else {
            vector.setGeometry(lr);
            vector.setAttributes(getAttributes());
        }

    }
}
