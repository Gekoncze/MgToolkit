package cz.mg.toolkit.graphics.designers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import cz.mg.toolkit.component.controls.buttons.special.CloseButton;
import cz.mg.toolkit.component.controls.buttons.special.DownScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.LeftScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.RightScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.UpScrollButton;
import cz.mg.toolkit.component.controls.menuitems.StandardMenuItem;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.backgrounds.SolidColorBackground;
import cz.mg.toolkit.graphics.borders.SolidColorBorder;
import cz.mg.toolkit.graphics.images.VectorImage;
import cz.mg.toolkit.utilities.Drawable;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class DefaultDesigner extends ContextDesigner {
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
    
    private static final Font TITLE_FONT = new Font("default", 18, Font.Style.BOLD);
    private static final Font MENU_ITEM_DESCRIPTION_FONT = new Font("default", 18, Font.Style.REGULAR);
    private static final Font MENU_ITEM_SHORTCUT_FONT = new Font("default", 18, Font.Style.ITALIC);
    
    private static final VectorImage LEFT_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(1.0, 0, 0, 0.5);
            g.drawLine(0, 0.5, 1.0, 1.0);
        }
    };
    
    private static final VectorImage RIGHT_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(0, 0, 1.0, 0.5);
            g.drawLine(1.0, 0.5, 0, 1.0);
        }
    };
    
    private static final VectorImage UP_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(0, 1.0, 0.5, 0);
            g.drawLine(0.5, 0, 1.0, 1.0);
        }
    };
    
    private static final VectorImage DOWN_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(0, 0, 0.5, 1.0);
            g.drawLine(0.5, 1.0, 1.0, 0);
        }
    };
    
    private static final VectorImage MINIMIZE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(0, 1.0, 1.0, 1.0);
        }
    };
    
    private static final VectorImage MAXIMIZE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawRectangle(0, 0, 1.0, 1.0);
        }
    };
    
    private static final VectorImage CLOSE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g) {
            g.drawLine(0, 0, 1.0, 1.0);
            g.drawLine(0, 1.0, 1.0, 0);
        }
    };
        
    private final Context defaultContext = new Context() {
        @Override
        public void design(Component component) {
            onDefaultDesign(component);
        }
    };
    
    private final Context titleBarContext = new Context() {
        @Override
        public void design(Component component) {
            onTitleBarDesign(component);
        }
    };

    @Override
    protected final Context getDefaultContext() {
        return defaultContext;
    }

    @Override
    protected Context getComponentContext(Component component) {
        if(component instanceof ToolkitDecoration.TitleBar) return titleBarContext;
        return null;
    }
    
    protected void onDesign(Component component){
        setContrastColor(component, CONTRAST_COLOR);
        if(component instanceof Drawable) setBackground(component, BACKGROUND);
        if(component instanceof Drawable) setBorder(component, BORDER);
        if(component instanceof StandardMenuItem.Description) setFont(component, MENU_ITEM_DESCRIPTION_FONT);
        if(component instanceof StandardMenuItem.Shortcut) setFont(component, MENU_ITEM_SHORTCUT_FONT);
        if(component instanceof ToolkitDecoration.TitleBar) setBorder(component, null);
        if(component instanceof ToolkitDecoration.Title) setFont(component, TITLE_FONT);
        if(component instanceof HorizontalSeparator) setBorder(component, null);
        if(component instanceof VerticalSeparator) setBorder(component, null);
        if(component instanceof Content) setBorder(component, null);
        if(component instanceof LeftScrollButton) ((LeftScrollButton)component).getImageContent().setImage(LEFT_SCROLL_BUTTON_IMAGE);
        if(component instanceof RightScrollButton) ((RightScrollButton)component).getImageContent().setImage(RIGHT_SCROLL_BUTTON_IMAGE);
        if(component instanceof UpScrollButton) ((UpScrollButton)component).getImageContent().setImage(UP_SCROLL_BUTTON_IMAGE);
        if(component instanceof DownScrollButton) ((DownScrollButton)component).getImageContent().setImage(DOWN_SCROLL_BUTTON_IMAGE);
        if(component instanceof CloseButton) ((CloseButton)component).getImageContent().setImage(CLOSE_BUTTON_IMAGE);
        if(component instanceof ToolkitDecoration.CloseButton) ((ToolkitDecoration.CloseButton)component).getImageContent().setImage(CLOSE_BUTTON_IMAGE);
    }
    
    protected void onDefaultDesign(Component component){
        onDesign(component);
        setBackgroundColor(component, BACKGROUND_COLOR);
        setForegroundColor(component, FOREGROUND_COLOR);
        setDisabledBackgroundColor(component, DISABLED_BACKGROUND_COLOR);
        setDisabledForegroundColor(component, DISABLED_FOREGROUND_COLOR);
        setHighlightedBackgroundColor(component, HIGHLIGHTED_BACKGROUND_COLOR);
        setHighlightedForegroundColor(component, HIGHLIGHTED_FOREGROUND_COLOR);
    }
    
    protected void onTitleBarDesign(Component component){
        onDesign(component);
        setBackgroundColor(component, TITLE_BACKGROUND_COLOR);
        setForegroundColor(component, TITLE_FOREGROUND_COLOR);
        setDisabledBackgroundColor(component, TITLE_DISABLED_BACKGROUND_COLOR);
        setDisabledForegroundColor(component, TITLE_DISABLED_FOREGROUND_COLOR);
        setHighlightedBackgroundColor(component, TITLE_HIGHLIGHTED_BACKGROUND_COLOR);
        setHighlightedForegroundColor(component, TITLE_HIGHLIGHTED_FOREGROUND_COLOR);
    }
}
