package cz.mg.toolkit.layout;

import cz.mg.toolkit.component.Container;


public abstract class Layout {
    public void onBeforeLayoutEnter(Container parent){}
    public void onBeforeLayoutLeave(Container parent){}
    public abstract void reshapeComponents(Container parent);
    public void onAfterLayoutEnter(Container parent){}
    public void onAfterLayoutLeave(Container parent){}
    
    public abstract double computeMinWidth(Container parent);
    public abstract double computeMinHeight(Container parent);
}
