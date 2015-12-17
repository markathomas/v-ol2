/**
 *
 */
package org.vaadin.vol;

import com.vaadin.shared.Connector;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.vaadin.vol.client.Point;
import org.vaadin.vol.client.StyleMap;
import org.vaadin.vol.client.VectorLayerServerRpc;
import org.vaadin.vol.client.VectorLayerState;

public class VectorLayer extends AbstractComponentContainer implements Layer {

    public VectorLayer() {
        registerRpc(new VectorLayerServerRpc() {
            public void draw(String[] vertices, VectorLayerState.DrawingMode drawingMode) {
                drawVector(vertices, drawingMode);
            }
            public void modify(String[] vertices, String connectorId) {
                modifyVector(vertices, connectorId);
            }
            public void select(String connectorId) {
                selectVector(connectorId);
            }
            public void unselect(String connectorId) {
                unselectVector(connectorId);
            }
        });
    }

    @Override
    public VectorLayerState getState() {
        return (VectorLayerState)super.getState();
    }

    public void addVector(Vector m) {
        addComponent(m);
    }

    public void replaceComponent(Component oldComponent, Component newComponent) {
        removeComponent(oldComponent);
        addComponent(newComponent);
    }

    @Override
    public int getComponentCount() {
        return getState().vectors.size();
    }

    public Iterator<Component> iterator() {
        List<Component> list = new ArrayList<Component>(getState().vectors.size());
        for (Connector connector : getState().vectors) {
            list.add((Vector)connector);
        }
        return list.iterator();
    }

    @Override
    public void addComponent(Component c) {
        if (c instanceof Vector) {
            getState().vectors.add(c);
            super.addComponent(c);
            markAsDirty();
        } else {
            throw new IllegalArgumentException(
                    "VectorLayer supports only Vectors");
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void removeComponent(Component c) {
        getState().vectors.remove(c);
        super.removeComponent(c);
        if (getState().selectedVector == c) {
            getState().selectedVector = null;
            fireEvent(new VectorUnSelectedEvent(this, (Vector) c));
        }
        markAsDirty();
    }

    public void setDrawingMode(VectorLayerState.DrawingMode drawingMode) {
        this.getState().drawingMode = drawingMode;
        markAsDirty();
    }

    public VectorLayerState.DrawingMode getDrawingMode() {
        return getState().drawingMode;
    }

    private void drawVector(String[] vertices, VectorLayerState.DrawingMode drawingMode) {
        if (vertices != null && vertices.length > 0) {
            Point[] points = getPoints(vertices);
            if (drawingMode == VectorLayerState.DrawingMode.LINE) {
                PolyLine polyline = new PolyLine();
                polyline.setPoints(points);
                newVectorPainted(polyline);
            } else if (drawingMode == VectorLayerState.DrawingMode.AREA
              || drawingMode == VectorLayerState.DrawingMode.RECTANGLE
              || drawingMode == VectorLayerState.DrawingMode.CIRCLE) {
                Area area = new Area();
                area.setPoints(points);
                newVectorPainted(area);
            } else if (drawingMode == VectorLayerState.DrawingMode.POINT) {
                PointVector point = new PointVector(points[0]);
                newVectorPainted(point);
            }
        }
    }

    private Point[] getPoints(String[] vertices) {
        Point[] points = new Point[vertices.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = Point.valueOf(vertices[i]);
        }
        return points;
    }

    private void modifyVector(String[] vertices, String connectorId) {
        Vector vector = getVector(connectorId);
        if (vector != null) {
            vector.setPointsWithoutRepaint(getPoints(vertices));
            vectorModified(vector);
        } else {
            Logger.getLogger(getClass().getName()).severe("Vector modified event didn't provide related vector!?");
        }
    }

    private void selectVector(String connectorId) {
        Vector vector = getVector(connectorId);
        if (vector != null) {
            this.getState().selectedVector = vector;
            VectorSelectedEvent vectorSelectedEvent = new VectorSelectedEvent(this, vector);
            fireEvent(vectorSelectedEvent);
        } else {
            Logger.getLogger(getClass().getName()).severe("Vector selected event didn't provide related vector!?");
        }
    }

    private void unselectVector(String connectorId) {
        Vector vector = getVector(connectorId);
        if (vector != null) {
            if (this.getState().selectedVector == vector) {
                this.getState().selectedVector = null;
            }
            VectorUnSelectedEvent vectorUnSelectedEvent = new VectorUnSelectedEvent(this, vector);
            fireEvent(vectorUnSelectedEvent);
        } else {
            Logger.getLogger(getClass().getName()).severe("Vector un-selected event didn't provide related vector!?");
        }
    }

    private Vector getVector(String connectorId) {
        for (Connector c : this.getState().vectors) {
            if (Objects.equals(c.getConnectorId(), connectorId)) {
                return (Vector)c;
            }
        }
        return null;
    }

    private void vectorModified(Vector object2) {
        VectorModifiedEvent vectorModifiedEvent = new VectorModifiedEvent(this, object2);
        fireEvent(vectorModifiedEvent);
    }

    protected void newVectorPainted(Vector vector) {
        VectorDrawnEvent vectorDrawnEvent = new VectorDrawnEvent(this, vector);
        fireEvent(vectorDrawnEvent);
        markAsDirty();
    }

    public interface VectorDrawnListener {

        Method method = ReflectTools.findMethod(
                VectorDrawnListener.class, "vectorDrawn",
                VectorDrawnEvent.class);

        void vectorDrawn(VectorDrawnEvent event);

    }

    public void addVectorDrawnListener(VectorDrawnListener listener) {
        addListener("vectorDrawn", VectorDrawnEvent.class, listener, VectorDrawnListener.method);
    }

    public void removeVectorDrawnListener(VectorDrawnListener listener) {
        removeListener(VectorDrawnEvent.class, listener, VectorDrawnListener.method);
    }

    public interface VectorModifiedListener {

        Method method = ReflectTools.findMethod(
                VectorModifiedListener.class, "vectorModified",
                VectorModifiedEvent.class);

        void vectorModified(VectorModifiedEvent event);

    }

    public void addVectorModifiedListener(VectorModifiedListener listener) {
        addListener("vectorModified", VectorModifiedEvent.class, listener, VectorModifiedListener.method);
    }

    public void removeVectorModifiedListener(VectorModifiedListener listener) {
        removeListener(VectorModifiedEvent.class, listener, VectorModifiedListener.method);
    }

    public void setDisplayName(String displayName) {
        this.getState().displayName = displayName;
    }

    public String getDisplayName() {
        return getState().displayName;
    }

    /**
     * @return the styleMap
     */
    public StyleMap getStyleMap() {
        return this.getState().styleMap;
    }

    /**
     * @param stylemap
     *            the styleMap to set
     */
    public void setStyleMap(StyleMap stylemap) {
        this.getState().styleMap = stylemap;
        markAsDirty();
    }

    public String getSelectionCtrlId() {
        return getState().selectionCtrlId;
    }

    public void setSelectionCtrlId(String selectionCtrlId) {
        this.getState().selectionCtrlId = selectionCtrlId;
    }

    public void setSelectionMode(VectorLayerState.SelectionMode selectionMode) {
        this.getState().selectionMode = selectionMode;
        markAsDirty();
    }

    public VectorLayerState.SelectionMode getSelectionMode() {
        return getState().selectionMode;
    }

    public class VectorDrawnEvent extends Event {

        private Vector vector;

        public VectorDrawnEvent(Component source, Vector vector) {
            super(source);
            setVector(vector);
        }

        private void setVector(Vector vector) {
            this.vector = vector;
        }

        public Vector getVector() {
            return vector;
        }

    }

    public class VectorModifiedEvent extends Event {

        private Vector vector;

        public VectorModifiedEvent(Component source, Vector vector) {
            super(source);
            setVector(vector);
        }

        private void setVector(Vector vector) {
            this.vector = vector;
        }

        public Vector getVector() {
            return vector;
        }

    }

    public interface VectorSelectedListener {

        String EVENT_ID = "vsel";

        Method method = ReflectTools.findMethod(
                VectorSelectedListener.class, "vectorSelected",
                VectorSelectedEvent.class);

        void vectorSelected(VectorSelectedEvent event);

    }

    public void addVectorSelectedListener(VectorSelectedListener listener) {
        addListener(VectorSelectedListener.EVENT_ID, VectorSelectedEvent.class,
                listener, VectorSelectedListener.method);
    }

    public void removeVectorSelectedListener(VectorSelectedListener listener) {
        removeListener(VectorSelectedListener.EVENT_ID,
                VectorSelectedEvent.class, listener);
    }

    public class VectorSelectedEvent extends Event {

        private Vector vector;

        public VectorSelectedEvent(Component source, Vector vector) {
            super(source);
            setVector(vector);
        }

        private void setVector(Vector vector) {
            this.vector = vector;
        }

        public Vector getVector() {
            return vector;
        }

    }

    public interface VectorUnSelectedListener {

        String EVENT_ID = "vusel";

        Method method = ReflectTools.findMethod(
                VectorUnSelectedListener.class, "vectorUnSelected",
                VectorUnSelectedEvent.class);

        void vectorUnSelected(VectorUnSelectedEvent event);

    }

    public void addVectorUnSelectedListener(VectorUnSelectedListener listener) {
        addListener(VectorUnSelectedListener.EVENT_ID,
                VectorUnSelectedEvent.class, listener,
                VectorUnSelectedListener.method);
    }

    public void removeVectorUnSelectedListener(VectorUnSelectedListener listener) {
        removeListener(VectorUnSelectedListener.EVENT_ID,
                VectorUnSelectedEvent.class, listener);
    }

    public Vector getSelectedVector() {
        return (Vector)getState().selectedVector;
    }

    public void setSelectedVector(Vector selectedVector) {
        if (this.getState().selectedVector != selectedVector) {
            if (this.getState().selectedVector != null) {
                fireEvent(new VectorUnSelectedEvent(this, getSelectedVector()));
            }
            this.getState().selectedVector = selectedVector;
            if (selectedVector != null) {
                fireEvent(new VectorSelectedEvent(this, selectedVector));
            }
            markAsDirty();
        }
    }

    public class VectorUnSelectedEvent extends Event {

        private Vector vector;

        public VectorUnSelectedEvent(Component source, Vector vector) {
            super(source);
            setVector(vector);
        }

        private void setVector(Vector vector) {
            this.vector = vector;
        }

        public Vector getVector() {
            return vector;
        }

    }

}
