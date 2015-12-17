package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;

public class MarkerState extends AbstractComponentState {

    public double lon;
    public double lat;
    public String projection = "EPSG:4326";
    public int iconWidth;
    public int iconHeight;
    public int iconXOffset = Integer.MIN_VALUE; // Integer.MIN_VALUE means ignore explicit offset
    public int iconYOffset = Integer.MIN_VALUE; // Integer.MIN_VALUE means ignore explicit offset
}
