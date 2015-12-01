package org.vaadin.vol.demo;

import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;

import com.vaadin.ui.Component;

public class CustomMarkerIcon extends AbstractVOLTest {

    private MarkerLayer markerLayer;
    private OpenLayersMap map;

    @Override
    public String getDescription() {
        return "Shows markers with custom icons. This example also shows how to specify an offset to the anchor point of the custom marker icon.";
    }

    @Override
    Component getMap() {
    	// create the map
        map = new OpenLayersMap();
        map.addLayer(new OpenStreetMapLayer());
        map.setSizeFull();
        
        // create the marker layer
        markerLayer = new MarkerLayer();
        map.addLayer(markerLayer);

        map.setCenter(22.30, 60.452);
        map.setZoom(14);

        // add marker
        addMarker(22.30, 60.452);

        return map;
    }

    @Override
    public void attach() {
        super.attach();
        showNotification(getDescription(), Notification.TYPE_WARNING_MESSAGE);
    }

    private void addMarker(double lon, double lat) {
    	// this example adds two markers with custom icons at the exact same coordinates, but for the
    	// second marker icon, an offset is specified that shifts the icon by the corresponding amount
    	// of pixels
        final Marker marker = new Marker(lon, lat);
        marker.setIcon("http://www.openlayers.org/dev/img/marker-green.png", 21, 25);        	
        markerLayer.addMarker(marker);
        
        final Marker markerWithOffset = new Marker(lon, lat);
        markerWithOffset.setIcon("http://www.openlayers.org/dev/img/marker-gold.png", 21, 25, -10, -25);        	
        markerLayer.addMarker(markerWithOffset);
    }
}
