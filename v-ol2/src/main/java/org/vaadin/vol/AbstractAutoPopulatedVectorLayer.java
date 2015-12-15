package org.vaadin.vol;

import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.Map;

import org.vaadin.vol.client.AutoPopulatedVectorLayerState;
import org.vaadin.vol.client.FeatureSelectionServerRpc;
import org.vaadin.vol.client.VectorLayerState;

/**
 * An abstract implementation (based on client side vector layer) that populates
 * it content on the client side only. Server side only controls how it is
 * populated, styled and provides methods for selection features.
 *
 */
public abstract class AbstractAutoPopulatedVectorLayer extends AbstractLayerBase {

    private final FeatureSelectionServerRpc featureSelectedServerRpc = new FeatureSelectionServerRpc() {
        public void beforeFeatureSelected(String fid, Map<String, Object> attr, String wkt) {
            BeforeFeatureSelectedEvent beforeFeatureSelectedEvent =
              new BeforeFeatureSelectedEvent(AbstractAutoPopulatedVectorLayer.this, fid, attr, wkt);
            fireEvent(beforeFeatureSelectedEvent);
        }

        public void featureSelected(String fid, Map<String, Object> attr, String wkt) {
            FeatureSelectedEvent featureSelectedEvent =
              new FeatureSelectedEvent(AbstractAutoPopulatedVectorLayer.this, fid, attr, wkt);
            fireEvent(featureSelectedEvent);
        }

        public void featureUnselected(String fid, Map<String, Object> attr, String wkt) {
            FeatureUnSelectedEvent featureUnSelectedEvent =
              new FeatureUnSelectedEvent(AbstractAutoPopulatedVectorLayer.this, fid, attr, wkt);
            fireEvent(featureUnSelectedEvent);
        }
    };

    public AbstractAutoPopulatedVectorLayer() {
        super();
        this.registerRpc(this.featureSelectedServerRpc);
    }

    @Override
    public AutoPopulatedVectorLayerState getState() {
        return (AutoPopulatedVectorLayerState)super.getState();
    }

    /**
     * @return the styleMap
     */
    public StyleMap getStyleMap() {
        return getState().styleMap;
    }

    /**
     * @param stylemap
     *            the styleMap to set
     */
    public void setStyleMap(StyleMap stylemap) {
        this.getState().styleMap = stylemap;
        markAsDirty();
    }

    public void setSelectionMode(VectorLayerState.SelectionMode selectionMode) {
        this.getState().selectionMode = selectionMode.toString();
        markAsDirty();
    }

    public VectorLayerState.SelectionMode getSelectionMode() {
        if (getState().selectionMode == null)
            return null;
        return VectorLayerState.SelectionMode.valueOf(getState().selectionMode);
    }

    public void setSelectionCtrlId(String selectionCtrlId) {
        this.getState().selectionCtrlId = selectionCtrlId;
    }

    public String getSelectionCtrlId() {
        return getState().selectionCtrlId;
    }

    /**
     * allows to set visibility of a layer. A call with 'setVisibility(false)'
     * displays layer in layer switcher but don't load its data.
     */
    public void setVisibility(boolean visibility) {
        this.getState().visibility = visibility;
        markAsDirty();
    }

    public void setFilter(String filterType,String filterProp,String filterValue) {
        this.getState().filterType=filterType;
        this.getState().filterProp=filterProp;
        this.getState().filterValue=filterValue;
        markAsDirty();
    }

    public void setFilterAndRefresh(String filterType,String filterProp,String filterValue) {
        this.getState().filterRefresh=true;
        setFilter(filterType,filterProp,filterValue);
    }

    public interface FeatureSelectedListener {

        String EVENT_ID = "vsel";

        Method method = ReflectTools.findMethod(
                FeatureSelectedListener.class, "featureSelected",
                FeatureSelectedEvent.class);

        void featureSelected(FeatureSelectedEvent event);

    }

    public void addListener(FeatureSelectedListener listener) {
        addListener(FeatureSelectedListener.EVENT_ID, FeatureSelectedEvent.class,
                listener, FeatureSelectedListener.method);
        getState().hasFeatureSelectedListeners = true;
    }

    public void removeListener(FeatureSelectedListener listener) {
        removeListener(FeatureSelectedListener.EVENT_ID,
                FeatureSelectedEvent.class, listener);
        getState().hasFeatureSelectedListeners = !getListeners(FeatureSelectedListener.class).isEmpty();
    }

    public void addListener(BeforeFeatureSelectedListener listener) {
        addListener(BeforeFeatureSelectedListener.EVENT_ID, BeforeFeatureSelectedEvent.class,
                listener, BeforeFeatureSelectedListener.method);
        getState().hasBeforeFeatureSelectedListeners = true;
    }

    public void removeListener(BeforeFeatureSelectedListener listener) {
        removeListener(BeforeFeatureSelectedListener.EVENT_ID,
                BeforeFeatureSelectedEvent.class, listener);
        getState().hasBeforeFeatureSelectedListeners = !getListeners(BeforeFeatureSelectedListener.class).isEmpty();
    }

    public class FeatureSelectedEvent extends Event {

        private String featureId;
        private Map<String, Object> attributes;
        private String wkt;

        public FeatureSelectedEvent(Component source, String featureId, Map<String, Object> attr, String wkt2) {
            super(source);
            this.setFeatureId(featureId);
            this.setAttributes(attr);
            this.setWkt(wkt2);
        }

        private void setWkt(String wkt2) {
            this.wkt = wkt2;
        }

        public void setAttributes(Map<String, Object> attr) {
            this.attributes = attr;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public String getFeatureId() {
            return featureId;
        }

        public void setFeatureId(String featureId) {
            this.featureId = featureId;
        }

        public String getWkt() {
            return wkt;
        }

    }

    public interface FeatureUnSelectedListener {

        String EVENT_ID = "vusel";

        Method method = ReflectTools.findMethod(
                FeatureUnSelectedListener.class, "featureUnSelected",
                FeatureUnSelectedEvent.class);

        void featureUnSelected(FeatureUnSelectedEvent event);

    }

    public interface BeforeFeatureSelectedListener {

        String EVENT_ID = "vbefsel";

        Method method = ReflectTools.findMethod(
                BeforeFeatureSelectedListener.class, "beforeFeatureSelected",
                BeforeFeatureSelectedEvent.class);

        boolean beforeFeatureSelected(BeforeFeatureSelectedEvent event);

    }

    public void addListener(FeatureUnSelectedListener listener) {
        addListener(FeatureUnSelectedListener.EVENT_ID,
                FeatureUnSelectedEvent.class, listener,
                FeatureUnSelectedListener.method);
        getState().hasFeatureUnselectedListeners = true;
    }

    public void removeListener(FeatureUnSelectedListener listener) {
        removeListener(FeatureUnSelectedListener.EVENT_ID,
                FeatureUnSelectedEvent.class, listener);
        getState().hasFeatureUnselectedListeners = !getListeners(FeatureUnSelectedListener.class).isEmpty();
    }

    public class FeatureUnSelectedEvent extends FeatureSelectedEvent {

        public FeatureUnSelectedEvent(Component source, String featureId,Map<String, Object> attr, String wkt) {
            super(source, featureId, attr, wkt);
        }

    }

    public class BeforeFeatureSelectedEvent extends FeatureSelectedEvent {
        public BeforeFeatureSelectedEvent(Component source, String featureId,Map<String, Object> attr, String wkt) {
            super(source, featureId, attr, wkt);
        }
    }

}
