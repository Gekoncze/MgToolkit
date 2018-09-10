package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class ContentButton extends Button {
    public final void setWrapSize(int width, int height, int padding){
        setPadding(this, padding);
        Content content = getContent();
        content.setContentWidth(width - getLeftPadding(this) - getRightPadding(this));
        content.setContentHeight(height - getTopPadding(this) - getBottomPadding(this));
    }
    
    public abstract Content getContent();
}
