package org.vaadin.vol.client;

import com.vaadin.shared.communication.ServerRpc;

public interface LayerBaseServerRpc extends ServerRpc {

    void loadStarted(String layerName);
    void loadEnded(String layerName);
    void visibilityChanged(String layerName, boolean visible);
}
