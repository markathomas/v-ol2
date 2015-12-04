package org.vaadin.vol.client;

import org.vaadin.vol.StyleMap;

public class AutoPopulatedVectorLayerState extends LayerBaseState {

    public boolean hasBeforeFeatureSelectedListeners;
    public boolean hasFeatureSelectedListeners;
    public boolean hasFeatureUnselectedListeners;

    public StyleMap styleMap;
    public String selectionMode;

    public boolean visibility;

    public String filterValue;
    public String filterProp;
    public String filterType;
    public boolean filterRefresh;

    /**
     * this will be used to group vector layers to share the same SelectFeature
     * control
     */
    public String selectionCtrlId;

}
