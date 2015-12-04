package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class MapTilerLayerState extends AbstractComponentState {

    @DelegateToWidget
    public String displayName;
    @DelegateToWidget
    public String uri = "";
    @DelegateToWidget
    public Boolean isBaseLayer = true;
    @DelegateToWidget
    public Double opacity = 1.0;
    @DelegateToWidget
    public Boolean transparent = true;
    @DelegateToWidget
    public Double[] bounds;
    @DelegateToWidget
    public String layers = "basic";
    @DelegateToWidget
    public int minZoom;
    @DelegateToWidget
    public int maxZoom = -1;
}
