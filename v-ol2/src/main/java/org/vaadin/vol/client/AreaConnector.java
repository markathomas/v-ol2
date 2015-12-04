package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.Area;
import org.vaadin.vol.client.ui.VArea;

@Connect(Area.class)
public class AreaConnector extends VectorConnector<VectorState> {

    @Override
    protected Widget createWidget() {
        return GWT.create(VArea.class);
    }

    @Override
    public VArea getWidget() {
        return (VArea)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
    }
}
