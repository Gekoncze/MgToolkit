package cz.mg.toolkit.utilities.shapes;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.Shape;


public class RectangleShape implements Shape {
    @Override
    public boolean isInside(Component component, double x, double y) {
        return x >= component.getX() && y >= component.getY() && x < (component.getX() + component.getWidth()) && y < (component.getY() + component.getHeight());
    }
}
