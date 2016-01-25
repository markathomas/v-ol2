package org.vaadin.vol.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

/**
 * RPC for new Vaadin 7.6+ context menu API.
 *
 * @author Henri Kerola / Vaadin
 */
public interface ContextClickRpc extends ServerRpc {

    void contextClick(MouseEventDetails details, Point point);
}
