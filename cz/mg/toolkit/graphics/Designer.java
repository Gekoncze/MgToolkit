package cz.mg.toolkit.graphics;

import cz.mg.toolkit.component.Component;


public interface Designer {
    public void design(Component component);
    public void pushState(Component component);
    public void popState(Component component);
    public void clearState();
}
