package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.controls.buttons.ImageButton;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.graphics.Graphics;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class ScrollButton extends ImageButton {
    private static final int DEFAULT_SCROLL_SPEED = 16;
    private static final int DEFAULT_PADDING = 6;
    private static final int DEFAULT_SIZE = 24;
    
    private Container scrollPanel;
    private int scrollSpeed = DEFAULT_SCROLL_SPEED;

    public ScrollButton() {
        initComponent();
        addEventListeners();
    }
    
    private void initComponent(){
        setFixedSize(this, DEFAULT_SIZE, DEFAULT_SIZE);
        setPadding(this, DEFAULT_PADDING);
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
        getImageContent().getEventListeners().addFirst(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                g.setColor(getCurrentForegroundColor());
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
}
