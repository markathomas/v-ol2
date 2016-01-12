package org.vaadin.vol.demo;

import org.vaadin.vol.OpenLayersMap;

import com.vaadin.ui.Component;

public class EmptyMap extends AbstractVOLTest {

    @Override
    public String getDescription() {
        return "Empty map shouldn't cause any errors.";
    }

    @Override
    public Component getTestComponent() {
        OpenLayersMap map = new OpenLayersMap();

        map.setSizeFull();

        return map;
    }

}
