/**
 *
 */
package org.vaadin.vol;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;

import org.vaadin.vol.client.PopupServerRpc;
import org.vaadin.vol.client.PopupState;

@SuppressWarnings("serial")
public class Popup extends AbstractComponentContainer {

    public class CloseEvent extends Event {
        public CloseEvent() {
            super(Popup.this);
        }
    }

    public interface CloseListener {
        void onClose(CloseEvent event);

        Method method = ReflectTools.findMethod(CloseListener.class, "onClose", CloseEvent.class);
        String id = "close";
    }

    private final transient PopupServerRpc popupServerRpc = new PopupServerRpc() {
        public void popupClicked() {
            fireEvent(new ClickEvent(Popup.this, null));
        }

        public void popupClosed() {
            if (hasListeners(CloseEvent.class)) {
                fireEvent(new CloseEvent());
            } else {
                Component parent2 = getParent();
                if (parent2 instanceof OpenLayersMap) {
                    OpenLayersMap olm = (OpenLayersMap)parent2;
                    olm.removeComponent(Popup.this);
                }
            }
        }
    };

    public Popup(double lon, double lat, String content) {
        getState().point = new Point(lon, lat);
        setContent(content);
        registerRpc(this.popupServerRpc);
    }

    public Popup(String content) {
        setContent(content);
        registerRpc(this.popupServerRpc);
    }

    public Popup(Component content) {
        addComponent(content);
        registerRpc(this.popupServerRpc);
    }

    public Popup() {
        this("");
    }

    @Override
    public PopupState getState() {
        return (PopupState)super.getState();
    }

    public double getLon() {
        return getState().point.getLon();
    }

    public double getLat() {
        return getState().point.getLat();
    }

    public void setLon(double lon) {
        getState().point.setLon(lon);
        markAsDirty();
    }

    public void setLat(double lat) {
        getState().point.setLat(lat);
        markAsDirty();
    }

    public void setContent(String content) {
        Label c = new Label(content, ContentMode.HTML);
        c.setSizeUndefined();
        addComponent(c);
        markAsDirty();
    }

    @Override
    public void addComponent(Component c) {
        if (c == null)  {
            setContent("");
        } else {
            if (getState().content != null) {
                removeAllComponents();
            }
            super.addComponent(c);
            getState().content = c;
            markAsDirty();
        }
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
        if (getState().anchor == null
          && (getState().popupstyle == PopupState.PopupStyle.FRAMED || getState().popupstyle == PopupState.PopupStyle.ANCHORED
            || getState().popupstyle == PopupState.PopupStyle.ANCHORED_BUBBLE)) {
            throw new IllegalStateException("Anchor element hasn't been defined, but is required for this type of popup.");
        }
    }

    public void addClickListener(ClickListener listener) {
        addListener("click", ClickEvent.class, listener, ClickListener.clickMethod);
    }

    public void removeClickListener(ClickListener listener) {
        removeListener(ClickEvent.class, listener);
    }


    public void setPopupStyle(PopupState.PopupStyle style) {
        getState().popupstyle = style;
        markAsDirty();
    }

    public PopupState.PopupStyle getPopupStyle() {
        return getState().popupstyle;
    }

    public void setAnchor(Marker marker) {
        getState().anchor = marker;
        markAsDirty();
    }

    public void addCloseListener(CloseListener listener) {
        super.addListener(CloseListener.id, CloseEvent.class, listener, CloseListener.method);
    }

    public void removeCloseListener(CloseListener listener) {
        super.removeListener(CloseListener.id, CloseEvent.class, listener);
    }

    public boolean isClosable() {
        return getState().closable;
    }

    public void setClosable(boolean closable) {
        this.getState().closable = closable;
    }

    public void replaceComponent(Component oldComponent, Component newComponent) {
        removeComponent(oldComponent);
        addComponent(newComponent);
    }

    @Override
    public int getComponentCount() {
        return 1;
    }

    public Iterator<Component> iterator() {
        return Collections.singleton((Component)getState().content).iterator();
    }

    @Override
    public void removeComponent(Component c) {
        if (c == getState().content) {
            super.removeComponent(c);
            getState().content = null;
            markAsDirty();
        } else {
            throw new IllegalArgumentException("Given component is not in this popup");
        }
    }

    public void setContent(Table table) {
        addComponent(table);
    }
}
