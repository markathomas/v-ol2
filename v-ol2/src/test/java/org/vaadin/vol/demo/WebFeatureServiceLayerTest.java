package org.vaadin.vol.demo;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import org.vaadin.vol.AbstractAutoPopulatedVectorLayer.FeatureSelectedEvent;
import org.vaadin.vol.AbstractAutoPopulatedVectorLayer.FeatureSelectedListener;
import org.vaadin.vol.client.Bounds;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.client.Point;
import org.vaadin.vol.client.Style;
import org.vaadin.vol.client.StyleMap;
import org.vaadin.vol.WebFeatureServiceLayer;
import org.vaadin.vol.WebMapServiceLayer;

/**
 * http://openlayers.org/dev/examples/wfs-states.js
 */
public class WebFeatureServiceLayerTest extends AbstractVOLTest {

    @Override
    public String getDescription() {
        return "Simple WFS example. The base map seems to be inaccessible. Should be changed to some public service that works.";
    }

    @Override
    public Component getTestComponent() {
        OpenLayersMap openLayersMap = new OpenLayersMap();
        WebMapServiceLayer webMapServiceLayer = new WebMapServiceLayer();
        webMapServiceLayer.setUri("http://tilecache.osgeo.org/wms-c/Basic.py");
        webMapServiceLayer.setBaseLayer(true);
        webMapServiceLayer.setDisplayName("Base map");
        openLayersMap.addLayer(webMapServiceLayer);

        WebFeatureServiceLayer webFeatureServiceLayer = new WebFeatureServiceLayer();

        webFeatureServiceLayer.addFeatureSelectedListener(new FeatureSelectedListener() {
            public void featureSelected(FeatureSelectedEvent event) {
                String featureId = event.getFeatureId();
                System.err.println("Selected feature id:" + featureId);
                Object state = event.getAttributes().get("STATE_NAME");
                Object persons = event.getAttributes().get("PERSONS");
                Notification.show("State: " + state + " (population:" + persons
                  + ")");
            }
        });

        // proxied to http://demo.opengeo.org/geoserver/wfs
        webFeatureServiceLayer.setUri(contextPath + "/WFSPROXY/");
        webFeatureServiceLayer.setFeatureType("states");
        webFeatureServiceLayer.setFeatureNS("http://www.openplans.org/topp");

        /*
         * Style like a normal web feature server.
         */

        Style style = new Style();
        style.extendCoreStyle("default");
        style.setFillColor("green");
        style.setFillOpacity(0.5);
        StyleMap styleMap = new StyleMap(style);
        styleMap.setExtendDefault(true);
        webFeatureServiceLayer.setStyleMap(styleMap);

        openLayersMap.addLayer(webFeatureServiceLayer);

        Bounds bounds = new Bounds(new Point(-140.4, 25.1), new Point(-44.4,
                50.5));
        openLayersMap.zoomToExtent(bounds);

        openLayersMap.setSizeFull();

        return openLayersMap;
    }

}
