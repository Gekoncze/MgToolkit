package cz.mg.toolkit.graphics.images;

import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;


public abstract class VectorImage implements Image {
    private double width, height;
    
    public abstract void onDraw(Graphics g, double w, double h);
    
    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public final void setWidth(double width) {
        this.width = width;
    }

    public final void setHeight(double height) {
        this.height = height;
    }
}
