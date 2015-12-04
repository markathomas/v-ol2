package org.vaadin.vol.client;

import com.vaadin.shared.annotations.DelegateToWidget;

public class WebFeatureServiceLayerState extends AutoPopulatedVectorLayerState {

    @DelegateToWidget
    public String uri = "";
    @DelegateToWidget
    public String featureType = "basic";
    @DelegateToWidget
    public String featureNS;

}
