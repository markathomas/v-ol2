package org.vaadin.vol.client;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.WebFeatureServiceLayer;
import org.vaadin.vol.client.ui.VWebFeatureServiceLayer;

@Connect(WebFeatureServiceLayer.class)
public class WebFeatureServiceLayerConnector extends AutoPopulatedVectorLayerConnector {

    @Override
    public WebFeatureServiceLayerState getState() {
        return (WebFeatureServiceLayerState)super.getState();
    }

    @Override
    public VWebFeatureServiceLayer getWidget() {
        return (VWebFeatureServiceLayer)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        org.vaadin.vol.client.wrappers.layer.WebFeatureServiceLayer lyr = this.getWidget().getLayer();
        if (stateChangeEvent.hasPropertyChanged("filterType") && lyr != null) {
            lyr.setFilter(getState().filterType, getState().filterProp, getState().filterValue);
            if (stateChangeEvent.hasPropertyChanged("filterRefresh")) {
                lyr.refresh();
            }
        }

        if (stateChangeEvent.hasPropertyChanged("visibility") && lyr != null) {
            lyr.setVisability(getState().visibility);
        }

        this.updateSelectionControl(stateChangeEvent);
    }
}
