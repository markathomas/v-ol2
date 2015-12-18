package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OpenLayersMapState extends AbstractComponentState {

    public List<Connector> layers = new LinkedList<Connector>();
    public Point center;
    public int zoom = 3;
    public Bounds bounds = Bounds.WORLD;

    public Connector baseLayer;
    public String jsMapOptions;
    public Bounds zoomToExtent;
    public Bounds restrictedExtent;
    public String projection;

    public Set<String> controls = new HashSet<String>();
}
