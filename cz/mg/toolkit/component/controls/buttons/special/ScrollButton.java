package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.controls.buttons.ContentButton;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;


public abstract class ScrollButton extends ContentButton {
    private static final int DEFAULT_SCROLL_SPEED = 16;
    
    private Container scrollPanel;
    private int scrollSpeed = DEFAULT_SCROLL_SPEED;

    public ScrollButton(Content content) {
        super(content);
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                if(scrollPanel == null) return;
                doScroll(scrollPanel);
                relayout();
            }
        });
    }

    public final Container getScrollPanel() {
        return scrollPanel;
    }

    public final void setScrollPanel(Container scrollPanel) {
        this.scrollPanel = scrollPanel;
    }

    public final int getScrollSpeed() {
        return scrollSpeed;
    }

    public final void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }
    
    protected abstract void doScroll(Container scrollPanel);
    
    public static class Content extends DrawableContent {
    }
}
