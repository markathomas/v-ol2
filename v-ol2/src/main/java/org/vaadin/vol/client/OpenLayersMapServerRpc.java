package org.vaadin.vol.client;

import com.vaadin.shared.communication.ServerRpc;

import org.vaadin.vol.Bounds;
import org.vaadin.vol.PointInformation;

public interface OpenLayersMapServerRpc extends ServerRpc {

    void mapClicked(PointInformation pointInformation);
    void extentChanged(Bounds bounds, int zoom);
    void baseLayerChanged(String connectorId);
}
