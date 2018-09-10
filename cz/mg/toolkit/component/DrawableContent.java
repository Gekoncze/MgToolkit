package cz.mg.toolkit.component;

import cz.mg.toolkit.event.adapters.DesignAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.events.DesignEvent;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.designers.DefaultDesigner;
import cz.mg.toolkit.utilities.Drawable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class DrawableContent extends Content implements Drawable {
    private static final DefaultDesigner DEFAULT_DESIGNER = new DefaultDesigner();
    
    public DrawableContent() {
        addEventListeners();
        DEFAULT_DESIGNER.design(this);
    }
    
    public DrawableContent(int contentWidth, int contentHeight) {
        super(contentWidth, contentHeight);
        addEventListeners();
        DEFAULT_DESIGNER.design(this);
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                Background background = getBackground(DrawableContent.this);
                if(background != null) background.onDraw(g, DrawableContent.this);
            }

            @Override
            public void onDrawEventLeave(Graphics g) {
                Border border = getBorder(DrawableContent.this);
                if(border != null) border.onDraw(g, DrawableContent.this);
            }
        });
    }
    
    @Override
    public final Color getCurrentBackgroundColor(){
        if(isEffectivelyDisabled()) return getDisabledBackgroundColor(this);
        if(isHighlighted()) return getHighlightedBackgroundColor(this);
        return getBackgroundColor(this);
    }
    
    @Override
    public final Color getCurrentForegroundColor(){
        if(isEffectivelyDisabled()) return getDisabledForegroundColor(this);
        if(isHighlighted()) return getHighlightedForegroundColor(this);
        return getForegroundColor(this);
    }
}
