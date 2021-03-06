package cz.mg.toolkit.graphics.designer;

import cz.mg.toolkit.component.Component;


public interface Designer {
    public Design getDesign(String name);
    public void design(Component component);
}
