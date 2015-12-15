package org.vaadin.vol.client.wrappers;

import com.google.gwt.core.client.JsArray;

import java.io.Serializable;

/**
 * Untyped bare bones handler interface for quick prototyping.
 */
public interface GwtOlHandler extends Serializable {
    @SuppressWarnings("rawtypes")
    public void onEvent(JsArray arguments);
}
