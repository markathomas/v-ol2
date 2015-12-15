package org.vaadin.vol.client;

import com.vaadin.shared.communication.ServerRpc;

public interface PopupServerRpc extends ServerRpc {

    void popupClicked();
    void popupClosed();
}
