/**
 *
 */
package org.vaadin.vol;

import org.vaadin.vol.client.GoogleMapLayerState;

/**
 * Google street layer that can be added to {@link OpenLayersMap}.
 * <p>
 * <strong>Note</strong> that to use this layer, developer must ensure the host
 * page has google map javascripts inlcuded. This can be achived for example by adding a following script tag to widgetset (...gwt.xml).
 *
 * <p>
 * <code>
 *  &lt;script src="http://maps.google.com/maps/api/js?v=3.2&amp;sensor=false"&gt;&lt;/script&gt;
 * </code>
 */
public class GoogleStreetMapLayer extends AbstractLayerBase implements Layer {

    @Override
    public GoogleMapLayerState getState() {
        return (GoogleMapLayerState)super.getState();
    }
}
