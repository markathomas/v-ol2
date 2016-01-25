package org.vaadin.vol;

import com.vaadin.annotations.JavaScript;
import com.vaadin.event.Action;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.server.KeyMapper;
import com.vaadin.shared.Connector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.*;

import org.vaadin.vol.client.*;

/**
 * Server side component for the VOpenLayersMap widget.
 */
@SuppressWarnings("serial")
@JavaScript({ "helpers.js" })
public class OpenLayersMap extends AbstractComponentContainer implements Action.Container {

    private LinkedList<Action.Handler> actionHandlers = new LinkedList<Action.Handler>();
    private KeyMapper<Action> actionMapper = new KeyMapper<Action>();

    public OpenLayersMap() {
        this(false);
    }

    public OpenLayersMap(boolean skipControls) {
        setWidth("100%");
        setHeight("100%");
        if (!skipControls) {
            addControl(Control.ArgParser);
            addControl(Control.Navigation);
            addControl(Control.TouchNavigation);
            addControl(Control.Attribution);
            addControl(Control.PanZoom);
            addControl(Control.LayerSwitcher);
        }

        this.registerRpc(new OpenLayersMapServerRpc() {

            @Override
            public void mapClicked(PointInformation pointInformation) {
                MapClickEvent mapClickEvent = new MapClickEvent(OpenLayersMap.this, pointInformation);
                fireEvent(mapClickEvent);
            }

            @Override
            public void extentChanged(Bounds bounds, int zoom) {
                getState().bounds = bounds;
                getState().zoom = zoom;
                ExtentChangeEvent extentChangeEvent = new ExtentChangeEvent();
                fireEvent(extentChangeEvent);
            }

            @Override
            public void baseLayerChanged(String connectorId) {
                for (Connector c : getState().layers) {
                    if (Objects.equals(c.getConnectorId(), connectorId)) {
                        getState().baseLayer = c;
                        break;
                    }
                }
                BaseLayerChangeEvent baseLayerChangeEvent = new BaseLayerChangeEvent();
                fireEvent(baseLayerChangeEvent);
            }

            @Override
            public void contextMenuClicked(String key, Point point) {
                final Action action = actionMapper.get(key);
                if (action != null) {
                    for (Action.Handler ah : actionHandlers) {
                        ah.handleAction(action, this, point);
                    }
                }
            }
        });
        registerRpc(new ContextClickRpc() {
            @Override
            public void contextClick(MouseEventDetails details, Point point) {
                fireEvent(new OpenLayersMapContextClickEvent(OpenLayersMap.this, details, point));
            }
        });
    }

    @Override
    public OpenLayersMapState getState() {
        return (OpenLayersMapState)super.getState();
    }

    public void addControl(Control control) {
        Set<String> tmp = new HashSet<String>(getState().controls);
        tmp.add(control.name());
        this.getState().controls = tmp;
        markAsDirty();
    }

    public void removeControl(Control control) {
        Set<String> tmp = new HashSet<String>(getState().controls);
        tmp.remove(control.name());
        this.getState().controls = tmp;
        markAsDirty();
    }

    /**
     * @return set of current controls used by this map. Note that returns
     *         reference to internal data structure. If you modify the set
     *         directly, call markAsDirty for the map to force repaint.
     */
    public Set<Control> getControls() {
        Set<Control> controls = new HashSet<Control>(getState().controls.size());
        for (String name : getState().controls) {
            controls.add(Control.valueOf(name));
        }
        return controls;
    }

    /**
     * A typed alias for {@link #addComponent(Component)}.
     *
     * @param layer
     */
    public void addLayer(Layer layer) {
        addComponent(layer);
    }

    @Override
    public void removeComponent(Component c) {
        super.removeComponent(c);
        this.getState().layers.remove(c);
        markAsDirty();
    }

    /**
     * Adds component into the OpenLayers Map. Note that the map only supports
     * certain types of Components.
     * <p>
     * Developers are encouraged to use better typed methods instead:
     *
     * @see #addLayer(Layer)
     * @see #addPopup(Popup)
     *
     * @see com.vaadin.ui.AbstractComponentContainer#addComponent(com.vaadin.ui.Component)
     */
    @Override
    public void addComponent(Component c) {
        super.addComponent(c);
        this.getState().layers.remove(c);
        this.getState().layers.add(c);
        markAsDirty();
    }


    /**
     * Change the base layer of the map.
     * <p>
     * Note that once the change has been made client-side, a BaseLayerChangeEvent will be fired server-side, and the name of
     * the new base layer will be available from getBaseLayerName().
     * @param newBaseLayer      the layer that will be the new base layer
     * @throws IllegalArgumentException If the layer is not already associated with the map and/or the layer is not a base layer.
     */
    public void setBaseLayer(Layer newBaseLayer) {
        // Need to add a check to make sure the newBaseLayer is in fact a base layer....API needs more work for that
        if (newBaseLayer != null && this.getState().layers.contains(newBaseLayer)) {
            //System.out.println("...not eq, setting");
            this.getState().baseLayer = newBaseLayer;
       } else {
            throw new IllegalArgumentException("Only existing map layers can become the base layer");
       }
    }

    /**
     * Get the name (display name) of the current base layer
     *
     * @return Current base layer name.
     */
    public Layer getBaseLayer() {
        return (Layer)this.getState().baseLayer;
    }

    public void setCenter(double lon, double lat) {
        this.getState().center = new Point(lon, lat);
        markAsDirty();
    }

    /**
     * Set the center of map to the center of a bounds
     *
     */
    public void setCenter(Bounds bounds) {
        double lat = (bounds.getBottom() + bounds.getTop()) / 2.0;
        double lon = (bounds.getRight() + bounds.getLeft()) / 2.0;
        setCenter(lon, lat);
    }

    public void setZoom(int zoomLevel) {
        this.getState().zoom = zoomLevel;
        markAsDirty();
    }

    public int getZoom() {
        return this.getState().zoom;
    }

    public void replaceComponent(Component oldComponent, Component newComponent) {
        this.removeComponent(oldComponent);
        this.addComponent(newComponent);
    }

    @Override
    public int getComponentCount() {
        return this.getState().layers.size();
    }

    public Iterator<Component> iterator() {
        List<Component> components = new ArrayList<Component>(getState().layers.size());
        for (Connector connector : getState().layers) {
            components.add((Component)connector);
        }
        return components.iterator();
    }

    public void addPopup(Popup popup) {
        addComponent(popup);
    }

    /**
     * Sets the js snippet that will be evaluated as maps custom options. See
     * OpenLayers JS api for more details.
     * <p>
     * Note, that the string will be executed as javascript on the client side.
     * VALIDATE content in case you accept input from the client.
     * <p>
     * Also note that init options only take effect if they are set before the
     * map gets rendered.
     *
     * @param jsMapOptions
     */
    public void setJsMapOptions(String jsMapOptions) {
        this.getState().jsMapOptions = jsMapOptions;
        markAsDirty();
    }

    public String getJsMapOptions() {
        return getState().jsMapOptions;
    }

    /**
     * Zooms the map to display given bounds.
     *
     * <p>
     * Note that this method overrides possibly set center and zoom levels.
     *
     * @param bounds
     */
    public void zoomToExtent(Bounds bounds) {
        getState().zoomToExtent = bounds;
        // also save center point for refreshes
        setCenter(bounds);
    }

    public Bounds getRestrictedExtent() {
        return this.getState().restrictedExtent;
    }

    /**
     * Sets the area within the panning and zooming is restricted. With this
     * method developer can "limit" the area that is shown for the end user.
     * <p>
     * Note, that due the fact that open layers supports just zoom levels, the
     * displayed area might be slightly larger if the size of the component
     * don't match with the size of restricted area on minimum zoom level. If
     * area outside restricted extent may not be displayed at all, one must
     * ensure about this by either using a base layer that only contains the
     * desired area or by "masking" out the undesired area with e.g. a vector
     * layer.
     *
     * @param bounds
     */
    public void setRestrictedExtent(Bounds bounds) {
        getState().restrictedExtent = bounds;
        markAsDirty();
    }

    /**
     * Sets the projection which is used by the user of this map. E.g. values
     * passed to API like {@link #setCenter(double, double)} should be in the
     * same projection.
     * <p>
     * Note that resetting projection on already rendered map may cause
     * unexpected results.
     *
     * @param projection
     */
    public void setApiProjection(String projection) {
        this.getState().projection = projection;
        markAsDirty();
    }

    /**
     * Gets the projection which is used by the user of this map. E.g. values
     * passed to API like {@link #setCenter(double, double)} should be in the
     * same projection.
     *
     * @return the projection used, defaults to EPSG:4326
     */
    public String getApiProjection() {
        return getState().projection;
    }

    /**
     * Calculates an array of resolutions for use in OpenLayers map creation
     */
    protected double[] calculateResolutions(Bounds bounds, int tileSize,
            int zoomLevels) {

        final double extentWidth = bounds.getRight() - bounds.getLeft();
        final double extentHeight = bounds.getTop() - bounds.getBottom();

        double resX = extentWidth / tileSize;
        double resY = extentHeight / tileSize;

        if (resX <= resY) {
            // use one tile wide by N tiles high
            int tilesHigh = (int) Math.round(resY / resX);
            // previous resY was assuming 1 tile high, recompute with the actual
            // number of tiles
            // high
            resY = resY / tilesHigh;
        } else {
            // use one tile high by N tiles wide
            int tilesWide = (int) Math.round(resX / resY);
            // previous resX was assuming 1 tile wide, recompute with the actual
            // number of tiles
            // wide
            resX = resX / tilesWide;
        }

        // the maximum of resX and resY is the one that adjusts better
        final double res = Math.max(resX, resY);

        double[] resolutions = new double[zoomLevels];
        resolutions[0] = res;

        for (int i = 1; i < zoomLevels; i++) {
            resolutions[i] = resolutions[i - 1] / 2;
        }

        return resolutions;
    }

    public void removeLayer(Layer layer) {
        removeComponent(layer);
    }

    /**
     * Note, this does not work until the map is rendered.
     *
     * @return
     */
    public Bounds getExtent() {
        return this.getState().bounds;
    }
    public void setExtent(Bounds extent) {
        this.getState().bounds = extent;
    }

    @Override
    public void addActionHandler(Action.Handler actionHandler) {
        if (actionHandler != null) {
            if (!actionHandlers.contains(actionHandler)) {
                actionHandlers.add(actionHandler);
            }
        }
    }

    @Override
    public void removeActionHandler(Action.Handler actionHandler) {
        if (actionHandlers.contains(actionHandler)) {
            actionHandlers.remove(actionHandler);
        }
    }

    public interface MapClickListener {

        Method method = ReflectTools.findMethod(
                MapClickListener.class, "mapClicked", MapClickEvent.class);

        void mapClicked(MapClickEvent event);

    }

    public void addMapClickListener(MapClickListener listener) {
        addListener("click", MapClickEvent.class, listener,
                MapClickListener.method);
    }

    public void removeMapClickListener(MapClickListener listener) {
        removeListener("click", MapClickEvent.class, listener);
    }

    public class MapClickEvent extends Event {

        private PointInformation pointInfo;

        public MapClickEvent(Component source, PointInformation pointInfo) {
            super(source);
            setPointInfo(pointInfo);

        }

        private void setPointInfo(PointInformation pointInfo) {
            this.pointInfo = pointInfo;

        }

        public PointInformation getPointInfo() {
            return pointInfo;
        }
    }

    public class ExtentChangeEvent extends Event {
        public ExtentChangeEvent() {
            super(OpenLayersMap.this);
        }

        @Override
        public OpenLayersMap getComponent() {
            return (OpenLayersMap) super.getComponent();
        }
    }

    public interface ExtentChangeListener {

        Method method = ReflectTools.findMethod(
                ExtentChangeListener.class, "extentChanged",
                ExtentChangeEvent.class);

        void extentChanged(ExtentChangeEvent event);

    }

    public void addExtentChangeListener(ExtentChangeListener listener) {
        addListener("extentChange", ExtentChangeEvent.class, listener,
                ExtentChangeListener.method);
    }

    public void removeExtentChangeListener(ExtentChangeListener listener) {
        removeListener(ExtentChangeEvent.class, listener);
    }

    public class BaseLayerChangeEvent extends Event {
        public BaseLayerChangeEvent() {
            super(OpenLayersMap.this);
        }

        @Override
        public OpenLayersMap getComponent() {
            return (OpenLayersMap) super.getComponent();
        }
    }

    public interface BaseLayerChangeListener {

        Method method = ReflectTools.findMethod(
                BaseLayerChangeListener.class, "baseLayerChanged",
                BaseLayerChangeEvent.class);

        void baseLayerChanged(BaseLayerChangeEvent event);
    }

    public void addBaseLayerChangeListener(BaseLayerChangeListener listener) {
        addListener("baseLayerChange", BaseLayerChangeEvent.class, listener,
                BaseLayerChangeListener.method);
    }

    public void removeBaseLayerChangeListener(BaseLayerChangeListener listener) {
        removeListener(BaseLayerChangeEvent.class, listener);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
        actionsToState();
    }

    @SuppressWarnings("Duplicates")
    private void actionsToState() {
        getState().actions.clear();
        for (Action.Handler ah : actionHandlers) {
            final Action[] actions = ah.getActions(this, this);
            if (actions != null) {
                for (Action action : actions) {
                    ContextMenuAction contextMenuAction = new ContextMenuAction();
                    String key = actionMapper.key(action);
                    contextMenuAction.key = key;
                    contextMenuAction.caption = action.getCaption();
                    getState().actions.add(contextMenuAction);
                    if (action.getIcon() != null) {
                        setResource(Constants.CONTEXT_MENU_ICON_RESOURCE_KEY + key, action.getIcon());
                    }

                }
            }
        }
    }

    public static class OpenLayersMapContextClickEvent extends ContextClickEvent {

        private final Point point;

        public OpenLayersMapContextClickEvent(OpenLayersMap source, MouseEventDetails mouseEventDetails, Point point) {
            super(source, mouseEventDetails);
            this.point = point;
        }

        public Point getPoint() {
            return point;
        }

        public OpenLayersMap getOpenLayersMap() {
            return (OpenLayersMap) getSource();
        }
    }
}
