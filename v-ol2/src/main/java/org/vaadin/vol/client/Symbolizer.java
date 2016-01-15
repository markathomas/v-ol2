package org.vaadin.vol.client;

import java.util.HashMap;
import java.util.Map;

public class Symbolizer {

    private Map<String, Object> map = new HashMap<String, Object>();

    public int size() {
        return map.size();
    }

    public void setProperty(String key, Object value) {
        map.put(key, value);
    }
}
