package cz.mg.toolkit.component;

import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.Drawable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class DrawableContent extends Content implements Drawable {    
    public DrawableContent() {
        addEventListeners();
    }
    
    public DrawableContent(int contentWidth, int contentHeight) {
        super(contentWidth, contentHeight);
        addEventListeners();
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
        if(isHighlighted(this)) return getHighlightedBackgroundColor(this);
        return getBackgroundColor(this);
    }
    
    @Override
    public final Color getCurrentForegroundColor(){
        if(isEffectivelyDisabled()) return getDisabledForegroundColor(this);
        if(isHighlighted(this)) return getHighlightedForegroundColor(this);
        return getForegroundColor(this);
    }
}
