package org.vaadin.vol.demo;

import org.vaadin.vol.Area;
import org.vaadin.vol.Layer;
import org.vaadin.vol.VectorLayer.SelectionMode;
import org.vaadin.vol.VectorLayer.VectorSelectedEvent;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.Style;
import org.vaadin.vol.StyleMap;
import org.vaadin.vol.VectorLayer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Demonstrates issue of only last VectorLayer added being able to listen for VectorSelected events.
 * With fix applied, each VectorLayer can listen to its own VectorSelected events.
 */
public class SelectMultipleVectorLayers extends AbstractVOLTest {

        private OpenLayersMap map = new OpenLayersMap();
	//private VectorLayer layer1;
	//private VectorLayer layer2;

    @Override
    public String getDescription() {    	
        return "Demonstrate listening for VectorSelected events from multiple layers.";
    }

    @Override
    Component getMap() {
        
        map.addLayer(new  OpenStreetMapLayer());
        map.setCenter(-104.9, 38);
        map.setZoom(6);
        map.setSizeFull();
 
    	HorizontalLayout hl = new HorizontalLayout();
    	hl.addComponent(createCheckBox("Layer 1", createLayer1()));        
    	hl.addComponent(createCheckBox("Layer 2", createLayer2()));
  
    	Label l1 = new Label("Without fix, only the last vector layer added to a map can handle VectorSelected events");
    	l1.setSizeUndefined();
    	Label l2 = new Label("Click checkboxes to add and remove layers");
    	l2.setSizeUndefined();
  
    	VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.addComponent(l1);
        vl.addComponent(hl);
        vl.addComponent(l2);
        vl.addComponent(map);
        vl.setExpandRatio(map, 1f);
        
        return vl;
    }

    private Component createCheckBox(String caption, final Layer layer) {
        CheckBox cb = new CheckBox(caption);
        cb.setImmediate(true);
        
        cb.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                addRemoveLayer((Boolean) event.getProperty().getValue(), layer);
            }
                
        });
        
        return cb;
    }
    
    private void addRemoveLayer(boolean addIt, Layer layer) {
        if (addIt) {
            map.addLayer(layer);
        } else {
            map.removeLayer(layer);
        }
        
    }
    
    private Layer createLayer1() {
    	VectorLayer layer1 = new VectorLayer();
    	layer1.setSelectionCtrlId("rz");
    	
    	layer1.addVector(new PointVector(-104.9, 40.8));
    	layer1.addVector(new PointVector(-105.6, 40.6));
    	layer1.addListener(new VectorLayer.VectorSelectedListener() {
    	    public void vectorSelected(VectorSelectedEvent event) {
    	        showNotification("Select on Layer1");
    	    }
    	});
    	layer1.setSelectionMode(SelectionMode.SIMPLE);	
    
    	Style s = new Style();
    	s.setPointRadius(15.0);
    	StyleMap sm = new StyleMap(s);
    	layer1.setStyleMap(sm);
    	
    	return layer1;
    }
    
    private Layer createLayer2() {
    	VectorLayer layer2 = new VectorLayer();
        layer2.setSelectionCtrlId("rz");
        
    	Point st = new Point(-103.8, 40.2);
    	double dx = 1.5;
    	double dy = 1.25;
    	
    	Point[] r1 = {
    	        st, new Point(st.getLon(), st.getLat() - dy),
    	        new Point(st.getLon() + dx, st.getLat() - dy), new Point(st.getLon() + dx, st.getLat()),
    	        st
    	};
    	Area a1 = new Area();
    	a1.setPoints(r1);
    	layer2.addVector(a1);
    	
    	double off = 1.3;
    	Point[]r2 = new Point[r1.length];
    	for (int i=0; i<r1.length; i++) {
    	    r2[i] = new Point(r1[i].getLon() + off, r1[i].getLat() + off);
    	}
    	Area a2 = new Area();
    	a2.setPoints(r2);
    	layer2.addVector(a2);
    	
    	layer2.addListener(new VectorLayer.VectorSelectedListener() {
    	    public void vectorSelected(VectorSelectedEvent event) {
    	        showNotification("Select on Layer2");

    	    }
    	}); 	
    	layer2.setSelectionMode(SelectionMode.SIMPLE);
  
        Style s = new Style();
        s.setFillColor("green");
        StyleMap sm = new StyleMap(s);
        layer2.setStyleMap(sm);
        
        return layer2;
    }


}
