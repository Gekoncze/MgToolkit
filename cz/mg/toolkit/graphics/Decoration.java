package cz.mg.toolkit.graphics;

import cz.mg.toolkit.component.Component;


public abstract class Decoration {
    private Decoration innerDecoration;

    public Decoration() {
    }

    public Decoration(Decoration decoration) {
        this.innerDecoration = decoration;
    }

    public Decoration getInnerDecoration() {
        return innerDecoration;
    }
    
    public void draw(Graphics g, Component component){
        onDraw(g, component);
        if(innerDecoration != null) innerDecoration.draw(g, component);
    }
    
    protected abstract void onDraw(Graphics g, Component component);
}
