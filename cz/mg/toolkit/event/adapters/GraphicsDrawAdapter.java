package cz.mg.toolkit.event.adapters;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.contexts.GraphicsEventContext;
import cz.mg.toolkit.event.events.DrawEvent;


public abstract class GraphicsDrawAdapter implements EventListener<DrawEvent> {
    @Override
    public final void onEventEnter(DrawEvent e) {
        GraphicsEventContext context = (GraphicsEventContext)e.getEventContext();
        onDrawEventEnter(context.getGraphics());
    }

    @Override
    public final void onEventLeave(DrawEvent e) {
        GraphicsEventContext context = (GraphicsEventContext)e.getEventContext();
        onDrawEventLeave(context.getGraphics());
    }
    
    public void onDrawEventEnter(Graphics g){
    }
    
    public void onDrawEventLeave(Graphics g){
    }

    @Override
    public boolean acceptsEvent(Event e) {
        return e instanceof DrawEvent && e.getEventContext() instanceof GraphicsEventContext;
    }
}
