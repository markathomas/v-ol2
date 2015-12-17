package org.vaadin.vol.client;

import com.google.gwt.core.client.JsArray;
import com.vaadin.client.ValueMap;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.vol.client.ui.VAbstractAutopopulatedVectorLayer;
import org.vaadin.vol.client.wrappers.GwtOlHandler;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Vector;
import org.vaadin.vol.client.wrappers.format.WKT;

public abstract class AutoPopulatedVectorLayerConnector extends AbstractComponentConnector {

    private final FeatureSelectionServerRpc featureSelectionServerRpc = RpcProxy.create(FeatureSelectionServerRpc.class, this);

    private GwtOlHandler selectedhandler;
    private GwtOlHandler beforeSelectedhandler;
    private GwtOlHandler unselectedhandler;

    @Override
    public VAbstractAutopopulatedVectorLayer getWidget() {
        return (VAbstractAutopopulatedVectorLayer)super.getWidget();
    }

    @Override
    public AutoPopulatedVectorLayerState getState() {
        return (AutoPopulatedVectorLayerState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        this.getWidget().attachLayerToMap();
    }

    private Vector getVector(JsArray arguments) {
        ValueMap javaScriptObject = arguments.get(0).cast();
        return javaScriptObject.getValueMap("feature").cast();
    }

    private Map<String, Object> getAttributeMap(Vector vector) {
        ValueMap attr = vector.getAttributes();
        Map<String, Object> hashMap = new HashMap<String, Object>();
        for (String key : attr.getKeySet()) {
            hashMap.put(key, attr.getString(key));
        }
        return hashMap;
    }

    private String getWKT(Vector vector) {
        Projection targetProjection = getWidget().getMap().getProjection();
        String projection = getWidget().getProjection();
        if (projection == null) {
            projection = "EPSG:4326";
        }
        Projection sourceProjection = Projection.get(projection);
        WKT wktFormatter = WKT.create(sourceProjection, targetProjection);
        return wktFormatter.write(vector);
    }

    @OnStateChange("selectionCtrlId")
    void selectionCtrlIdChanged() {
        this.getWidget().setSelectionCtrlId(getState().selectionCtrlId);
    }

    @OnStateChange("styleMap")
    void styleMapChanged() {
        //this.getWidget().setStyleMap(VVectorLayer.getStyleMap(uidl)getState().styleMap);
    }

    protected void updateSelectionControl(StateChangeEvent stateChangeEvent) {
        if (getState().registeredEventListeners != null &&
          (getState().registeredEventListeners.contains("vsel")
            || getState().registeredEventListeners.contains("vbefsel")
            || getState().registeredEventListeners.contains("vusel"))
          ) {
            if (!this.getWidget().hasControl()) {
                if (getState().registeredEventListeners.contains("vsel") && this.selectedhandler == null) {
                    this.selectedhandler = new GwtOlHandler() {
                        @SuppressWarnings("rawtypes")
                        public void onEvent(JsArray arguments) {
                            Vector vector = getVector(arguments);
                            featureSelectionServerRpc
                              .featureSelected(vector.getFeatureId(), getAttributeMap(vector), getWKT(vector));
                        }
                    };
                    this.getWidget().getLayer().registerHandler("featureselected", this.selectedhandler);
                }
                if (getState().registeredEventListeners.contains("vusel") && this.unselectedhandler == null) {
                    this.unselectedhandler = new GwtOlHandler() {
                        @SuppressWarnings("rawtypes")
                        public void onEvent(JsArray arguments) {
                            Vector vector = getVector(arguments);
                            featureSelectionServerRpc
                              .featureUnselected(vector.getFeatureId(), getAttributeMap(vector), getWKT(vector));
                        }
                    };
                    this.getWidget().getLayer().registerHandler("featureunselected", this.unselectedhandler);
                }
                if (getState().registeredEventListeners.contains("vbefsel") && this.beforeSelectedhandler == null) {
                    this.beforeSelectedhandler = new GwtOlHandler() {
                        @SuppressWarnings("rawtypes")
                        public void onEvent(JsArray arguments) {
                            Vector vector = getVector(arguments);
                            featureSelectionServerRpc
                              .beforeFeatureSelected(vector.getFeatureId(), getAttributeMap(vector), getWKT(vector));
                        }
                    };
                    this.getWidget().getLayer().registerHandler("beforefeatureselected", this.beforeSelectedhandler);
                }

                this.getWidget().createControl();
            }

            this.getWidget().activateControl();
        } else {
            this.getWidget().destroyControl();
        }
    }
}

