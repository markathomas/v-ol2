package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.ui.VLazyExecutor;
import com.vaadin.shared.Connector;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.vaadin.vol.client.wrappers.*;
import org.vaadin.vol.client.wrappers.control.Control;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VOpenLayersMap extends FlowPanel {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-openlayersmap";

    /**
     * Projection of coordinates passed from the serverside
     */
    private Projection serverSideProjection = Projection.get("EPSG:4326");

    private Map map = new Map();

    private HashMap<String, Widget> components = new HashMap<String, Widget>();
    private FlowPanel fakePaintables = new FlowPanel();

    private HashMap<String, Control> myControls = new HashMap<String, Control>();

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VOpenLayersMap() {
        setWidth("100%");
        setHeight("100%");
        add(map);
        add(fakePaintables);
        fakePaintables.setVisible(false);

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }

    public FlowPanel getFakePaintables() {
        return fakePaintables;
    }

    public int getWindowClickTopPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientY(event.getNativeEvent())
                + Window.getScrollTop();
    }

    public int getWindowClickLeftPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientX(event.getNativeEvent())
                + Window.getScrollLeft();
    }

    public double getMapClickTopPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientY(event.getNativeEvent())
                - getMap().getAbsoluteTop();
    }

    public double getMapClickLeftPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientX(event.getNativeEvent())
                - getMap().getAbsoluteLeft();
    }

    public void updateLayers(Collection<Connector> layers) {
        Set<String> orphanedComponents = new HashSet<String>(components.keySet());

        for (Connector layer : layers) {
            orphanedComponents.remove(layer.getConnectorId());
            Widget widget = ((ComponentConnector)layer).getWidget();
            if (!components.containsKey(layer.getConnectorId())) {
                components.put(layer.getConnectorId(), widget);
                fakePaintables.add(widget);
            }
        }

        for (String id : orphanedComponents) {
            Widget remove = components.remove(id);
            fakePaintables.remove(remove);
        }
    }

    public void updateControls(Set<String> controls) {
        this.logger.info("Updating widget with " + controls.size() + " controls!");
        HashSet<String> oldcontrols = new HashSet<String>(myControls.keySet());

        for (String name : controls) {
            this.logger.info("Handling addition of " + name + " control");
            if (oldcontrols.contains(name)) {
                oldcontrols.remove(name);
            } else {
                Control controlByName = Control.getControlByName(name, getMap());
                this.logger.info("Adding control " +  name + " to map");
                if (controlByName != null) {
                    map.addControl(controlByName);
                    myControls.put(name, controlByName);
                } else {
                    this.logger.severe("Control not in OL build: " + name);
                }
            }
        }

        for (String string : oldcontrols) {
            Control control = myControls.get(string);
            if (control != null) {
                map.removeControl(control);
            }
            myControls.remove(string);
        }
    }

    public void zoomToExtent(Bounds bounds) {

        // Skip for empty map
        if (getMap().getProjection() != null) {
            bounds.transform(getProjection(), getMap().getProjection());
            getMap().zoomToExtent(bounds);
        }
    }

    public void updateZoomAndCenter(Integer newZoom, LonLat center) {

        // Skip for empty map
        if (getMap().getProjection() != null) {

            int zoom = map.getZoom();
            if (newZoom != null) {
                zoom = newZoom;
                if (center == null) {
                    // just set zoom, no center position
                    map.setZoom(zoom);
                }
            }

            if (center != null) {
                // expect center point to be in WSG84
                Projection projection = map.getProjection();
                center.transform(getProjection(), projection);
                map.setCenter(center, zoom);
            }
        }
    }

    public Map getMap() {
        return map;
    }

    /**
     * @return the projection this map widget uses on the server side
     */
    public Projection getProjection() {
        return serverSideProjection;
    }

    /**
     * @param projection
     *            the projection this map widget uses on the server side
     */
    public void setProjection(Projection projection) {
        serverSideProjection = projection;
    }

    public void attachSpecialWidget(Widget paintable,
            com.google.gwt.dom.client.Element elementById) {
        add(paintable, elementById);
    }

    VLazyExecutor resizeMap = new VLazyExecutor(300, new ScheduledCommand() {
        public void execute() {
            map.updateSize();
        }
    });

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        resizeMap.trigger();
    }

    @Override
    public void setHeight(String height) {
        super.setHeight(height);
        resizeMap.trigger();
    }
}
