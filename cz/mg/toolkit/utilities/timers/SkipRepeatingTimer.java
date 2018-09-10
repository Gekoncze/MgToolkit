package cz.mg.toolkit.utilities.timers;

import cz.mg.toolkit.utilities.Timer;
import static cz.mg.toolkit.utilities.Timer.getCurrentTimeInMilliseconds;
import cz.mg.toolkit.event.events.ActionEvent;


public class SkipRepeatingTimer extends Timer {
    protected long lastTickTime;

    public SkipRepeatingTimer(int delay) {
        super(delay);
    }
    
    @Override
    protected void onCheck() {
        long currentTickTime = getCurrentTimeInMilliseconds();
        long difference = currentTickTime - lastTickTime;
        if(difference >= delay){
            lastTickTime = currentTickTime;
            eventObserver.sendEvent(new ActionEvent(this));
        }
    }

    @Override
    protected void onStart() {
        lastTickTime = getCurrentTimeInMilliseconds();
    }

    @Override
    protected void onStop() {
    }
}
