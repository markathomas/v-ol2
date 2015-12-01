package org.vaadin.vol.demo;

import java.util.Arrays;
import java.util.List;

import org.vaadin.vol.GoogleHybridMapLayer;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.Layer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenLayersMap.BaseLayerChangeEvent;
import org.vaadin.vol.OpenLayersMap.BaseLayerChangeListener;
import org.vaadin.vol.OpenStreetMapLayer;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

/**
 * Demonstrates changing base layer from server-side and
 * keeping client and server synchronized.
 */
public class ChangeBaseLayer extends AbstractVOLTest {

        private OpenLayersMap map = new OpenLayersMap();

    @Override
    public String getDescription() {    	
        return "Demonstrate changing base layer from server-side and keeping synchronized.";
    }

    @Override
    Component getMap() {
        
        OpenStreetMapLayer osm = new OpenStreetMapLayer();
        osm.setDisplayName("OSM Streets Base");
        
        GoogleStreetMapLayer gsm = new  GoogleStreetMapLayer();
        gsm.setDisplayName("Google Streets Base");
        
        GoogleHybridMapLayer ghm = new GoogleHybridMapLayer();
        ghm.setDisplayName("Google Hybrid Base");
        
        map.addLayer(osm);
        map.addLayer(gsm);
        map.addLayer(ghm);
        
        map.setCenter(-104.9, 38);
        map.setZoom(6);
        map.setSizeFull();
  
    	Label l1 = new Label("The map's base layer state should be synchronized between this OptionGroup and the OL LayerSwitcher.");
    	l1.setSizeUndefined();

    	final Layer[] layers = {osm, gsm, ghm};
    	final List<Layer> layerlist = Arrays.asList(layers);
    	final OptionGroup baseLayers = new OptionGroup("Base Layers", layerlist);
    	baseLayers.select(osm.getDisplayName());
    	baseLayers.setNullSelectionAllowed(false);
    	baseLayers.setImmediate(true);
    	baseLayers.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Layer newlayer = (Layer) event.getProperty().getValue();
                if (!newlayer.equals(map.getBaseLayer())) {
                    map.setBaseLayer(newlayer);
                }
            }
        });
    	
  
    	map.addListener(new BaseLayerChangeListener() {
            
            public void baseLayerChanged(BaseLayerChangeEvent event) {
                Layer newBaseLayer = map.getBaseLayer();
                if (!newBaseLayer.equals(baseLayers.getValue())) {
                    baseLayers.select(newBaseLayer);
                }
            }
        });
    	
    	VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.addComponent(l1);
        vl.addComponent(baseLayers);
        vl.addComponent(map);
        vl.setExpandRatio(map, 1f);
        
        return vl;
    }


}
