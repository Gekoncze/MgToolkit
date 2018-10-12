package cz.mg.toolkit.graphics.designers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.controls.CheckBox;
import cz.mg.toolkit.component.controls.HorizontalScrollBar;
import cz.mg.toolkit.component.controls.HorizontalSlider;
import cz.mg.toolkit.component.controls.Menu;
import cz.mg.toolkit.component.controls.MultilineTextInput;
import cz.mg.toolkit.component.controls.RadioButton;
import cz.mg.toolkit.component.controls.SinglelineTextInput;
import cz.mg.toolkit.component.controls.Spinner;
import cz.mg.toolkit.component.controls.VerticalScrollBar;
import cz.mg.toolkit.component.controls.VerticalSlider;
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
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.decorations.BackgroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.ForegroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleBorderDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleDecoration;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.DrawableComponent;


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
    
    private static final Decoration COMMON_BACKGROUND = new BackgroundColorDecoration(new RectangleDecoration());
    private static final Decoration COMMON_FOREGROUND = new ForegroundColorDecoration(new RectangleBorderDecoration());
    
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
    private static final double SPINNER_BUTTON_PADDING = 4;
    private static final double SLIDER_PADDING = 8;
    
    private static final Decoration LEFT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(1.0*w, 0.0*h, 0.0*w, 0.5*h);
            g.drawLine(0.0*w, 0.5*h, 1.0*w, 1.0*h);
        }
    });
    
    private static final Decoration RIGHT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.5*h);
            g.drawLine(1.0*w, 0.5*h, 0.0*w, 1.0*h);
        }
    });
    
    private static final Decoration UP_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h);
        }
    });
    
    private static final Decoration DOWN_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h);
        }
    });
    
    private static final Decoration MINIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.9*h, 1.0*w, 0.9*h);
        }
    });
    
    private static final Decoration MAXIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawRectangleBorder(0.0*w, 0.0*h, 1.0*w, 1.0*h);
        }
    });
    
    private static final Decoration CLOSE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 1.0*h);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 0.0*h);
        }
    });
    
    private static final Decoration UP_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h);
            g.drawLine(0.0*w, 0.8*h, 1.0*w, 0.8*h);
        }
    });
    
    private static final Decoration DOWN_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h);
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.0*h);
        }
    });
    
    private static final Decoration CHECK_BOX_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            boolean s = ((CheckBox)component).isSelected();
            g.drawRectangleBorder(0, 0, w, h);
            if(s) g.drawLine(0.1*w, 0.5*h, 0.4*w, 0.9*h);
            if(s) g.drawLine(0.4*w, 0.8*h, 0.8*w, 0.1*h);
        }
    });
    
    private static final Decoration RADIO_BUTTON_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            boolean s = ((RadioButton)component).isSelected();
            g.drawOvalBorder(0, 0, w, h);
            if(s) g.drawOval(0.2*w, 0.2*h, 0.6*w, 0.6*h);
        }
    });
    
    private static final Decoration HORIZONTAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration(){
        @Override
        protected void onDraw(Graphics g, Component component) {
            HorizontalScrollBar.DraggableBar bar = (HorizontalScrollBar.DraggableBar) component;
            Panel scrollablePanel = bar.getScrollablePanel();
            
            if(scrollablePanel.getContentWidth() <= 0) return;
            
            double ww = component.getWidth();
            double hh = component.getHeight();

            double hp = 3; // horizontal padding
            double vp = 2; // vertical padding
            double s = 2; // spacing (for |||)
            double aw = ww - 2*hp; // available width


            double scroll = getHorizontalScroll(scrollablePanel);
            double minScroll = scrollablePanel.getMinHorizontalScroll();
            double maxScroll = scrollablePanel.getMaxHorizontalScroll();
            double ds = maxScroll - minScroll;

            if(ds <= 0) return;

            double k = scrollablePanel.getWidth() / scrollablePanel.getContentWidth();
            double p = (scroll - minScroll) / (ds);

            double w =  (aw * k);
            double h = hh - 2*vp;

            double fw = aw - w;

            double x = hp + (fw*p);
            double y = vp;

            double rx = x + w/2;

            if(w > 4*s){
                double vpp = vp+4;
                g.drawLine(rx-s, vpp, rx-s, hh-vpp);
                g.drawLine(rx  , vpp, rx  , hh-vpp);
                g.drawLine(rx+s, vpp, rx+s, hh-vpp);
            }

            g.drawRectangleBorder(x, y, w-1, h-1);
        }
    });
    
    private static final Decoration VERTICAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            VerticalScrollBar.DraggableBar bar = (VerticalScrollBar.DraggableBar) component;
            Panel scrollablePanel = bar.getScrollablePanel();
            
            if(scrollablePanel.getContentHeight() <= 0) return;
            
            double ww = component.getWidth();
            double hh = component.getHeight();

            double hp = 3; // horizontal padding
            double vp = 2; // vertical padding
            double s = 2; // spacing (for |||)
            double ah = hh - 2*vp; // available height


            double scroll = getVerticalScroll(scrollablePanel);
            double minScroll = scrollablePanel.getMinVerticalScroll();
            double maxScroll = scrollablePanel.getMaxVerticalScroll();
            double ds = maxScroll - minScroll;

            if(ds <= 0) return;

            double k = scrollablePanel.getHeight() / scrollablePanel.getContentHeight();
            double p = (scroll - minScroll) / (ds);

            double w = ww - 2*hp;
            double h =  (ah * k);

            double fh = ah - h;

            double x = hp;
            double y = vp + (fh*p);

            double ry = y + h/2;

            if(h > 4*s){
                double hpp = hp+4;
                g.drawLine(hpp, ry-s, ww-hpp, ry-s);
                g.drawLine(hpp, ry,   ww-hpp, ry);
                g.drawLine(hpp, ry+s, ww-hpp, ry+s);
            }

            g.drawRectangleBorder(x, y, w-1, h-1);
        }
    });
    
    private static final Decoration HORIZONTAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            double lp = getLeftPadding(component);
            double rp = getRightPadding(component);
            g.drawLine(lp, h/2, w-rp, h/2);
            
            double pos = ((HorizontalSlider)component).getSliderPosition();
            g.drawOval(pos-lp, h/2-lp, lp*2, lp*2);
        }
    });
    
    private static final Decoration VERTICAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            double tp = getTopPadding(component);
            double bp = getBottomPadding(component);
            g.drawLine(w/2, tp, w/2, h-bp);
            
            double pos = ((VerticalSlider)component).getSliderPosition();
            g.drawOval(w/2-tp, pos-tp, tp*2, tp*2);
        }
    });
        
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
        if(component instanceof DrawableComponent) setBackground(component, COMMON_BACKGROUND);
        if(component instanceof DrawableComponent) setForeground(component, COMMON_FOREGROUND);
        if(component instanceof StandardMenuItem.Description) setFont(component, MENU_ITEM_DESCRIPTION_FONT);
        if(component instanceof StandardMenuItem.Shortcut) setFont(component, MENU_ITEM_SHORTCUT_FONT);
        if(component instanceof ToolkitDecoration.TitleBar) setForeground(component, null);
        if(component instanceof ToolkitDecoration.Title) setFont(component, TITLE_FONT);
        if(component instanceof HorizontalSeparator) setForeground(component, null);
        if(component instanceof VerticalSeparator) setForeground(component, null);
        if(component instanceof Content) setForeground(component, null);
        if(component instanceof LeftScrollButton.Content) setForeground(component, LEFT_SCROLL_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof RightScrollButton.Content) setForeground(component, RIGHT_SCROLL_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof UpScrollButton.Content) setForeground(component, UP_SCROLL_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof DownScrollButton.Content) setForeground(component, DOWN_SCROLL_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof CloseButton.Content) setForeground(component, CLOSE_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof ToolkitDecoration.MinimizeButton.Content) setForeground(component, MINIMIZE_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof ToolkitDecoration.MaximizeButton.Content) setForeground(component, MAXIMIZE_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof ToolkitDecoration.CloseButton.Content) setForeground(component, CLOSE_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof Button) setPadding(component, BUTTON_PADDING);
        if(component instanceof ScrollButton) setPadding(component, SCROLL_BUTTON_PADDING);
        if(component instanceof CloseButton) setPadding(component, CLOSE_BUTTON_PADDING);
        if(component instanceof CheckBox) setPadding(component, CHECK_BOX_PADDING);
        if(component instanceof CheckBox) setForeground(component, CHECK_BOX_FOREGROUND);
        if(component instanceof RadioButton) setPadding(component, RADIO_BUTTON_PADDING);
        if(component instanceof RadioButton) setForeground(component, RADIO_BUTTON_FOREGROUND);
        if(component instanceof Spinner.UpButton.Content) setForeground(component, UP_SPINNER_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof Spinner.UpButton) setPadding(component, SPINNER_BUTTON_PADDING);
        if(component instanceof Spinner.DownButton.Content) setForeground(component, DOWN_SPINNER_BUTTON_CONTENT_FOREGROUND);
        if(component instanceof Spinner.DownButton) setPadding(component, SPINNER_BUTTON_PADDING);
        if(component instanceof Menu) setSpacing(component, MENU_SPACING);
        if(component instanceof Menu) setPadding(component, MENU_PADDING);
        if(component instanceof SinglelineTextInput.TextContent) setPadding(component, TEXT_INPUT_PADDING);
        if(component instanceof MultilineTextInput.TextContent) setPadding(component, TEXT_INPUT_PADDING);
        if(component instanceof HorizontalTabArea.CloseButton) setPadding(component, TAB_CLOSE_BUTTON_PADDING);
        if(component instanceof HorizontalTabArea) setPadding(component, TAB_AREA_HEADER_PADDING);
        if(component instanceof HorizontalTabArea.Tab.HorizontalTabHeader) setHorizontalSpacing(component, TAB_AREA_HEADER_SPACING);
        if(component instanceof SplitArea) setSpacing(component, SPLIT_AREA_SPACING);
        if(component instanceof ToolkitDecoration.TitlebarButton) setPadding(component, TITLE_BAR_BUTTON_PADDING); 
        if(component instanceof ToolkitDecoration.TitleBar) setPadding(component, TITLE_BAR_PADDING); 
        if(component instanceof ToolkitDecoration.TitleBar) setHorizontalSpacing(component, TITLE_BAR_SPACING);
        if(component instanceof HorizontalScrollBar.DraggableBar) setForeground(component, HORIZONTAL_SCROLL_BAR_FOREGROUND);
        if(component instanceof VerticalScrollBar.DraggableBar) setForeground(component, VERTICAL_SCROLL_BAR_FOREGROUND);
        if(component instanceof HorizontalSlider) setForeground(component, HORIZONTAL_SLIDER_FOREGROUND);
        if(component instanceof HorizontalSlider) setPadding(component, SLIDER_PADDING);
        if(component instanceof VerticalSlider) setForeground(component, VERTICAL_SLIDER_FOREGROUND);
        if(component instanceof VerticalSlider) setPadding(component, SLIDER_PADDING);
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
