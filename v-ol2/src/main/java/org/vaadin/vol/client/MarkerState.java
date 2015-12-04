package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class MarkerState extends AbstractComponentState {

    @DelegateToWidget
    public double lon;
    @DelegateToWidget
    public double lat;
    @DelegateToWidget
    public String projection = "EPSG:4326";
    @DelegateToWidget
    public int iconWidth;
    @DelegateToWidget
    public int iconHeight;
    @DelegateToWidget
    public int iconXOffset = Integer.MIN_VALUE; // Integer.MIN_VALUE means ignore explicit offset
    @DelegateToWidget
    public int iconYOffset = Integer.MIN_VALUE; // Integer.MIN_VALUE means ignore explicit offset

    public boolean hasClickListeners;
}
