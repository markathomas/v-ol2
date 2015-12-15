package org.vaadin.vol.client;

import com.vaadin.shared.annotations.DelegateToWidget;

public class XYZLayerState extends LayerBaseState {

    @DelegateToWidget
    public String uri = "";
    @DelegateToWidget
    public boolean sphericalMercator;
}
