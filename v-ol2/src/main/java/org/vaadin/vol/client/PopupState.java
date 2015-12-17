package org.vaadin.vol.client;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;

public class PopupState extends AbstractComponentState {

    public enum PopupStyle {
        DEFAULT, ANCHORED, ANCHORED_BUBBLE, FRAMED, FRAMED_CLOUD
    }

    public String projection = "EPSG:4326";
    public PopupStyle popupstyle = PopupStyle.DEFAULT;
    public Connector anchor;
    public Connector content;
    public Point point = new Point(0, 0);
    public boolean closable = true;
}
