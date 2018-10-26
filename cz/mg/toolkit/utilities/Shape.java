package cz.mg.toolkit.utilities;

import cz.mg.toolkit.component.Component;


public interface Shape {
    public boolean isInside(Component component, double x, double y);
}
