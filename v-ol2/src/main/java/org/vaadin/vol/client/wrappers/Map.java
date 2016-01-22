package org.vaadin.vol.client.wrappers;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import java.util.logging.Logger;

import org.vaadin.vol.client.wrappers.control.Control;
import org.vaadin.vol.client.wrappers.layer.Layer;
import org.vaadin.vol.client.wrappers.popup.Popup;

/**
 * A widget that contains open layers map. Proxys all relevant OpenLayers map
 * methods to the contained map.
 */
public class Map extends Widget {

    private static int sequense = 0;

    private MapOverlay jsoverlay;

    private final DivElement mapElement;

    private String mapInitOptions;

    @SuppressWarnings("unchecked")
    private JsArray<Control> initialControls = (JsArray<Control>) JsArray
            .createArray();

    private final Logger logger = Logger.getLogger(getClass().getName());

    public Map() {
        setElement(Document.get().createDivElement());
        mapElement = Document.get().createDivElement();
        Style style = mapElement.getStyle();
        style.setWidth(100, Unit.PCT);
        style.setHeight(100, Unit.PCT);
        setWidth("100%");
        setHeight("100%");
        getElement().appendChild(mapElement);
        String id = "VOLMAP_" + sequense++;
        mapElement.setId(id);
    }

    /**
     * @param mapInitOptions
     *            the js string that will be evaluated as options for the map.
     */
    public void setMapInitOptions(String mapInitOptions) {
        this.mapInitOptions = mapInitOptions;
        this.logger.info("Set initial map options to " + mapInitOptions);
    }

    public String getMapInitOptions() {
        return mapInitOptions;
    }

    private MapOverlay getMap() {
        if (jsoverlay == null) {
            this.logger.info("initializing map overlay to be attached to element " + mapElement.getId()
              + " with initial map options of " + mapInitOptions + " and controls " + initialControls);
            jsoverlay = MapOverlay.get(mapElement.getId(), mapInitOptions, initialControls);
        }
        return jsoverlay;
    }

    public void addControl(Control control) {
        if (jsoverlay == null) {
            this.logger.info("adding control " + control + " as initial map control");
            initialControls.push(control);
        } else {
            this.logger.info("adding control " + control);
            getMap().addControl(control);
        }
    }

    public void addLayer(Layer layer) {
        this.logger.info("adding layer " + layer + " to map");
        getMap().addLayer(layer);
    }

    public void removeLayer(Layer remove) {
        this.logger.info("removing layer " + remove + " from map");
        getMap().removeLayer(remove);
    }

    public void setCenter(LonLat lonLat, int zoom) {
        this.logger.info("setting center of map to " + lonLat + " at zoom level " + zoom);
        getMap().setCenter(lonLat, zoom);

    }

    public Projection getProjection() {
        return getMap().getProjection();
    }

    public void addPopup(Popup popup) {
        getMap().addPopup(popup);
    }

    public void removePopup(Popup popup) {
        getMap().removePopup(popup);
    }

    public Layer getLayer(String id) {
        return getMap().getLayer(id);
    }

    public void removeControl(Control control) {
        this.logger.info("removing control " + control + " from map");
        getMap().removeContol(control);
    }

    public Bounds getMaxExtent() {
        return getMap().getMaxExtent();
    }

    public int getZoom() {
        return getMap().getZoom();
    }

    public void setZoom(int zoom) {
        getMap().zoomTo(zoom);
    }

    public void registerEventHandler(String evtName, GwtOlHandler handler) {
        getMap().registerHandler(evtName, handler);
    }

    public Bounds getExtent() {
        return getMap().getExtent();
    }

    public void zoomToExtent(Bounds bounds) {
        getMap().zoomToExtent(bounds);
    }

    public void setRestrictedExtent(Bounds bounds) {
        getMap().setRestrictedExtent(bounds);
    }

    public Layer getBaseLayer() {
        return getMap().getBaseLayer();
    }

    public void setBaseLayer(Layer layer) {
        getMap().setBaseLayer(layer);
    }

    public JsArray<Control> getControls() {
        return getMap().getControls();
    }

    public LonLat getLonLatFromPixel(Pixel pixel) {
        return getMap().getLonLatFromPixel(pixel);
    }

    public void updateSize() {
        getMap().updateSize();
    }

}
