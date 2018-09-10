package cz.mg.toolkit.event.contexts;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.EventContext;
import cz.mg.toolkit.graphics.Graphics;


public final class GraphicsEventContext extends EventContext {
    private final Graphics graphics;

    public GraphicsEventContext(Graphics graphics) {
        this.graphics = graphics;
    }

    public final Graphics getGraphics() {
        return graphics;
    }
    
    @Override
    public final void pushState(Component component) {
        graphics.pushClip();
        graphics.pushTransform();
        graphics.translate(component.getX(), component.getY());
        graphics.clipRectangle(0, 0, component.getWidth(), component.getHeight());
    }

    @Override
    public final void popState(Component component) {
        graphics.popTransform();
        graphics.popClip();
    }
}
