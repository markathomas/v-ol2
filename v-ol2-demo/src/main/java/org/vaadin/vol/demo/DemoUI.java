package org.vaadin.vol.demo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.client.Bounds;
import org.vaadin.vol.client.Point;

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
        //map.setApiProjection("EPSG:4326");
        //map.setExtent(new Bounds(new Point(-180d, -90d), new Point(180d, 90d)));
        map.setSizeFull();

        // base layers
        OpenStreetMapLayer osmLayer = new OpenStreetMapLayer();
        //osmLayer.setUrl("http://b.tile.openstreetmap.org/${z}/${x}/${y}.png");
        osmLayer.setDisplayName("OSM");
        map.addLayer(osmLayer);
        map.setBaseLayer(osmLayer);

        map.setCenter(146.9417, -42.0429);
        map.setZoom(7);

        setContent(map);

    }

}
