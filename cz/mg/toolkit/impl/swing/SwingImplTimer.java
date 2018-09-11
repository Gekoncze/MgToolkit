package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.impl.ImplTimer;


public class SwingImplTimer implements ImplTimer {
    final javax.swing.Timer swingTimer;

    public SwingImplTimer(final int delay, EventObserver eventObserver) {
        if(eventObserver == null) throw new RuntimeException();
        swingTimer = new javax.swing.Timer(delay, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                eventObserver.sendEvent(null);
            }
        });
    }
    
    @Override
    public void start(){
        swingTimer.start();
    }
    
    @Override
    public void stop(){
        swingTimer.stop();
    }

    @Override
    public boolean isRunning() {
        return swingTimer.isRunning();
    }
}
