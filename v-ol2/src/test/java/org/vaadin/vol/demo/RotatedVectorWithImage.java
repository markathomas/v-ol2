package org.vaadin.vol.demo;

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.Style;
import org.vaadin.vol.VectorLayer;

import com.vaadin.ui.Component;

public class RotatedVectorWithImage extends AbstractVOLTest {
    @Override
    public String getDescription() {
        return "Simple vector with external graphic and rotation.";
    }

    @Override
    protected void setup() {
        super.setup();
    }

    @Override
    Component getMap() {
        final OpenLayersMap map = new OpenLayersMap();
        OpenStreetMapLayer osm = new OpenStreetMapLayer();
        map.addLayer(osm);

        final VectorLayer vectorLayer = new VectorLayer();
        
        PointVector pointVector2 = new PointVector(22.29, 60.45);

        Style s = new Style();
        s.setExternalGraphic("http://dev.vaadin.com/chrome/site/vaadin-trac.png");
        s.setGraphicHeight(60);
        s.setGraphicWidth(342);
        s.setProperty("rotation", 30);
        pointVector2.setCustomStyle(s);

        vectorLayer.addVector(pointVector2);
        
        map.addLayer(vectorLayer);
        
        map.setCenter(22.29, 60.45);


        return map;
    }



}
