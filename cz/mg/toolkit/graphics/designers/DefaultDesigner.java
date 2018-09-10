package cz.mg.toolkit.graphics.designers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.controls.buttons.special.MinimizeButton;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Designer;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.backgrounds.SolidColorBackground;
import cz.mg.toolkit.graphics.borders.SolidColorBorder;
import cz.mg.toolkit.utilities.Drawable;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class DefaultDesigner implements Designer {
    private static final String TITLE_CONTEXT = "title";
    private static final String MENU_ITEM_CONTEXT = "menu item";
    private static final String MENU_ITEM_SHORTCUT_CONTEXT = "menu item shortcut";
    
    private static final Color BACKGROUND_COLOR = new Color(0, 16, 0, 255);
    private static final Color FOREGROUND_COLOR = new Color(0, 220, 0, 255);
    
    private static final Color DISABLED_BACKGROUND_COLOR = new Color(16, 16, 16, 255);
    private static final Color DISABLED_FOREGROUND_COLOR = new Color(220, 220, 220, 255);
    
    private static final Color HIGHLIGHTED_BACKGROUND_COLOR = new Color(0, 48, 0, 255);
    private static final Color HIGHLIGHTED_FOREGROUND_COLOR = new Color(0, 255, 0, 255);
    
    private static final Color TITLE_BACKGROUND_COLOR = new Color(0, 224, 0, 255);
    private static final Color TITLE_FOREGROUND_COLOR = new Color(0, 16, 0, 255);
    
    private static final Color TITLE_DISABLED_BACKGROUND_COLOR = new Color(224, 224, 224, 255);
    private static final Color TITLE_DISABLED_FOREGROUND_COLOR = new Color(16, 16, 16, 255);
    
    private static final Color TITLE_HIGHLIGHTED_BACKGROUND_COLOR = new Color(0, 255, 0, 255);
    private static final Color TITLE_HIGHLIGHTED_FOREGROUND_COLOR = new Color(0, 32, 0, 255);
    
    private static final Color CONTRAST_COLOR = new Color(255, 0, 0, 255);
    
    private static final Background BACKGROUND = new SolidColorBackground();
    private static final Border BORDER = new SolidColorBorder();
    
    private static final Font MENU_ITEM_FONT = new Font("default", 12, Font.Style.REGULAR);
    private static final Font MENU_ITEM_SHORTCUT_FONT = new Font("default", 12, Font.Style.REGULAR);
    
    @Override
    public void design(Component component) {
        boolean isTitleContext = false;
        Component current = component;
        while(current != null){
            if(TITLE_CONTEXT.equals(getContext(current))) isTitleContext = true;
            current = current.getParent();
        }
        
        if(!isTitleContext){
            setBackgroundColor(component, BACKGROUND_COLOR);
            setForegroundColor(component, FOREGROUND_COLOR);
            setDisabledBackgroundColor(component, DISABLED_BACKGROUND_COLOR);
            setDisabledForegroundColor(component, DISABLED_FOREGROUND_COLOR);
            setHighlightedBackgroundColor(component, HIGHLIGHTED_BACKGROUND_COLOR);
            setHighlightedForegroundColor(component, HIGHLIGHTED_FOREGROUND_COLOR);
        } else {
            System.out.println("is title: " + component.getClass().getSimpleName());
            setBackgroundColor(component, TITLE_BACKGROUND_COLOR);
            setForegroundColor(component, TITLE_FOREGROUND_COLOR);
            setDisabledBackgroundColor(component, TITLE_DISABLED_BACKGROUND_COLOR);
            setDisabledForegroundColor(component, TITLE_DISABLED_FOREGROUND_COLOR);
            setHighlightedBackgroundColor(component, TITLE_HIGHLIGHTED_BACKGROUND_COLOR);
            setHighlightedForegroundColor(component, TITLE_HIGHLIGHTED_FOREGROUND_COLOR);
        }
        
        setContrastColor(component, CONTRAST_COLOR);
        
        if(component instanceof Drawable) setBackground(component, BACKGROUND);
        if(component instanceof Drawable) setBorder(component, BORDER);
        
        if(MENU_ITEM_CONTEXT.equals(getContext(component))) setFont(component, MENU_ITEM_FONT);
        if(MENU_ITEM_SHORTCUT_CONTEXT.equals(getContext(component))) setFont(component, MENU_ITEM_SHORTCUT_FONT);
        if(TITLE_CONTEXT.equals(getContext(component))) setBorder(component, null);
        
        if(component instanceof HorizontalSeparator) setBorder(component, null);
        if(component instanceof VerticalSeparator) setBorder(component, null);
        if(component instanceof Content) setBorder(component, null);
    }
}
