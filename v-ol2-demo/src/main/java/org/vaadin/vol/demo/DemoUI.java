package org.vaadin.vol.demo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.Action;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;

@Theme("demo")


@Title("OpenLayers 2 Add-on Demo")
@JavaScript({"http://openlayers.org/api/2.13/OpenLayers.js", "http://www.openstreetmap.org/openlayers/OpenStreetMap.js"})
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.vol.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        final OpenLayersMap map = new OpenLayersMap();
        map.setApiProjection("EPSG:4326");
        map.setSizeFull();

        // base layers
        OpenStreetMapLayer osmLayer = new OpenStreetMapLayer();
        //osmLayer.setUrl("http://b.tile.openstreetmap.org/${z}/${x}/${y}.png");
        osmLayer.setDisplayName("OSM");
        map.addLayer(osmLayer);
        map.setBaseLayer(osmLayer);

        MarkerLayer markerLayer = new MarkerLayer();
        markerLayer.setDisplayName("Markers");
        map.addLayer(markerLayer);

        Marker marker = new Marker(146.9417, -42.0429);
        marker.setIcon("http://10.4.1.10/VAADIN/themes/ecx/img/icons/aim.png", 48, 48);
        marker.addActionHandler(new Action.Handler() {

            public Action[] getActions(Object target, Object sender) {
                return new Action[] {
                  new Action("Click Me!")
                };
            }

            public void handleAction(Action action, Object sender, Object target) {
                Notification.show("Marker context menu clicked");
            }
        });
        markerLayer.addMarker(marker);

        map.setCenter(146.9417, -42.0429);
        map.setZoom(7);

        setContent(map);

    }

}
