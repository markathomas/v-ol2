package org.vaadin.vol.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import org.vaadin.vol.client.PopupState;
import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.wrappers.GwtOlHandler;
import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.Marker;
import org.vaadin.vol.client.wrappers.Projection;
import org.vaadin.vol.client.wrappers.Size;
import org.vaadin.vol.client.wrappers.popup.Popup;
import org.vaadin.vol.client.wrappers.popup.PopupAnchored;
import org.vaadin.vol.client.wrappers.popup.PopupAnchoredBubble;
import org.vaadin.vol.client.wrappers.popup.PopupFramed;
import org.vaadin.vol.client.wrappers.popup.PopupFramedCloud;

public class VPopup extends Widget {

    private Popup popup;
    private Widget paintable;

    public VPopup() {
        setElement(Document.get().createDivElement());
    }

    public void hide() {
        popup.hide();
    }

    public void updatePopup(final PopupState state, GwtOlHandler closeEventHandler) {
        /*
         * With Popups, we always remove the old one and add a new one.
         */

        if (popup != null) {
            getMap().removePopup(popup);
        }
        double lon = state.point.getLon();
        double lat = state.point.getLat();
        final Projection projection = Projection.get(state.projection);
        LonLat point = LonLat.create(lon, lat);
        Projection projection2 = getMap().getProjection();
        point.transform(projection, projection2);

        String content = "Loading...";

        // TODO remove marker dependency
        Marker anchor = null;
        if (state.anchor != null) {
            anchor = ((VMarker)state.anchor).getMarker();
        }

        paintable = (Widget)state.content;

        boolean closable = state.closable;

        final String pid = state.content.getConnectorId() + "popup";
        switch (state.popupstyle) {
        case ANCHORED:
            popup = PopupAnchored.create(pid, point, null, content, anchor,
                    closable, closeEventHandler);
            break;
        case ANCHORED_BUBBLE:
            popup = PopupAnchoredBubble.create(pid, point, null, content,
                    anchor, closable, closeEventHandler);
            break;
        case FRAMED:
            popup = PopupFramed.create(pid, point, null, content, anchor,
                    closable, closeEventHandler);
            break;
        case FRAMED_CLOUD:
            popup = PopupFramedCloud.create(pid, point, null, content, anchor,
                    closable, closeEventHandler);
            break;

        case DEFAULT:
        default:
            popup = Popup.create(pid, point, null, content, closable,
                    closeEventHandler);
            break;
        }

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                getMap().addPopup(popup);
                Element elementById = Document.get().getElementById(pid + "_contentDiv");
                elementById.setInnerHTML("");
                VOpenLayersMap parent2 = (VOpenLayersMap)getParent();
                parent2.attachSpecialWidget(paintable, elementById);
                int offsetHeight = paintable.getOffsetHeight();
                int offsetWidth = paintable.getOffsetWidth();
                popup.setSize(Size.create(offsetWidth, offsetHeight));
            }
        });

    }

    private Map getMap() {
        return MapUtil.getMap(getParent());
    }

    public Popup getPopup() {
        return popup;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        paintable.removeFromParent();
        popup.hide();
        //client.unregisterPaintable(paintable);
    }

}
