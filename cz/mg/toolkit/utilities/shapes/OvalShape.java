package cz.mg.toolkit.utilities.shapes;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.Shape;


public class OvalShape implements Shape {
    @Override
    public boolean isInside(Component component, double x, double y) {
        double rx = component.getWidth() / 2;
        double ry = component.getHeight() / 2;
        double sx = component.getX() + rx;
        double sy = component.getY() + ry;
        double dx = x - sx;
        double dy = y - sy;
        double relativeHorizontalDistance = (dx*dx)/(rx*rx);
        double relativeVerticalDistance = (dy*dy)/(ry*ry);
        double relativeDistance = relativeHorizontalDistance + relativeVerticalDistance;
        return relativeDistance <= 1;
    }
}
