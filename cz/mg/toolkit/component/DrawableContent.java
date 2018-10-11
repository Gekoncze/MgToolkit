package cz.mg.toolkit.component;

import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Graphics;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.DrawableComponent;
import cz.mg.toolkit.graphics.Decoration;


public abstract class DrawableContent extends Content implements DrawableComponent {    
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
                Decoration background = getBackground(DrawableContent.this);
                if(background != null) background.draw(g, DrawableContent.this);
            }

            @Override
            public void onDrawEventLeave(Graphics g) {
                Decoration foreground = getForeground(DrawableContent.this);
                if(foreground != null) foreground.draw(g, DrawableContent.this);
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
