/**
 *
 */
package org.vaadin.vol;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.vol.client.MarkerLayerState;

public class MarkerLayer extends AbstractComponentContainer implements Layer {

    private List<Marker> markers = new LinkedList<Marker>();

    @Override
    public MarkerLayerState getState() {
        return (MarkerLayerState)super.getState();
    }

    public void addMarker(Marker m) {
        addComponent(m);
    }

    public void replaceComponent(Component oldComponent, Component newComponent) {
        removeComponent(oldComponent);
        addComponent(newComponent);
    }

    @Override
    public int getComponentCount() {
        return this.markers.size();
    }

    public Iterator<Component> iterator() {
        List<Component> list = new ArrayList<Component>(markers);
        return list.iterator();
    }

    @Override
    public void addComponent(Component c) {
        if (c instanceof Marker) {
            markers.add((Marker) c);
            super.addComponent(c);
            markAsDirty();
        } else {
            throw new IllegalArgumentException(
              "MarkerLayer supports only markers");
        }
    }

    @Override
    public void removeComponent(Component c) {
        if (c instanceof Marker) {
            super.removeComponent(c);
            markers.remove(c);
            markAsDirty();
        } else {
            throw new IllegalArgumentException(
              "MarkerLayer supports only markers");
        }
    }

    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
    }

    public String getDisplayName() {
        return getState().displayName;
    }

    public void removeMarker(Marker marker) {
        removeComponent(marker);
    }
}
