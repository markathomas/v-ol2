package org.vaadin.vol.demo;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.addonhelpers.AbstractTest;

@SuppressWarnings("unchecked")
@JavaScript({"http://openlayers.org/api/2.13/OpenLayers.js", "http://www.openstreetmap.org/openlayers/OpenStreetMap.js"})
public abstract class AbstractVOLTest extends AbstractTest {

    protected VerticalLayout content;
    protected String contextPath;
}
