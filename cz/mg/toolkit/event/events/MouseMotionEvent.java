package cz.mg.toolkit.event.events;

import cz.mg.toolkit.device.devices.Mouse;


public class MouseMotionEvent extends MouseEvent {
    private final double dx;
    private final double dy;

    public MouseMotionEvent(Mouse mouse, double dx, double dy) {
        super(mouse);
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
