package org.vaadin.vol.client;

import java.io.Serializable;

public class UniqueValueRule implements Serializable {
    RenderIntent intent;
    String property;
    Symbolizer lookup;

    public UniqueValueRule() {

    }

    public UniqueValueRule(RenderIntent intent, String property, Symbolizer lookup) {
        this.intent = intent;
        this.property = property;
        this.lookup = lookup;
    }

    /**
     * @return the intent
     */
    public RenderIntent getIntent() {
        return intent;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the lookup
     */
    public Symbolizer getLookup() {
        return lookup;
    }
}
