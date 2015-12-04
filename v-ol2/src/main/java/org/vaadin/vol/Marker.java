/**
 *
 */
package org.vaadin.vol;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;

import org.vaadin.vol.client.MarkerServerRpc;
import org.vaadin.vol.client.MarkerState;

@SuppressWarnings("serial")
public class Marker extends AbstractComponent {

    public Marker(double lon, double lat) {
        this.registerRpc(new MarkerServerRpc() {
            public void markerClicked() {
                fireEvent(new ClickEvent(Marker.this, null));
            }
        });
        this.getState().lon = lon;
        this.getState().lat = lat;
    }

    @Override
    public MarkerState getState() {
        return (MarkerState)super.getState();
    }

    public double getLon() {
        return getState().lon;
    }

    public double getLat() {
        return getState().lat;
    }

    public void setLon(double lon) {
        this.getState().lon = lon;
        markAsDirty();
    }

    public void setLat(double lat) {
        this.getState().lat = lat;
        markAsDirty();
    }

    public void setIcon(String url, int width, int height) {
        this.setIcon(new ExternalResource(url), width, height, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void setIcon(String url, int width, int height, int xOffset, int yOffset) {
        this.setIcon(new ExternalResource(url), width, height, xOffset, yOffset);
    }

    public void setIcon(Resource icon, int width, int height) {
        this.setIcon(icon, width, height, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void setIcon(Resource icon, int width, int height, int xOffset, int yOffset) {
        getState().iconWidth = width;
        getState().iconHeight = height;
        getState().iconXOffset = xOffset;
        getState().iconYOffset = yOffset;
        setIcon(icon); // also calls markAsDirty()
    }

    public void addClickListener(ClickListener listener) {
        addListener("click", ClickEvent.class, listener,
                ClickListener.clickMethod);
        getState().hasClickListeners = true;
        markAsDirty();
    }

    public void removeClickListener(ClickListener listener) {
        removeListener(ClickEvent.class, listener);
        getState().hasClickListeners = !this.getListeners(ClickListener.class).isEmpty();
    }
}
