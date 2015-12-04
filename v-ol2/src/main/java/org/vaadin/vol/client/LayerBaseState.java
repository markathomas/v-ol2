package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class LayerBaseState extends AbstractComponentState {

    @DelegateToWidget
    public String projection = "EPSG:4326";
    @DelegateToWidget
    public String displayName;
    @DelegateToWidget
    public String attribution;
    public boolean hasLoadStartListeners;
    public boolean hasLoadEndListeners;
    public boolean hasVisibilityChangedListeners;
}
