package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class MapTilerLayerState extends AbstractComponentState {

    @DelegateToWidget
    public String displayName;
    @DelegateToWidget
    public String uri = "";
    @DelegateToWidget
    public Boolean baseLayer = true;
    @DelegateToWidget
    public Double opacity = 1.0;
    @DelegateToWidget
    public boolean transparent = true;
    @DelegateToWidget
    public Double[] bounds = new Double[] { -180d, -90d, 180d, 90d };
    @DelegateToWidget
    public String layers = "basic";
    @DelegateToWidget
    public int minZoomLevel;
    @DelegateToWidget
    public int maxZoomLevel = -1;
}
