package org.vaadin.vol.client;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.Area;
import org.vaadin.vol.client.ui.VArea;

@Connect(Area.class)
public class AreaConnector extends VectorConnector<VectorState> {

    @Override
    public VArea getWidget() {
        return (VArea)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
    }
}
