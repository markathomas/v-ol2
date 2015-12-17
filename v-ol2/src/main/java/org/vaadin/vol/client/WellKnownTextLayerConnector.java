package org.vaadin.vol.client;

import com.google.gwt.core.client.JsArray;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.WellKnownTextLayer;
import org.vaadin.vol.client.ui.VOpenLayersMap;
import org.vaadin.vol.client.ui.VWellKnownTextLayer;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.format.WKT;

@Connect(WellKnownTextLayer.class)
public class WellKnownTextLayerConnector extends AutoPopulatedVectorLayerConnector {

    @Override
    public WellKnownTextLayerState getState() {
        return (WellKnownTextLayerState)super.getState();
    }

    @Override
    public VWellKnownTextLayer getWidget() {
        return (VWellKnownTextLayer)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        org.vaadin.vol.client.wrappers.layer.VectorLayer lyr = this.getWidget().getLayer();
        lyr.removeAllFeatures();

        if (getState().wkt == null || getState().wkt.isEmpty()) {
            return;
        }

        Projection targetProjection = getWidget().getMap().getProjection();
        String projection = getWidget().getProjection();
        Projection sourceProjection;
        if (projection != null) {
            sourceProjection = Projection.get(projection);
        } else {
            // if not explicitly defined, use the API projection from the map
            sourceProjection = ((VOpenLayersMap)getWidget().getParent()).getProjection();
        }
        WKT wktFormatter = WKT.create(targetProjection, sourceProjection);
        JsArray<Vector> read = wktFormatter.read(getState().wkt);
        for(int i = 0; i < read.length(); i++) {
            Vector vector = read.get(i);
            lyr.addFeature(vector);
        }

        this.updateSelectionControl(stateChangeEvent);
    }
}
