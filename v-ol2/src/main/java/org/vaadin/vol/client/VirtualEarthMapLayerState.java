package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class VirtualEarthMapLayerState extends AbstractComponentState {

    @DelegateToWidget
    public String displayName;
}
