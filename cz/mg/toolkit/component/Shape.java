package cz.mg.toolkit.component;

import cz.mg.collections.node.TreeNode;


public abstract class Shape extends TreeNode<Component, Component> {
    private double x, y;
    private double width, height;

    public final double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public final double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public final void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }
    
    public final void move(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }
    
    public final void moveHorizontally(double dx){
        this.x += dx;
    }
    
    public final void moveVertically(double dy){
        this.y += dy;
    }

    public final double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public final double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public final void setSize(double width, double height){
        setWidth(width);
        setHeight(height);
    }
    
    public boolean isInside(double px, double py){
        return px >= x && py >= y && px < (x + width) && py < (y + height);
    }
}
