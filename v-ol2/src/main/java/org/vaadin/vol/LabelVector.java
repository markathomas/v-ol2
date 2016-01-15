package org.vaadin.vol;

import org.vaadin.vol.client.Attributes;
import org.vaadin.vol.client.Style;

public class LabelVector extends PointVector {


    @Override
    public void setCaption(String caption) {
        super.setCaption(caption);
        setTextToSymbolizerOrAsLabelAttribute(caption);
   }

    private void setTextToSymbolizerOrAsLabelAttribute(String caption) {
        if(getStyleName().isEmpty()) {
            Style customStyle = getCustomStyle();
            if(customStyle == null) {
                customStyle = new Style();
            }
            setCustomStyle(customStyle);
            customStyle.setLabel(caption);
        } else {
            Attributes attributes = getAttributes();
            if(attributes == null) {
                attributes = new Attributes();
                setAttributes(attributes);
            }
            attributes.setLabel(caption);
            setCustomStyle(null);
        }
    }

    public LabelVector() {
    }

    public LabelVector(String label) {
        setCaption(label);
    }

    @Override
    public void setStyleName(String style) {
        boolean wasEmpty = getStyleName().isEmpty();
        super.setStyleName(style);
        if(wasEmpty != (style == null)) {
            setTextToSymbolizerOrAsLabelAttribute(getCaption());
        }
    }
}
