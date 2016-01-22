package org.vaadin.vol.client;

import com.google.gwt.user.client.ui.Widget;
import org.vaadin.vol.client.ui.VOpenLayersMap;
import org.vaadin.vol.client.wrappers.Map;

/**
 * @author Henri Kerola / Vaadin
 */
public class MapUtil {

    public static <T> T findParent(Class<T> clazz, Widget widget) {
        if (widget == null) {
            return null;
        }

        if (clazz.equals(widget.getClass())) {
            return (T) widget;
        } else {
            return findParent(clazz, widget.getParent());
        }
    }

    public static VOpenLayersMap getVMap(Widget widget) {
        if (widget == null) {
            return null;
        }

        if (widget instanceof VOpenLayersMap) {
            return (VOpenLayersMap)widget;
        } else {
            return getVMap(widget.getParent());
        }
    }

    public static Map getMap(Widget widget) {
        VOpenLayersMap map = getVMap(widget);
        if (map == null) {
            return null;
        }

        return map.getMap();
    }
}
