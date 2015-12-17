package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class OpenLayersMapState extends AbstractComponentState {

    public List<Connector> layers = new LinkedList<Connector>();
    public double centerLon;
    public double centerLat;
    public int zoom = 3;
    public Bounds bounds = Bounds.WORLD;

    public String jsMapOptions;
    public Bounds zoomToExtent;
    public Bounds restrictedExtent;
    public String projection;

    public HashSet<Control> controls = new HashSet<Control>(Arrays.asList(
      Control.ArgParser, Control.Navigation, Control.TouchNavigation,
      Control.Attribution));
}
