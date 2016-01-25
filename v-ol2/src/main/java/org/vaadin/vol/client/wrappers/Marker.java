package org.vaadin.vol.client.wrappers;


public class Marker extends AbstractOpenLayersWrapper {

    protected Marker(){};

    public native static Marker create(LonLat lonlat, Icon icon)
    /*-{
        return new $wnd.OpenLayers.Marker(lonlat, icon);
    }-*/;

    /**
     * TODO better typed listeners and parameters.
     *
     * @param gwtOlHandler
     */
    public final void addClickHandler(GwtOlHandler gwtOlHandler) {
        registerHandler("click", gwtOlHandler);
    }

    public native final void addRightMouseButtonDownHandler(GwtOlHandler handler)
    /*-{
        var f = function(e) {
            if ($wnd.OpenLayers.Event.isRightClick(e)) {
                $entry(handler.@org.vaadin.vol.client.wrappers.GwtOlHandler::onEvent(Lcom/google/gwt/core/client/JsArray;)(arguments));
            }
        };
        this.events.register("mousedown", this, f);

    }-*/;
}
