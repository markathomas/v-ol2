package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VectorLayerState extends AbstractComponentState {



    public enum SelectionMode {
        NONE, SIMPLE
        // MULTI, MULTI_WITH_AREA_SELECTION etc
    }

    public enum DrawingMode {
        NONE, LINE, AREA, RECTANGLE, CIRCLE, POINT, MODIFY
    }

    public Map<String, String> styleMap;
    public List<String> uniqueValueRules;
    public boolean extendDefault = false;

    public Connector selectedVector;

    public String displayName = "Vector layer";

    public List<Connector> vectors = new LinkedList<Connector>();

    public SelectionMode selectionMode = SelectionMode.NONE;

    public DrawingMode drawingMode = DrawingMode.NONE;

    public String selectionCtrlId;             // Common SelectFeature control identifier
}
