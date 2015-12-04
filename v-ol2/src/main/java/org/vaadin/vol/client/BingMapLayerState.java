package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class BingMapLayerState extends AbstractComponentState {

    @DelegateToWidget
    public String displayName;
    @DelegateToWidget
    public String apikey;
    @DelegateToWidget
    public String type = "Road";
}
