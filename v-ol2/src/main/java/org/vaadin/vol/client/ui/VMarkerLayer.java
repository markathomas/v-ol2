package org.vaadin.vol.client.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.Profiler;
import org.vaadin.vol.client.MapUtil;
import org.vaadin.vol.client.wrappers.Map;
import org.vaadin.vol.client.wrappers.layer.MarkerLayer;

public class VMarkerLayer extends FlowPanel implements VLayer {

    private MarkerLayer markers;
    private String displayName;
    private boolean layerAdded = false;

    public MarkerLayer getLayer() {
        if (markers == null) {
            markers = MarkerLayer.create(displayName);
        }
        return markers;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        if (markers != null) {
            markers.setDisplayName(displayName);
        }
    }

    /**
     * For internal use only. May be removed or replaced in the future.
     */
    public void addOrMove(Widget child, int index) {
        if (!layerAdded) {
            getMap().addLayer(getLayer());
            layerAdded = true;
        }
        Profiler.enter("VMarkerLayer.addOrMove");
        if (child.getParent() == this) {
            Profiler.enter("VMarkerLayer.addOrMove getWidgetIndex");
            int currentIndex = getWidgetIndex(child);
            Profiler.leave("VMarkerLayer.addOrMove getWidgetIndex");
            if (index == currentIndex) {
                Profiler.leave("VMarkerLayer.addOrMove");
                return;
            }
        } else if (index == getWidgetCount()) {
            // optimized path for appending components - faster especially for
            // initial rendering
            Profiler.enter("VMarkerLayer.addOrMove add");
            add(child);
            Profiler.leave("VMarkerLayer.addOrMove add");
            Profiler.leave("VMarkerLayer.addOrMove");
            return;
        }
        Profiler.enter("VMarkerLayer.addOrMove insert");
        insert(child, index);
        Profiler.leave("VMarkerLayer.addOrMove insert");
        Profiler.leave("VMarkerLayer.addOrMove");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (this.markers != null) {
            getMap().removeLayer(this.markers);
        }
    }

    protected Map getMap() {
        return MapUtil.getMap(getParent());
    }

    public boolean hasChildComponent(Widget component) {
        return getWidgetIndex(component) != -1;
    }
}
