package org.vaadin.vol.client;

import com.vaadin.shared.annotations.DelegateToWidget;

public class WebMapServiceLayerState extends LayerBaseState {

    @DelegateToWidget
    public String uri = "";
    public String type = "wms";
    @DelegateToWidget
    public String layers = "basic";
    @DelegateToWidget
    public String cqlFilter;
    @DelegateToWidget
    public boolean baseLayer = true;
    @DelegateToWidget
    public double opacity = 1.0;
    @DelegateToWidget
    public boolean transparent = true;
    @DelegateToWidget
    public boolean singleTile;
    public String feature_id = "";
    @DelegateToWidget
    public String format = "image/jpeg";
    @DelegateToWidget
    public String layerStyles;
    @DelegateToWidget
    public String viewparams;
    @DelegateToWidget
    public boolean inLayerSwitcher;
    @DelegateToWidget
    public boolean visibility;

}
