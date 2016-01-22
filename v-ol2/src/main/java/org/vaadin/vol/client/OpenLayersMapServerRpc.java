package org.vaadin.vol.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface OpenLayersMapServerRpc extends ServerRpc {

    void mapClicked(PointInformation pointInformation);
    void extentChanged(Bounds bounds, int zoom);
    void baseLayerChanged(String connectorId);
    // for older Action based context menu
    void contextMenuClicked(String key, Point point);
    // for new Vaadin 7.6+ context menu API
    void contextClick(MouseEventDetails details, Point point);
}
