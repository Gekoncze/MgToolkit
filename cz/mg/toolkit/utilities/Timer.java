package cz.mg.toolkit.utilities;

import cz.mg.toolkit.event.EventObserver;


public abstract class Timer {
    protected EventObserver eventObserver;
    protected long delay;
    private boolean stopped = true;
    
    private javax.swing.Timer implTimer = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
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

    public boolean isStopped() {
        return stopped;
    }
    
    public boolean isStarted() {
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
