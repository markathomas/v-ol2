package org.vaadin.vol.client;

import java.io.Serializable;

public class Attributes implements Serializable {

    private String label;
    private Integer pointRadius;
    private String size;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getPointRadius() {
        return pointRadius;
    }

    public void setPointRadius(Integer pointRadius) {
        this.pointRadius = pointRadius;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
