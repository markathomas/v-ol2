package org.vaadin.vol.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

import org.vaadin.vol.client.MarkerState;
import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.wrappers.Icon;
import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.Marker;
import org.vaadin.vol.client.wrappers.Pixel;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Size;
import org.vaadin.vol.client.wrappers.layer.MarkerLayer;

public class VMarker extends Widget implements VMarkable {

    protected Marker marker;

    public VMarker() {
        setElement(Document.get().createDivElement());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vaadin.terminal.gwt.client.Paintable#updateFromUIDL(com.vaadin.terminal
     * .gwt.client.UIDL, com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    public <E extends MarkerState> void updateFromStateChange(E state, com.vaadin.client.ui.Icon icon) {

        if (marker != null) {
            getLayer().removeMarker(marker);
        }

        this.beforeMarkerCreation(state);

        this.marker = this.createMarker(state, icon);

        this.afterMarkerCreation(state);

        getLayer().addMarker(marker);
    }

    protected <E extends MarkerState> Marker createMarker(E state, com.vaadin.client.ui.Icon icon) {
        LonLat point = createPoint(state);

        Icon markerIcon = getIcon(state, icon);
        return Marker.create(point, markerIcon);
    }

    protected <E extends MarkerState> LonLat createPoint(E state) {
        double lon = state.lon;
        double lat = state.lat;
        LonLat point = LonLat.create(lon, lat);
        this.reproject(state, point);
        return point;
    }

    protected <E extends MarkerState> void reproject(E state, LonLat point) {
        Projection projection2 = getMap().getProjection();
        final Projection projection = Projection.get(state.projection);
        point.transform(projection, projection2);
    }

    protected <E extends MarkerState> Icon getIcon(E state, com.vaadin.client.ui.Icon icon) {
        if (icon != null) {
            String url = icon.getUri();
            int width = state.iconWidth > 0 ? state.iconWidth : 32;
            int height = state.iconHeight > 0 ? state.iconHeight : 32;
            if (state.iconXOffset > Integer.MIN_VALUE && state.iconYOffset > Integer.MIN_VALUE) {
                return Icon.create(url, Size.create(width, height),
                        Pixel.create(state.iconXOffset, state.iconYOffset));
            }
            return Icon.create(url, Size.create(width, height));
        }
        return null;
    }

    protected <E extends MarkerState> void beforeMarkerCreation(E state) {
    }

    protected <E extends MarkerState> void afterMarkerCreation(E state) {

    }

    protected MarkerLayer getLayer() {
        return MapUtil.findParent(VMarkerLayer.class, getParent()).getLayer();
    }

    protected Map getMap() {
        return MapUtil.getMap(getParent());
    }

    public Marker getMarker() {
        return marker;
    }

    @Override
    protected void onDetach() {
        if (marker != null) {
            getLayer().removeMarker(marker);
        }
        super.onDetach();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (marker != null) {
            getLayer().addMarker(marker);
        }
    }

}
