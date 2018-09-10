package cz.mg.toolkit.event;


public abstract interface EventListener<E extends Event> {
    public void onEventEnter(E e);
    public void onEventLeave(E e);
    public boolean acceptsEvent(Event e);
}
