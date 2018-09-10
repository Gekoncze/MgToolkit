package cz.mg.toolkit.utilities;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.utilities.timers.OneOffTimer;
import cz.mg.toolkit.utilities.timers.SkipRepeatingTimer;


public class KeystrokeRepeater {
    private static final int DEFAULT_BEFORE_REPEAT_DELAY = 750;
    private static final int DEFAULT_REPEAT_DELAY = 50;
    
    private final OneOffTimer waitTimer;
    private final SkipRepeatingTimer repeatTimer;
    
    private EventObserver eventObserver;
    private KeyboardButtonEvent event;

    public KeystrokeRepeater() {
        waitTimer = new OneOffTimer(DEFAULT_BEFORE_REPEAT_DELAY);
        repeatTimer = new SkipRepeatingTimer(DEFAULT_REPEAT_DELAY);
        
        waitTimer.setEventObserver(new EventObserver() {
            @Override
            public void sendEvent(Event e) {
                event.setRepeated(true);
                event.setConsumed(false);
                eventObserver.sendEvent(event);
                repeatTimer.start();
            }
        });
        repeatTimer.setEventObserver(new EventObserver() {
            @Override
            public void sendEvent(Event e) {
                event.setRepeated(true);
                event.setConsumed(false);
                eventObserver.sendEvent(event);
            }
        });
    }
    
    public final boolean isStarted(){
        return waitTimer.isStarted() || repeatTimer.isStarted();
    }
    
    public final void onKeyboardButtonPressedEvent(KeyboardButtonEvent event, EventObserver eventObserver){
        if(event.isRepeated()) return;
        waitTimer.stop();
        repeatTimer.stop();
        
        this.eventObserver = eventObserver;
        this.event = event;
        waitTimer.start();
    }

    public final void onKeyboardButtonReleasedEvent(KeyboardButtonEvent event, EventObserver eventObserver) {
        if(event.isRepeated()) return;
        waitTimer.stop();
        repeatTimer.stop();
    }

    public final OneOffTimer getWaitTimer() {
        return waitTimer;
    }

    public final SkipRepeatingTimer getRepeatTimer() {
        return repeatTimer;
    }
    
    public final void stop(){
        waitTimer.stop();
        repeatTimer.stop();
        eventObserver = null;
        event = null;
    }
}
