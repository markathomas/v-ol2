package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.JavaScriptObject;

import org.vaadin.vol.client.wrappers.Bounds;
import org.vaadin.vol.client.wrappers.layer.TileMapServiceLayer;

public class VMapTilerLayer extends VAbstracMapLayer<TileMapServiceLayer> {

    private String uri;
    private String layers;
    private Boolean baseLayer;
    private Double opacity;
    private String format;
    private boolean transparent;
    private Double[] bounds;
    private int minZoomLevel = 13;
    private int maxZoomLevel = 16;

    @Override
    TileMapServiceLayer createLayer() {
        return TileMapServiceLayer.create(getDisplayName(), uri, baseLayer,
                getGetUrlMethod(Bounds.create(bounds[0], bounds[1], bounds[2], bounds[3]), minZoomLevel, maxZoomLevel));
    }

    private native JavaScriptObject getGetUrlMethod(Bounds mapBounds,
            int minZoomLevel, int maxZoomLevel)
    /*-{
        function func(bounds) {
            var res = this.map.getResolution();
            var x = Math.round((bounds.left - -20037508) / (res * this.tileSize.w));
            var y = Math.round((bounds.bottom - -20037508) / (res * this.tileSize.h));
            var z = this.map.getZoom();
            if ((mapBounds.intersectsBounds( bounds ) || mapBounds.contains(bounds.getCenterLonLat())) && z >= minZoomLevel && z <= maxZoomLevel ) {
               return this.url + z + "/" + x + "/" + y + "." + this.type;
            } else {
               console.log("not in area??");
               return "http://www.maptiler.org/img/none.png";
            }
        };
        return func;
     }-*/;

    @Override
    public void attachLayerToMap() {
        TileMapServiceLayer layer2 = getLayer();
        Bounds maxExtent = layer2.getMaxExtent();
        if (maxExtent == null) {
            /*
             * TMS in openlaeyers is coded with bad habbits. They don't use
             * getters and max extent will inherit extent + tile origin from bad
             * projection.
             */
            Bounds maxExtent3 = getMap().getMaxExtent();
            layer2.setMaxExtent(maxExtent3);
        }
        super.attachLayerToMap();

    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLayers() {
        return this.layers;
    }

    public void setLayers(String layers) {
        this.layers = layers;
    }

    public Boolean isBaseLayer() {
        return this.baseLayer;
    }

    public void setBaseLayer(Boolean baseLayer) {
        this.baseLayer = baseLayer;
    }

    public Double getOpacity() {
        return this.opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public Double[] getBounds() {
        return this.bounds;
    }

    public void setBounds(Double[] bounds) {
        this.bounds = bounds;
    }

    public int getMinZoomLevel() {
        return this.minZoomLevel;
    }

    public void setMinZoomLevel(int minZoomLevel) {
        this.minZoomLevel = minZoomLevel;
    }

    public int getMaxZoomLevel() {
        return this.maxZoomLevel;
    }

    public void setMaxZoomLevel(int maxZoomLevel) {
        this.maxZoomLevel = maxZoomLevel;
    }
}
