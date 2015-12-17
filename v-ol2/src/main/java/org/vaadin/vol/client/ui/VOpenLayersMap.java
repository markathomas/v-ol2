package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.VConsole;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.ui.VLazyExecutor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.vol.client.wrappers.Bounds;
import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.Projection;
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

    private HashMap<String, Control> myControls = new HashMap<String, Control>();

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VOpenLayersMap() {
        setWidth("500px");
        setHeight("500px");
        add(map);

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }


    private int getWindowClickTopPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientY(event.getNativeEvent())
                + Window.getScrollTop();
    }

    private int getWindowClickLeftPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientX(event.getNativeEvent())
                + Window.getScrollLeft();
    }

    private double getMapClickTopPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientY(event.getNativeEvent())
                - getMap().getAbsoluteTop();
    }

    private double getMapClickLeftPosition(ContextMenuEvent event) {
        return WidgetUtil.getTouchOrMouseClientX(event.getNativeEvent())
                - getMap().getAbsoluteLeft();
    }

    /**
     * Called whenever an update is received from the server
     */
    public void updateFromUIDL(UIDL uidl, final ApplicationConnection client) {


        updateZoomAndCenter(uidl);

        if (uidl.hasAttribute("baseLayer")) {
            //Server side wants to change the base layer.
            VLayer baseLayer = (VLayer) uidl.getPaintableAttribute("baseLayer", client);
            getMap().setBaseLayer(baseLayer.getLayer());
        }
    }

    public void updateControls(Set<org.vaadin.vol.client.Control> controls) {
        HashSet<String> oldcontrols = new HashSet<String>(myControls.keySet());

        for (org.vaadin.vol.client.Control control : controls) {
            String name = control.name();
            if (oldcontrols.contains(name)) {
                oldcontrols.remove(name);
            } else {
                Control controlByName = Control.getControlByName(name, getMap());
                if (controlByName != null) {
                    map.addControl(controlByName);
                    myControls.put(name, controlByName);
                } else {
                    VConsole.error("Control not in OL build: " + name);
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

    private void updateZoomAndCenter(UIDL uidl) {

        // Skip for empty map
        if (getMap().getProjection() != null) {

            if (uidl.hasAttribute("ze_top")) {
                /*
                 * Zoom to extent
                 */
                double top = uidl.getDoubleAttribute("ze_top");
                double right = uidl.getDoubleAttribute("ze_right");
                double bottom = uidl.getDoubleAttribute("ze_bottom");
                double left = uidl.getDoubleAttribute("ze_left");
                Bounds bounds = Bounds.create(left, bottom, right, top);
                bounds.transform(getProjection(), getMap().getProjection());
                getMap().zoomToExtent(bounds);
                return;
            }

            int zoom = map.getZoom();
            if (uidl.hasAttribute("zoom")) {
                zoom = uidl.getIntAttribute("zoom");
                if (!uidl.hasAttribute("clat")) {
                    // just set zoom, no center position
                    map.setZoom(zoom);
                }
            }

            if (uidl.hasAttribute("clat")) {
                double lat = uidl.getDoubleAttribute("clat");
                double lon = uidl.getDoubleAttribute("clon");
                LonLat lonLat = LonLat.create(lon, lat);
                // expect center point to be in WSG84
                Projection projection = map.getProjection();
                lonLat.transform(getProjection(), projection);
                map.setCenter(lonLat, zoom);
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
        add(paintable, (Element) elementById.cast());
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
