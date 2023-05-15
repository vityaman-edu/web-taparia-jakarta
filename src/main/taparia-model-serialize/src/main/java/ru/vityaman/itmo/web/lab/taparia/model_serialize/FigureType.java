package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FigureType {
    public static final String ELLIPSE = "ellipse";
    public static final String POLYGON = "polygon";
    public static final String UNION = "union";
    public static final String INTERSECTION = "intersection";
    public static final String NEGATION = "negation";
    public static final String COLORED = "colored";

    public static final String PATTERN = "^("
        + ELLIPSE + "|" + POLYGON
        + "|" + UNION + "|" + INTERSECTION + "|" + NEGATION
        + "|" + COLORED + ")$";
}
