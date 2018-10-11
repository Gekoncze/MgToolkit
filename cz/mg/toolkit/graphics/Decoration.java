package cz.mg.toolkit.graphics;

import cz.mg.toolkit.component.Component;


public abstract class Decoration {
    private Decoration decoration;

    public Decoration() {
    }

    public Decoration(Decoration decoration) {
        this.decoration = decoration;
    }
    
    public void draw(Graphics g, Component component){
        onDraw(g, component);
        if(decoration != null) decoration.draw(g, component);
    }
    
    protected abstract void onDraw(Graphics g, Component component);
}
