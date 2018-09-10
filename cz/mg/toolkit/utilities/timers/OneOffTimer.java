package cz.mg.toolkit.utilities.timers;

import cz.mg.toolkit.utilities.Timer;
import static cz.mg.toolkit.utilities.Timer.getCurrentTimeInMilliseconds;
import cz.mg.toolkit.event.events.ActionEvent;


public class OneOffTimer extends Timer {
    protected long startTime;

    public OneOffTimer(int delay) {
        super(delay);
    }
    
    @Override
    protected void onCheck() {
        long currentTime = getCurrentTimeInMilliseconds();
        long difference = currentTime - startTime;
        if(difference >= delay){
            stop();
            eventObserver.sendEvent(new ActionEvent(this));
        }
    }

    @Override
    protected void onStart() {
        startTime = getCurrentTimeInMilliseconds();
    }

    @Override
    protected void onStop() {
    }
}
