package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.vol.Bounds;
import org.vaadin.vol.Control;

public class OpenLayersMapState extends AbstractComponentState {

    public List<Connector> layers = new LinkedList<Connector>();
    public double centerLon = 0;
    public double centerLat = 0;
    public int zoom = 3;
    public Bounds bounds = new Bounds();

    public String jsMapOptions;
    public Bounds zoomToExtent;
    public Bounds restrictedExtent;
    public String projection;

    public HashSet<Control> controls = new HashSet<Control>(Arrays.asList(
      Control.ArgParser, Control.Navigation, Control.TouchNavigation,
      Control.Attribution));
}
