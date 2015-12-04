package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class MarkerLayerState extends AbstractComponentState {

    @DelegateToWidget
    public String displayName = "Markers";
}
