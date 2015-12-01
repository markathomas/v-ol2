package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;

public class LayerBaseState extends AbstractComponentState {

    public String projection = "EPSG:4326";
    public String name;
    public String attribution;
    public boolean hasLoadStartListeners;
    public boolean hasLoadEndListeners;
    public boolean hasVisibilityChangedListeners;
}
