package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;

import org.vaadin.vol.Attributes;
import org.vaadin.vol.Point;
import org.vaadin.vol.Style;

public class VectorState extends AbstractComponentState {

    public String projection;
    public Point[] points;
    public String intent;
    public Style style;
    public Attributes vectAttributes;
}
