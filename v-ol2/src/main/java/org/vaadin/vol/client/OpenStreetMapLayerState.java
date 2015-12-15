package org.vaadin.vol.client;

import com.vaadin.shared.annotations.DelegateToWidget;

public class OpenStreetMapLayerState extends LayerBaseState {

    @DelegateToWidget
    public String url;
}
