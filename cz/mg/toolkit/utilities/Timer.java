package cz.mg.toolkit.utilities;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.impl.ImplTimer;
import cz.mg.toolkit.impl.swing.SwingImplTimer;


public abstract class Timer {
    protected EventObserver eventObserver;
    protected long delay;
    private boolean stopped = true;
    
    private ImplTimer implTimer = new SwingImplTimer(10, new EventObserver() {
        @Override
        public void sendEvent(Event e) {
            if(eventObserver != null && !stopped) onCheck();
        }
    });

    public Timer(long delay) {
        this.delay = delay;
    }

    public EventObserver getEventObserver() {
        return eventObserver;
    }

    public void setEventObserver(EventObserver eventObserver) {
        this.eventObserver = eventObserver;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    
    public boolean isRunning() {
        return !stopped;
    }
    
    public void start(){
        stopped = false;
        onStart();
        implTimer.start();
    }
    
    public void stop(){
        stopped = true;
        implTimer.stop();
        onStop();
    }
    
    public static long getCurrentTimeInMilliseconds(){
        return System.currentTimeMillis();
    }
    
    protected abstract void onCheck();
    protected abstract void onStart();
    protected abstract void onStop();
}
