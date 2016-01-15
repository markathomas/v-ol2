package org.vaadin.vol.client;

import com.vaadin.client.Util;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import org.vaadin.vol.client.ui.VAbstractVector;
import org.vaadin.vol.client.ui.VVectorLayer;
import org.vaadin.vol.client.wrappers.Projection;

public abstract class VectorConnector<E extends VectorState> extends AbstractComponentConnector {

    @Override
    @SuppressWarnings("unchecked")
    public E getState() {
        return (E)super.getState();
    }

    @Override
    @SuppressWarnings("unchecked")
    public VAbstractVector<E> getWidget() {
        return (VAbstractVector<E>)super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        boolean update = getWidget().getVector() != null;
        if (update) {
            // temporary remove erase the vector
            getWidget().getLayer().eraseFeature(getWidget().getVector());
        }

        if (stateChangeEvent.hasPropertyChanged("attributesJson")) {
            getWidget().setAttributes(Util.parse(getState().attributesJson));
        }

        getWidget().createOrUpdateVector(stateChangeEvent, getState());

        if (stateChangeEvent.hasPropertyChanged("intent")) {
            getWidget().setIntent(getState().intent);
            getWidget().getVector().setRenderIntent(getState().intent);
        }

        if (stateChangeEvent.hasPropertyChanged("styleJson")) {
            getWidget().getVector().setStyle(Util.parse(getState().styleJson));
        }

        if (update) {
            ((VVectorLayer)getWidget().getParent()).vectorUpdated(getWidget());
        } else {
            getWidget().getLayer().addFeature(getWidget().getVector());
        }
    }

    @OnStateChange("projection")
    void projectionChanged() {
        if (getState().projection != null) {
            getWidget().setProjection(Projection.get(getState().projection));
        } else {
            getWidget().setProjection(null);
        }
    }
}
