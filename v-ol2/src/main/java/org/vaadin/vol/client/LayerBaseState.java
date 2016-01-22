package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class LayerBaseState extends AbstractComponentState {

    @DelegateToWidget
    public String projection;
    @DelegateToWidget
    public String displayName;
    @DelegateToWidget
    public String attribution;
}
