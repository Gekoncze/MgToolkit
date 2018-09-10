package cz.mg.toolkit.event.contexts;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.EventContext;


public final class InputEventContext extends EventContext {
    private final ChainList<Double> xStack = new ChainList<>();
    private final ChainList<Double> yStack = new ChainList<>();
    private final ChainList<MouseLocation> mlStack = new ChainList<>();
    private double x, y;
    private MouseLocation mouseLocation = MouseLocation.INSIDE;

    public InputEventContext(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public MouseLocation getMouseLocation() {
        return mouseLocation;
    }
    
    @Override
    public final void pushState(Component component) {
        mlStack.addLast(mouseLocation);
        xStack.addLast(x);
        yStack.addLast(y);
        
        if(mouseLocation == MouseLocation.INSIDE) if(!component.isInside(x, y)) mouseLocation = MouseLocation.OUTSIDE;
        x -= component.getX();
        y -= component.getY();
    }

    @Override
    public final void popState(Component component) {
        y = yStack.removeLast();
        x = xStack.removeLast();
        mouseLocation = mlStack.removeLast();
        
    }
    
    public static enum MouseLocation {
        OUTSIDE,
        INSIDE
    }
}
