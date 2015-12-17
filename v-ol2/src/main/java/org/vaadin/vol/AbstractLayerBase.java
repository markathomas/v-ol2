package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

import org.vaadin.vol.client.LayerBaseServerRpc;
import org.vaadin.vol.client.LayerBaseState;

/*
 * Layer base class to handle the basic layer events
 *
 *  The move events currently not handled. They should handled in map.
 *  I don't implement listeners for add or remove layer because I found no
 *  practical use case :-D
 */
abstract public class AbstractLayerBase extends AbstractComponent {

    protected AbstractLayerBase() {
        final LayerBaseServerRpc baseRpc = new LayerBaseServerRpc() {
            public void loadStarted(String layerName) {
                final LoadStartEvent event = new LoadStartEvent(AbstractLayerBase.this, layerName);
                fireEvent(event);
            }

            public void loadEnded(String layerName) {
                final LoadEndEvent event = new LoadEndEvent(AbstractLayerBase.this, layerName);
                fireEvent(event);
            }

            public void visibilityChanged(String layerName, boolean visible) {
                VisibilityChangedEvent event = new VisibilityChangedEvent(AbstractLayerBase.this, layerName, visible);
                fireEvent(event);

            }
        };
        this.registerRpc(baseRpc);
    }

    /*
     * add a listener to layers 'loadstart' event
     */
    public void addLoadStartListener(LoadStartListener listener) {
        addListener(LoadStartListener.EVENT_ID, LoadStartEvent.class, listener, LoadStartListener.method);
            }

    public void removeLoadStartListener(LoadStartListener listener) {
        removeListener(LoadStartListener.EVENT_ID, LoadStartEvent.class, listener);
    }

    /*
     * add a listener to layers 'loadend' event
     */
    public void addLoadEndListener(LoadEndListener listener) {
        addListener(LoadEndListener.EVENT_ID, LoadEndEvent.class, listener, LoadEndListener.method);
    }

    public void removeLoadEndListener(LoadEndListener listener) {
        removeListener(LoadEndListener.EVENT_ID, LoadEndEvent.class, listener);
    }

    /*
     * add a listener to layers 'visabilitychanged' event
     */
    public void addVisibilityChangedListener(VisibilityChangedListener listener) {
        addListener(VisibilityChangedListener.EVENT_ID,
                VisibilityChangedEvent.class, listener,
                VisibilityChangedListener.method);
    }

    public void removeVisibilityChangedListener(VisibilityChangedListener listener) {
        removeListener(VisibilityChangedListener.EVENT_ID,
                VisibilityChangedEvent.class, listener);
    }

    @Override
    public LayerBaseState getState() {
        return (LayerBaseState)super.getState();
    }

    public String getAttribution() {
        return this.getState().attribution;
    }

    /**
     * Sets the attribution text for layer. Feature is not functional in all layers.
     */
    public void setAttribution(String attribution) {
        this.getState().attribution = attribution;
    }

    public String getDisplayName() {
        return this.getState().displayName;
    }

    /**
     * Sets the display displayName for layer.
     */
    public void setDisplayName(String name) {
        this.getState().displayName = name;
    }

    public String getProjection() {
        return this.getState().projection;
    }

    /**
     * Sets the projection for layer.
     */
    public void setProjection(String projection) {
        this.getState().projection = projection;
    }

    public interface LoadStartListener {
        String EVENT_ID = "llstart";

        Method method = ReflectTools.findMethod(LoadStartListener.class, "loadStart", LoadStartEvent.class);

        void loadStart(LoadStartEvent event);
    }

    public interface LoadEndListener {
        String EVENT_ID = "llend";

        Method method = ReflectTools.findMethod(LoadEndListener.class, "loadEnd", LoadEndEvent.class);

        void loadEnd(LoadEndEvent event);
    }

    public interface VisibilityChangedListener {
        String EVENT_ID = "lvis";

        Method method = ReflectTools.findMethod(VisibilityChangedListener.class, "visibilityChanged",VisibilityChangedEvent.class);

        void visibilityChanged(VisibilityChangedEvent event);
    }

    public class LayerBaseEvent extends Event {
        private String layerName;

        public LayerBaseEvent(Component source, String layerName) {
            super(source);
            this.layerName = layerName;
        }

        public String getLayerName() {
            return layerName;
        }
    }

    public class LoadStartEvent extends LayerBaseEvent {
        public LoadStartEvent(Component source, String layerName) {
            super(source, layerName);
        }
    }

    public class LoadEndEvent extends LayerBaseEvent {
        public LoadEndEvent(Component source, String layerName) {
            super(source, layerName);
        }
    }

    public class VisibilityChangedEvent extends LayerBaseEvent {
        private boolean visible;

        public VisibilityChangedEvent(Component source, String layerName,
                Boolean visible) {
            super(source, layerName);

            // false only in case visible variable not set
            this.visible = visible != null ? visible : false;
        }

        public boolean isVisible() {
            return this.visible;
        }
    }
}
