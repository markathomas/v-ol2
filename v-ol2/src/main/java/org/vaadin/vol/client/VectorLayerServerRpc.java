package org.vaadin.vol.client;

import com.vaadin.shared.communication.ServerRpc;

public interface VectorLayerServerRpc extends ServerRpc {

    void draw(String[] vertices, VectorLayerState.DrawingMode drawingMode);
    void modify(String[] vertices, String connectorId);
    void select(String connectorId);
    void unselect(String connectorId);
}
