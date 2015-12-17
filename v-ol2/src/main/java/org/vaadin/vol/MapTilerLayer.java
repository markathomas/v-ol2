/**
 *
 */
package org.vaadin.vol;

import com.vaadin.ui.AbstractComponent;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.vaadin.vol.client.MapTilerLayerState;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * see great tool: www.maptiler.org
 */
public class MapTilerLayer extends AbstractComponent implements Layer {

    private static final double MARGIN = 0.0001;


    public MapTilerLayer(Double[] bounds, int maxZoom, int minZoom) {
        this.setMaxZoom(maxZoom);
        this.setMinZoom(minZoom);
        this.setBounds(bounds);
    }

    public MapTilerLayer(String url) throws SAXException, IOException,
            ParserConfigurationException {
        setUri(url);
        URL url2 = new URL(url + "tilemapresource.xml");
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(url2.openStream());
        Node bbox = doc.getElementsByTagName("BoundingBox").item(0);

        // yes, these a fucked up, probably in maptiler xml file
        double bottom = Double.parseDouble(bbox.getAttributes()
                .getNamedItem("minx").getNodeValue());
        double left = Double.parseDouble(bbox.getAttributes()
                .getNamedItem("miny").getNodeValue());
        double top = Double.parseDouble(bbox.getAttributes()
                .getNamedItem("maxx").getNodeValue());
        double right = Double.parseDouble(bbox.getAttributes()
                .getNamedItem("maxy").getNodeValue());
        setBounds(new Double[] {left-MARGIN,bottom-MARGIN,right+MARGIN,top+MARGIN });

        Node tilesets = doc.getElementsByTagName("TileSets").item(0);

        for (int i = 0; i < tilesets.getChildNodes().getLength(); i++) {
            Node item = tilesets.getChildNodes().item(i);
            if(!item.getNodeName().equals("TileSet")) {
                continue;
            }
            int z = Integer.parseInt(item.getAttributes().getNamedItem("order")
                    .getNodeValue());
            if (getMaxZoom() == -1) {
                setMaxZoom(setMinZoom(z));
            } else if (z > getMaxZoom()) {
                setMaxZoom(z);
            } else if (z < getMinZoom()) {
                setMinZoom(z);
            }
        }
    }

    @Override
    public MapTilerLayerState getState() {
        return (MapTilerLayerState)super.getState();
    }

    public void setUri(String uri) {
        this.getState().uri = uri;
        markAsDirty();
    }

    public void setBaseLayer(boolean isBaseLayer) {
        this.getState().baseLayer = isBaseLayer;
        markAsDirty();
    }

    public boolean isBaseLayer() {
        return getState().baseLayer;
    }

    public void setOpacity(Double opacity) {
        this.getState().opacity = opacity;
        markAsDirty();
    }

    public Double getOpacity() {
        return getState().opacity;
    }

    public String getDisplayName() {
        return getState().displayName;
    }

    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
        markAsDirty();

    }

    public String getUri() {
        return getState().uri;
    }

    public void setLayers(String layers) {
        this.getState().layers = layers;
        markAsDirty();
    }

    public String getLayer() {
        return getState().layers;
    }

    public void setTransparent(Boolean transparent) {
        this.getState().transparent = transparent;
        markAsDirty();
    }

    public Boolean getTransparent() {
        return getState().transparent;
    }

    public void setBounds(Double[] bounds) {
        this.getState().bounds = bounds;
        markAsDirty();
    }

    public Double[] getBounds() {
        return getState().bounds;
    }

    public int setMinZoom(int minZoom) {
        this.getState().minZoomLevel = minZoom;
        markAsDirty();
        return minZoom;
    }

    public int getMinZoom() {
        return getState().minZoomLevel;
    }

    public void setMaxZoom(int maxZoom) {
        this.getState().maxZoomLevel = maxZoom;
        markAsDirty();
    }

    public int getMaxZoom() {
        return getState().maxZoomLevel;
    }
}
