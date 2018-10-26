package cz.mg.toolkit.utilities;

import cz.mg.toolkit.component.Component;


public interface SizePolicy {
    public void beforeLayoutUpdateHorizontal(Component component);
    public void beforeLayoutUpdateVertical(Component component);
    public void afterLayoutUpdateHorizontal(Component component);
    public void afterLayoutUpdateVertical(Component component);
}
