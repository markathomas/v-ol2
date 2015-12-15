package org.vaadin.vol.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.PolyLine;
import org.vaadin.vol.client.ui.VPolyLine;

@Connect(PolyLine.class)
public class PolyLineConnector extends VectorConnector {

    @Override
    public VPolyLine getWidget() {
        return (VPolyLine)super.getWidget();
    }

    @Override
    public VPolyLine createWidget() {
        return GWT.create(VPolyLine.class);
    }
}
