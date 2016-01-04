package org.vaadin.vol.client;

import com.vaadin.shared.annotations.DelegateToWidget;

public class BingMapLayerState extends LayerBaseState {

    @DelegateToWidget
    public String apiKey;
    @DelegateToWidget
    public String type = "Road";
}
