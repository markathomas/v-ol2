package org.vaadin.vol.demo;

import com.vaadin.ui.Component;

import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenLayersMap.MapClickEvent;
import org.vaadin.vol.OpenLayersMap.MapClickListener;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Popup;

public class Issue72PopupHangsMap extends AbstractVOLTest {

    @Override
    public String getDescription() {
        return "Reloads should work with Popups.";
    }

    @Override
    Component getMap() {
        final OpenLayersMap map = new OpenLayersMap();
        map.setSizeFull();
        map.addLayer(new OpenStreetMapLayer());

        map.addMapClickListener(new MapClickListener() {

            public void mapClicked(MapClickEvent event) {
                final Popup popup = new Popup(event.getPointInfo().getLon(),
                        event.getPointInfo().getLat(), "FOOBAR");
                popup.addCloseListener(new Popup.CloseListener() {

                    public void onClose(org.vaadin.vol.Popup.CloseEvent event) {
                        map.removeComponent(popup);

                    }
                });
                map.addPopup(popup);
            }
        });

        return map;
    }

}
