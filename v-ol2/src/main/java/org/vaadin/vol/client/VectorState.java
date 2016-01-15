package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;

public class VectorState extends AbstractComponentState {

    public String projection;
    public Point[] points;
    public String intent;

    // Styles object as JSON
    public String styleJson;
    // Attributes object as JSON
    public String attributesJson;
}
