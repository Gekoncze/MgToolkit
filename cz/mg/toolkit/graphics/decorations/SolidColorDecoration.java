package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;


public class SolidColorDecoration extends Decoration {
    private Color color;

    public SolidColorDecoration(Decoration decoration, Color color) {
        super(decoration);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        g.setColor(color);
    }
}
