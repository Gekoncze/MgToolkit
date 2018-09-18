package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.contents.HorizontalSpacer;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.component.controls.buttons.ImageButton;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ToolkitDecoration extends Decoration {
    private final TitleBar titleBar = new TitleBar();
    
    public ToolkitDecoration() {
        initComponent();
        initComponents();
    }
    
    private void initComponent(){
        setLayout(new VerticalLayout());
    }
    
    private void initComponents(){
        getChildren().addFirst(titleBar);
    }

    @Override
    public void setIcon(BitmapImage icon) {
       titleBar.icon.setImage(icon);
    }
    
    @Override
    public void setTitle(String title) {
        titleBar.title.setText(title);
    }
    
    
    public static class TitleBar extends Panel {
        private static final double DEFAULT_HEIGHT = 24;
        
        private final Icon icon = new Icon();
        private final Title title = new Title();
        private final MinimizeButton minimizeButton = new MinimizeButton();
        private final MaximizeButton maximizeButton = new MaximizeButton();
        private final CloseButton closeButton = new CloseButton();
        
        private double windowMoveStartX;
        private double windowMoveStartY;
        private double mouseMoveStartX;
        private double mouseMoveStartY;
        
        public TitleBar() {
            initComponent();
            addEventListeners();
        }
        
        private void initComponent(){
            setLayout(new HorizontalLayout());
            setFillParentWidth(this);
            setFixedHeight(this, DEFAULT_HEIGHT);
            setVerticalContentAlignment(this, 0.5);
            
            getChildren().addLast(icon);
            getChildren().addLast(new HorizontalSpacer());
            getChildren().addLast(title);
            getChildren().addLast(new HorizontalSpacer());
            getChildren().addLast(minimizeButton);
            getChildren().addLast(maximizeButton);
            getChildren().addLast(closeButton);

            setBorder(this, null);
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventLeave(MouseButtonEvent e) {
                    if(wasPressed(e) && wasLeftButton(e)){
                        e.consume();
                        Window window = getWindow();
                        requestMouseFocus();
                        windowMoveStartX = window.getX();
                        windowMoveStartY = window.getY();
                        mouseMoveStartX = e.getMouse().getScreenX();
                        mouseMoveStartY = e.getMouse().getScreenY();
                    }
                }
            });
            getEventListeners().addLast(new MouseButtonAdapter() {
                @Override
                public void onMouseButtonEventEnter(MouseButtonEvent e) {
                    if(wasReleased(e) && wasLeftButton(e) && hasMouseFocus()){
                        e.consume();
                        releaseMouseFocus();
                    }
                }
            });
            getEventListeners().addLast(new MouseMotionAdapter() {
                @Override
                public void onMouseMotionEventEnter(MouseMotionEvent e) {
                    if(hasMouseFocus()){
                        e.consume();
                        Window window = getWindow();
                        if(window == null) return;
                        double dx = e.getMouse().getScreenX() - mouseMoveStartX;
                        double dy = e.getMouse().getScreenY() - mouseMoveStartY;
                        window.setX(windowMoveStartX + dx);
                        window.setY(windowMoveStartY + dy);
                    }
                }
            });
        }
    }
    
    public static class Icon extends ImageContent {
        public Icon() {
            initComponent();
            addEventListeners();
        }
        
        private void initComponent(){
            setFixedWidth(this, 0);
            setFillParentHeight(this);
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new AfterLayoutAdapter() {
                @Override
                public void onEventEnter(AfterLayoutEvent e) {
                    if(getWidth() != getHeight()){
                        setFixedWidth(Icon.this, getHeight());
                    }
                }
            });
        }
    }
    
    public static class Title extends TextContent {
        public Title() {
            initComponent();
        }
        
        private void initComponent(){
            setVerticalAlignment(this, 0.5);
        }
    }
    
    public static abstract class TitlebarButton extends ImageButton {
        public TitlebarButton() {
            initComponent();
            addEventListeners();
        }
        
        private void initComponent(){
            setFixedWidth(this, 0);
            setFillParentHeight(this);
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new AfterLayoutAdapter() {
                @Override
                public void onEventEnter(AfterLayoutEvent e) {
                    if(getWidth() != getHeight()){
                        setFixedWidth(TitlebarButton.this, getHeight());
                    }
                }
            });
            getImageContent().getEventListeners().addFirst(new GraphicsDrawAdapter() {
                @Override
                public void onDrawEventLeave(Graphics g) {
                    g.setColor(getCurrentForegroundColor());
                }
            });
            getEventListeners().addLast(new ActionAdapter() {
                @Override
                public void onEventEnter(ActionEvent e) {
                    e.consume();
                    Window window = getWindow();
                    if(window != null) windowAction(window);
                }
            });
        }
        
        protected abstract void windowAction(Window window);
    }
    
    public static class MinimizeButton extends TitlebarButton {
        @Override
        protected void windowAction(Window window) {
            window.setMinimized(!window.isMinimized());
        }
    }
    
    public static class MaximizeButton extends TitlebarButton {
        @Override
        protected void windowAction(Window window) {
            window.setMaximized(!window.isMaximized());
        }
    }
    
    public static class CloseButton extends TitlebarButton {
        @Override
        protected void windowAction(Window window) {
            window.close();
        }
    }
}
