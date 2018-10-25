package cz.mg.toolkit.shape;

import cz.mg.toolkit.component.Component;


public interface Shape {
    public boolean isInside(Component component, double x, double y);
}
