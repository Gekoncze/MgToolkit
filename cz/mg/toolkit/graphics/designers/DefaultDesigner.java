package cz.mg.toolkit.graphics.designers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.controls.CheckBox;
import cz.mg.toolkit.component.controls.Menu;
import cz.mg.toolkit.component.controls.RadioButton;
import cz.mg.toolkit.component.controls.SinglelineTextInput;
import cz.mg.toolkit.component.controls.buttons.special.CloseButton;
import cz.mg.toolkit.component.controls.buttons.special.DownScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.LeftScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.RightScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.ScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.UpScrollButton;
import cz.mg.toolkit.component.controls.menuitems.StandardMenuItem;
import cz.mg.toolkit.component.wrappers.HorizontalTabArea;
import cz.mg.toolkit.component.wrappers.SplitArea;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.backgrounds.SolidColorBackground;
import cz.mg.toolkit.graphics.borders.SolidColorBorder;
import cz.mg.toolkit.graphics.borders.SolidColorOvalBorder;
import cz.mg.toolkit.graphics.images.VectorImage;
import cz.mg.toolkit.utilities.Drawable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


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
    private static final Border RECTANGLE_BORDER = new SolidColorBorder();
    private static final Border OVAL_BORDER = new SolidColorOvalBorder();
    
    private static final Font TITLE_FONT = new Font("default", 18, Font.Style.BOLD);
    private static final Font MENU_ITEM_DESCRIPTION_FONT = new Font("default", 18, Font.Style.REGULAR);
    private static final Font MENU_ITEM_SHORTCUT_FONT = new Font("default", 18, Font.Style.ITALIC);
    
    private static final double BUTTON_PADDING = 8;
    private static final double SCROLL_BUTTON_PADDING = 6;
    private static final double CLOSE_BUTTON_PADDING = 0;
    private static final double MENU_SPACING = 6;
    private static final double MENU_PADDING = 6;
    private static final double TEXT_INPUT_PADDING = 4;
    private static final double TAB_CLOSE_BUTTON_PADDING = 4;
    private static final double TAB_AREA_HEADER_PADDING = 4;
    private static final double TAB_AREA_HEADER_SPACING = 4;
    private static final double SPLIT_AREA_SPACING = 4;
    private static final double TITLE_BAR_BUTTON_PADDING = 4;
    private static final double TITLE_BAR_SPACING = 2;
    private static final double TITLE_BAR_PADDING = 2;
    private static final double CHECK_BOX_PADDING = 4;
    private static final double RADIO_BUTTON_PADDING = 4;
    
    private static final VectorImage LEFT_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(1.0*w, 0.0*h, 0.0*w, 0.5*h);
            g.drawLine(0.0*w, 0.5*h, 1.0*w, 1.0*h);
        }
    };
    
    private static final VectorImage RIGHT_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.5*h);
            g.drawLine(1.0*w, 0.5*h, 0.0*w, 1.0*h);
        }
    };
    
    private static final VectorImage UP_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h);
        }
    };
    
    private static final VectorImage DOWN_SCROLL_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h);
        }
    };
    
    private static final VectorImage MINIMIZE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 0.9*h, 1.0*w, 0.9*h);
        }
    };
    
    private static final VectorImage MAXIMIZE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawRectangle(0.0*w, 0.0*h, 1.0*w, 1.0*h);
        }
    };
    
    private static final VectorImage CLOSE_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 1.0*h);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 0.0*h);
        }
    };
    
    private static final VectorImage CHECK_BOX_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.drawLine(0.0*w, 0.5*h, 0.4*w, 0.9*h);
            g.drawLine(0.4*w, 0.9*h, 1.0*w, 0.0*h);
        }
    };
    
    private static final VectorImage RADIO_BUTTON_IMAGE = new VectorImage() {
        @Override
        public void onDraw(Graphics g, double w, double h) {
            g.fillOval(0.0*w, 0.0*h, 1.0*w, 1.0*h);
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
        setPadding(component, 0);
        setSpacing(component, 0);
        setContrastColor(component, CONTRAST_COLOR);
        if(component instanceof Drawable) setBackground(component, BACKGROUND);
        if(component instanceof Drawable) setBorder(component, RECTANGLE_BORDER);
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
        if(component instanceof ToolkitDecoration.MinimizeButton) ((ToolkitDecoration.MinimizeButton)component).getImageContent().setImage(MINIMIZE_BUTTON_IMAGE);
        if(component instanceof ToolkitDecoration.MaximizeButton) ((ToolkitDecoration.MaximizeButton)component).getImageContent().setImage(MAXIMIZE_BUTTON_IMAGE);
        if(component instanceof ToolkitDecoration.CloseButton) ((ToolkitDecoration.CloseButton)component).getImageContent().setImage(CLOSE_BUTTON_IMAGE);
        if(component instanceof Button) setPadding(component, BUTTON_PADDING);
        if(component instanceof ScrollButton) setPadding(component, SCROLL_BUTTON_PADDING);
        if(component instanceof CloseButton) setPadding(component, CLOSE_BUTTON_PADDING);
        if(component instanceof CheckBox) setPadding(component, CHECK_BOX_PADDING);
        if(component instanceof CheckBox) setBorder(component, RECTANGLE_BORDER);
        if(component instanceof CheckBox) ((CheckBox)component).getImageContent().setImage(CHECK_BOX_IMAGE);
        if(component instanceof RadioButton) setPadding(component, RADIO_BUTTON_PADDING);
        if(component instanceof RadioButton) setBorder(component, OVAL_BORDER);
        if(component instanceof RadioButton) ((RadioButton)component).getImageContent().setImage(RADIO_BUTTON_IMAGE);
        if(component instanceof Menu) setSpacing(component, MENU_SPACING);
        if(component instanceof Menu) setPadding(component, MENU_PADDING);
        if(component instanceof SinglelineTextInput.Text) setPadding(component, TEXT_INPUT_PADDING);
        if(component instanceof HorizontalTabArea.CloseButton) setPadding(component, TAB_CLOSE_BUTTON_PADDING);
        if(component instanceof HorizontalTabArea) setPadding(component, TAB_AREA_HEADER_PADDING);
        if(component instanceof HorizontalTabArea.Tab.HorizontalTabHeader) setHorizontalSpacing(component, TAB_AREA_HEADER_SPACING);
        if(component instanceof SplitArea) setSpacing(component, SPLIT_AREA_SPACING);
        if(component instanceof ToolkitDecoration.TitlebarButton) setPadding(component, TITLE_BAR_BUTTON_PADDING); 
        if(component instanceof ToolkitDecoration.TitleBar) setPadding(component, TITLE_BAR_PADDING); 
        if(component instanceof ToolkitDecoration.TitleBar) setHorizontalSpacing(component, TITLE_BAR_SPACING); 
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
