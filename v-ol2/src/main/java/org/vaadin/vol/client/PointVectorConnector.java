package org.vaadin.vol.client;

import com.vaadin.shared.ui.Connect;

import org.vaadin.vol.PointVector;
import org.vaadin.vol.client.ui.VPointVector;

@Connect(PointVector.class)
public class PointVectorConnector extends VectorConnector {

    @Override
    public VPointVector getWidget() {
        return (VPointVector)super.getWidget();
    }
}
