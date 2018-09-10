package cz.mg.toolkit.event.events;

import cz.mg.toolkit.device.devices.Mouse;


public class MouseWheelEvent extends MouseEvent {
    private final Direction direction;

    public MouseWheelEvent(Mouse mouse, Direction direction) {
        super(mouse);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public static enum Direction {
        UP,
        DOWN
    }
}
