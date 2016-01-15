package org.vaadin.vol.client;

//  Style Properties
//
//  The properties that you can use for styling are:
//   -- fillColor
//  Default is #ee9900. This is the color used for filling in Polygons. It is also used in the center of marks for points: the interior color of circles or other shapes. It is not used if an externalGraphic is applied to a point.
//
//  -- fillOpacity:
//  Default is 0.4. This is the opacity used for filling in Polygons. It is also used in the center of marks for points: the interior color of circles or other shapes. It is not used if an externalGraphic is applied to a point.
//
//  -- strokeColor
//  Default is #ee9900. This is color of the line on features. On polygons and point marks, it is used as an outline to the feature. On lines, this is the representation of the feature.
//
//  -- strokeOpacity
//  Default is 1 This is opacity of the line on features. On polygons and point marks, it is used as an outline to the feature. On lines, this is the representation of the feature.
//
//  -- strokeWidth
//  Default is 1 This is width of the line on features. On polygons and point marks, it is used as an outline to the feature. On lines, this is the representation of the feature.
//
//  -- strokeLinecap
//  Default is round. Options are butt, round, square. This property is similar to the SVG stroke-linecap property. It determines what the end of lines should look like. See the SVG link for image examples.
//
//  -- strokeDashstyle
//  Default is solid. Options are:
//   -- dot
//   -- dash
//   -- dashdot
//   -- longdash
//   -- longdashdot
//   -- solid
//
//  -- pointRadius
//  Default is 6.
//
//  -- pointerEvents:
//  Default is visiblePainted. Only used by the SVG Renderer. See SVG pointer-events definition for more.
//
//  -- cursor
//  Cursor used when mouse is over the feature. Default is an empty string, which inherits from parent elements.
//
//  -- externalGraphic
//  An external image to be used to represent a point.
//
//  -- graphicWidth, graphicHeight
//  These properties define the height and width of an externalGraphic. This is an alternative to the pointRadius symbolizer property to be used when your graphic has different sizes in the X and Y direction.
//
//  -- graphicOpacity
//  Opacity of an external graphic.
//
//  -- graphicXOffset, graphicYOffset
//  Where the center of an externalGraphic should be.
//
//  -- rotation
//  The rotation angle in degrees clockwise for a point symbolizer.
//
//  -- graphicName
//  Name of a type of symbol to be used for a point mark.
//
//  -- display
//  Can be set to none to hide features from rendering.

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Style implements Serializable {

    public static final String STROKE_DASHSTYLE_SOLID = "solid";
    public static final String STROKE_DASHSTYLE_DASHDOT = "dashdot";
    public static final String STROKE_DASHSTYLE_DOT = "dot";
    public static final String STROKE_DASHSTYLE_DASH = "dash";
    public static final String STROKE_DASHSTYLE_LONGDASH = "longdash";
    public static final String STROKE_DASHSTYLE_LONGDASHDOT = "longdashdor";

    public static final String STROKE_LINECAP_BUTT = "butt";
    public static final String STROKE_LINECAP_ROUND = "round";
    public static final String STROKE_LINECAP_SQUARE = "square";

    private static long idx = 0;
    private String name;

    private String label;
    private String fillColor;
    private Object fillOpacity;
    private Object pointRadius;
    private String strokeColor;
    private Object strokeWidth;
    private String externalGraphic;
    private Integer rotation;
    private String fontColor;
    private Object graphicWidth;
    private Object graphicHeight;
    private Object graphicXOffset;
    private Object graphicYOffset;
    private Integer backgroundHeight; //
    private Integer backgroundWidth; //
    private String backgroundGraphic;
    private Integer backgroundXOffset;
    private Integer backgroundYOffset;
    private Integer graphicZIndex;
    private Integer backgroundGraphicZIndex;
    private Double strokeOpacity;
    private String fontSize;
    private String fontFamily;
    private String fontWeight;
    private String labelAlign;
    private Integer labelXOffset;
    private Integer labelYOffset;
    private String labelOutlineColor;
    private Integer labelOutlineWidth;
    private String strokeLinecap;
    private String strokeDashstyle;
    private Boolean fill;
    private Boolean stroke;
    private Boolean graphic;
    private String cursor;
    private String graphicName;
    private String graphicTitle;


    @SerializedName("__VOL_INHERIT")
    private String coreStyleName;
    @SerializedName("__VOL_CONTEXT")
    private String contextJs;

    public Style() {
        name = "Style" + String.valueOf(++idx);
        init();
    }

    public Style(String string) {
        name = string;
        init();
    }

    public String getName() {
        return name;
    }

    private String getString(Object value) {
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    private Double getDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        }
        return null;
    }

    private Integer getInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    private Boolean getBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    private String setAttribute(String value) {
        if (value == null) {
            return null;
        }

        return "${" + value + "}";
    }

    /**
     * Hex fill color. Default is '#ee9900'.
     *
     * @param c
     *            - hexidecimal color code or a W3C standard color name
     */
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getFillColorByAttribute() {
        return fillColor;
    }
    public void setFillColorByAttribute(String c) {
        fillColor = setAttribute(c);
    }

    /** Hex fill color. */
    public String getFillColor() {
        return fillColor;
    }

    /** Fill opacity (0-1). Default is 0.4 */
    public void setFillOpacity(double o) {
        fillOpacity = o;
    }

    public String getFillOpacityByAttribute() {
        return getString(fillOpacity);
    }
    public void setFillOpacityByAttribute(String o) {
        fillOpacity = setAttribute(o);
    }

    /** Fill opacity (0-1). */
    public Double getFillOpacity() {
        return getDouble(fillOpacity);
    }

    /** Pixel point radius. Default is 6. */
    public void setPointRadius(double r) {
        pointRadius = r;
    }

    public String getPointRadiusByAttribute() {
        return getString(pointRadius);
    }
    public void setPointRadiusByAttribute(String r) {
        pointRadius = setAttribute(r);
    }

    /** Pixel point radius. */
    public Double getPointRadius() {
        return getDouble(pointRadius);
    }

    /**
     * Hex stroke color. Default is '#ee9900'.
     *
     * @param c
     *            - see setFillColor
     */
    public void setStrokeColor(String c) {
        strokeColor = c;
    }

    public String getStrokeColorByAttribute() {
        return strokeColor;
    }
    public void setStrokeColorByAttribute(String c) {
        strokeColor = setAttribute(c);
    }

    /** Hex stroke html color. */
    public String getStrokeColor() {
        return strokeColor;
    }

    /** Pixel stroke width. Default is 1. */
    public void setStrokeWidth(double w) {
        strokeWidth = w;
    }

    public Double getStrokeWidthByAttribute() {
        return getDouble(strokeWidth);
    }
    public void setStrokeWidthByAttribute(String w) {
        strokeWidth = setAttribute(w);
    }

    /** Pixel stroke width. */
    public Double getStrokeWidth() {
        return getDouble(strokeWidth);
    }

    /** Url to an external graphic that will be used for rendering points. */
    public void setExternalGraphic(String graphicURL) {
        externalGraphic = graphicURL;
    }

    public String getExternalGraphicByAttribute() {
        return externalGraphic;
    }
    public void setExternalGraphicByAttribute(String graphicURL) {
        externalGraphic = setAttribute(graphicURL);
    }

    /** Url to an external graphic that will be used for rendering points. */
    public String getExternalGraphic() {
        return externalGraphic;
    }

    /**
     * Convenient method to set the pixel width and height for sizing an
     * external graphic.
     *
     * @param width
     *            The width (in pixels) to set
     * @param height
     *            The height (in pixels) to set
     */
    public void setGraphicSize(int width, int height) {
        setGraphicWidth(width);
        setGraphicHeight(height);
    }

    private void setProperty(String s, Object o) {

    }

    private Object getProperty(String p) {
        return null;
    }

    private String getPropertyByAttribute(String graphicWidth) {
        return null;
    }

    private void setPropertyByAttribute(String graphicXOffset, Object xOffsetAttr) {

    }

    public void setGraphicWidth(Integer width) {
        graphicWidth = width;
    }

    public String getGraphicWidthByAttribute() {
        return getString(graphicWidth);
    }

    public void setGraphicWidthByAttribute(String widthAttr) {
        graphicWidth = setAttribute(widthAttr);
    }

    public void setGraphicHeight(Integer height) {
        graphicHeight = height;
    }

    public String getGraphicHeightByAttribute() {
        return getString(graphicHeight);
    }
    public void setGraphicHeightByAttribute(String heightAttr) {
        graphicHeight = setAttribute(heightAttr);
    }

    /** Pixel width for sizing an external graphic. */
    public Integer getGraphicWidth() {
        return getInteger(graphicWidth);
    }

    /** Pixel height for sizing an external graphic. */
    public Integer getGraphicHeight() {
        return getInteger(graphicHeight);
    }

    public Integer getGraphicXOffset() {
        return getInteger(graphicXOffset);
    }
    public void setGraphicXOffset(Integer xOffset) {
        graphicXOffset = xOffset;
    }

    public String getGraphicXOffsetByAttribute() {
        return getString(graphicXOffset);
    }
    public void setGraphicXOffsetByAttribute(String xOffsetAttr) {
        graphicXOffset = setAttribute(xOffsetAttr);
    }

    public Integer getGraphicYOffset() {
        return getInteger(graphicYOffset);
    }
    public void setGraphicYOffset(Integer yOffset) {
        graphicYOffset = yOffset;
    }

    public String getGraphicYOffsetByAttribute() {
        return getString(graphicYOffset);
    }
    public void setGraphicYOffsetByAttribute(String yOffsetAttr) {
        graphicYOffset = setAttribute(yOffsetAttr);
    }

    /**
     * The height of the background graphic. If not provided, the graphicHeight
     * will be used.
     */
    public void setBackgroundHeight(Integer backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }

    /** The height of the background graphic. */
    public Integer getBackgroundHeight() {
        return backgroundHeight;
    }

    /**
     * The width of the background width. If not provided, the graphicWidth will
     * be used.
     */
    public void setBackgroundWidth(Integer backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    /** The width of the background width. */
    public Integer getBackgroundWidth() {
        return backgroundWidth;
    }

    /** Url to a graphic to be used as the background under an externalGraphic. */
    public void setBackgroundGraphic(String graphicURL) {
        backgroundGraphic = graphicURL;
    }

    /** Url to a graphic to be used as the background under an externalGraphic. */
    public String getBackgroundGraphic() {
        return backgroundGraphic;
    }

    public Integer getBackgroundXOffset() {
        return backgroundXOffset;
    }
    public void setBackgroundXOffset(Integer backgroundXOffset) {
        this.backgroundXOffset = backgroundXOffset;
    }

    public Integer getBackgroundYOffset() {
        return backgroundYOffset;
    }
    public void setBackgroundYOffset(Integer backgroundYOffset) {
        this.backgroundYOffset = backgroundYOffset;
    }

    /** The integer z-index value to use in rendering. */
    public void setGraphicZIndex(Integer graphicZIndex) {
        this.graphicZIndex = graphicZIndex;
    }

    /** The integer z-index value to use in rendering. */
    public Integer getGraphicZIndex() {
        return graphicZIndex;
    }

    /**
     * The integer z-index value to use in rendering the background graphic.
     * Usually is a number smaller then the GraphicZIndex, so the background can
     * be behind the feature graphic.
     */
    public void setBackgroundGraphicZIndex(Integer backgroundGraphicZIndex) {
        this.backgroundGraphicZIndex = backgroundGraphicZIndex;
    }

    /** The integer z-index value to use in rendering the background graphic. */
    public Integer getBackgroundGraphicZIndex() {
        return backgroundGraphicZIndex;
    }

    /** Stroke opacity (0-1). Default is 1. */
    public void setStrokeOpacity(double strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
    }

    public Double getStrokeOpacity() {
        return strokeOpacity;
    }

    /**
     * The text for an optional label. For browsers that use the canvas
     * renderer, this requires either fillText or mozDrawText to be available.
     * <p>
     * Note: you can set a custom label for each feature added to a layer by
     * using tags in the label, and setting attributes using
     * {@link org.gwtopenmaps.openlayers.client.util.Attributes}. For example,
     * set the style.label to "${customLabel}", then, for each feature added to
     * the layer, add an "customLabel" attribute with
     * <p>
     * <code>attributes.setAttribute("customLabel","myLabel for this specific feature")</code>
     * <p>
     * Note: this can also be used in any style field of type String, such as
     * fillColor, fontColor, etc
     * */
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getLabelByAttribute() {
        return label;
    }
    public void setLabelByAttribute(String label) {
        this.label = setAttribute(label);
    }

    public String getFontColor() {
        return fontColor;
    }
    /** The font color for the label, to be provided like CSS. */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontSize() {
        return fontSize;
    }
    /** The font size for the label, to be provided like in CSS. */
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }
    /** The font family for the label, to be provided like in CSS. */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontWeight() {
        return fontWeight;
    }
    /** The font weight for the label, to be provided like in CSS. */
    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    /**
     * Sets the Label alignment string directly. This specifies the insertion
     * point relative to the text. It is a string composed of two characters.
     * <p>
     * The first character is for the horizontal alignment, the second for the
     * vertical alignment.
     * <p>
     * Valid values for horizontal alignment: 'l'=left, 'c'=center, 'r'=right.
     * Valid values for vertical alignment: 't'=top, 'm'=middle, 'b'=bottom.
     * Example values: 'lt', 'cm', 'rb'. The canvas renderer does not support
     * vertical alignment, it will always use 'b'.
     */
    public void setLabelAlign(String align) {
        labelAlign = align;
    }

    /**
     * Vertical Label alignment. This specifies the insertion point relative to
     * the text.
     */
    public String getLabelAlign() {
        return labelAlign;
    }

    public void setLabelXOffset(Integer offset) {
        labelXOffset = offset;
    }

    public Integer getLabelXOffset() {
        return labelXOffset;
    }

    public void setLabelYOffset(Integer offset) {
        labelYOffset = offset;
    }

    public Integer getLabelYOffset() {
        return labelYOffset;
    }

    public void setLabelOutlineColor(String color) {
        labelOutlineColor = color;
    }

    public String getLabelOutlineColor() {
        return labelOutlineColor;
    }

    public void setLabelOutlineWidth(Integer width) {
        labelOutlineWidth = width;
    }

    public Integer getLabelOutlineWidth(){
        return labelOutlineWidth;
    }

    /** Stroke linecap. */
    public String getStrokeLinecap() {
        return strokeLinecap;
    }

    /**
     * Directly sets the StrokeLineCap string. Default is 'round'. [butt | round
     * | square]
     */
    public void setStrokeLinecap(String strokeLinecap) {
        this.strokeLinecap = strokeLinecap;
    }

    /**
     * Directly sets the stroke dash style string. Default is Default is
     * 'solid'. [dot | dash | dashdot | longdash | longdashdot | solid]
     */
    public void setStrokeDashstyle(String strokeDashstyle) {
        this.strokeDashstyle = strokeDashstyle;
    }

    /**
     * Stroke dash style.
     */
    public String getStrokeDashstyle() {
        return strokeDashstyle;
    }

    /** Set to false if no fill is desired. */
    public void fill(boolean fill) {
        this.fill = fill;
    }

    /** Set to false if no fill is desired. */
    public Boolean getFill() {
        return fill;
    }

    /** Set to false if no stroke is desired. */
    public void stroke(boolean stroke) {
        this.stroke = stroke;
    }

    /** Set to false if no stroke is desired. */
    public Boolean getStroke() {
        return stroke;
    }

    /** Set to false if no graphic is desired. */
    public void graphic(boolean graphic) {
        this.graphic = graphic;
    }

    /** Set to false if no graphic is desired. */
    public Boolean getGraphic() {
        return graphic;
    }

    /** Cursor. Default is ''. */
    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    /** Cursor. */
    public String getCursor() {
        return cursor;
    }

    /**
     * Directly sets the named graphic to use when rendering points. Default is
     * 'circle'.
     * <p>
     * Supported values include 'circle' (default), 'square', 'star', 'x',
     * 'cross', 'triangle'.
     */
    public void setGraphicName(String graphicName) {
        this.graphicName = graphicName;
    }

    /**
     * Named graphic to use when rendering points. Supported values include
     * 'circle' (default), 'square', 'star', 'x', 'cross', 'triangle'.
     */
    public String getGraphicName() {
        return graphicName;
    }

    /**
     * Tooltip for an external graphic. Only supported in Firefox and Internet
     * Explorer.
     */
    public void setGraphicTitle(String graphicTitle) {
        this.graphicTitle = graphicTitle;
    }

    /**
     * Tooltip for an external graphic. Only supported in Firefox and Internet
     * Explorer.
     */
    public String getGraphicTitle() {
        return graphicTitle;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }


    /**
     * @param coreStyleName
     *            the core style name this Style should extend. E.g. 'default'
     *            or 'selected'
     */
    public void extendCoreStyle(String coreStyleName) {
        this.coreStyleName = coreStyleName;
    }

    private void init() {
        extendCoreStyle("default");
    }

    public String getContextJs() {
        return contextJs;
    }
    public void setContextJs(String js) {
        contextJs = js;
    }
}
