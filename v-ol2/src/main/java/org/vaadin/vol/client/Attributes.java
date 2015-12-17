package org.vaadin.vol.client;

import java.io.Serializable;
import java.util.Map;

/**
 * Attibutes class wraps a JavaScript Object
 *
 *
 */
public class Attributes extends JsObject {

    /**
     * return Attributes as a Map arrays
     */
    public Map<String, Serializable> getAttributes() {
        return this.getKeyValueMap();
    }
}
