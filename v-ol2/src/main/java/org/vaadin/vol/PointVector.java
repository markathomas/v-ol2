package org.vaadin.vol;

import org.vaadin.vol.client.Point;

public class PointVector extends Vector {

    public PointVector(Point point) {
        setPoints(point);
    }

    public PointVector(double x, double y) {
        this(new Point(x, y));
    }

    public PointVector() {
        setPoints(new Point(0, 0));
    }

    public Point getPoint() {
        return getPoints()[0];
    }

    /**
     * Note that for PointVector only one points is needed and handled.
     *
     * @see org.vaadin.vol.Vector#setPoints(Point[])
     */
    @Override
    public void setPoints(Point... points) {
        super.setPoints(points);
    }
}
