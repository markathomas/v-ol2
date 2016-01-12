package org.vaadin.vol.demo;

import com.vaadin.ui.Component;

import org.vaadin.vol.LabelVector;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.VectorLayer;
import org.vaadin.vol.client.Point;
import org.vaadin.vol.client.Style;
import org.vaadin.vol.client.StyleMap;

@SuppressWarnings("serial")
public class TextOnVectorLayer extends AbstractVOLTest {

    private VectorLayer vectorLayer;
    private OpenLayersMap map;

    @Override
    public String getDescription() {
        return "Displaying texts on a VectorLayer.";
    }

    @Override
    public Component getTestComponent() {
        if (map == null) {

            map = new OpenLayersMap();

            OpenStreetMapLayer osm = new OpenStreetMapLayer();

            map.setSizeFull();

            // base layers
            map.addLayer(osm);

            vectorLayer = new VectorLayer();


            StyleMap styleMap = new StyleMap();

            /*
             * Text to VectorLayer via styling a point vector (or any other vector)
             */
            PointVector pointVector = new PointVector(0, 0);
            Style styleCircleWithText = new Style();

            styleCircleWithText.setLabel("Vaadin with OpenLayers is great!");
            styleCircleWithText.setFontColor("cyan");

            // also
            styleCircleWithText.setPointRadius(100);
            styleCircleWithText.setFillColor("#000");
            styleCircleWithText.setFillOpacity(0.5);

            styleMap.setStyle("circleWithText", styleCircleWithText);
            pointVector.setRenderIntent("circleWithText");

            vectorLayer.addComponent(pointVector);


            /*
             * Practically same thing with LabelVector.
             */
            LabelVector labelVector = new LabelVector("Simple, flexible...");
            labelVector.getCustomStyle().setFontColor("red");
            labelVector.setPoints(new Point(5,-5));
            vectorLayer.addVector(labelVector);

            labelVector = new LabelVector("...and java.");
            labelVector.setPoints(new Point(0,-10));

            Style styleRed = new Style();
            styleRed.fill(false);
            styleRed.stroke(false);
            styleRed.setFontColor("purple");
            styleRed.setFontWeight("bold");
            // if using render intents with labels, label must be defined as $label
            styleRed.setLabelByAttribute("label");
            styleMap.setStyle("red", styleRed);

            labelVector.setRenderIntent("red");
            vectorLayer.addComponent(labelVector);

            vectorLayer.setStyleMap(styleMap);

            map.addLayer(vectorLayer);
        }
        return map;
    }


}
