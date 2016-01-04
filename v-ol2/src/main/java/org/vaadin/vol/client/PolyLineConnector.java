package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.PolyLine;
import org.vaadin.vol.client.ui.VPolyLine;

@Connect(PolyLine.class)
public class PolyLineConnector extends VectorConnector {

    @Override
    public VPolyLine getWidget() {
        return (VPolyLine)super.getWidget();
    }
}
