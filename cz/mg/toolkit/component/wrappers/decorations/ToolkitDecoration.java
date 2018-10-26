package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.ToplevelComponent;
import cz.mg.toolkit.component.contents.HorizontalSpacer;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.controls.buttons.ContentButton;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FixedSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.SameAsHeightSizePolicy;


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
            initComponents();
            addEventListeners();
        }
        
        private void initComponent(){
            setLayout(new HorizontalLayout());
            setHorizontalSizePolicy(this, new FillParentSizePolicy());
            setVerticalSizePolicy(this, new FixedSizePolicy());
            setVerticalContentAlignment(this, 0.5);
        }
        
        private void initComponents(){
            getChildren().addLast(icon);
            getChildren().addLast(new HorizontalSpacer());
            getChildren().addLast(title);
            getChildren().addLast(new HorizontalSpacer());
            getChildren().addLast(minimizeButton);
            getChildren().addLast(maximizeButton);
            getChildren().addLast(closeButton);
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
        
        private Window getWindow(){
            ToplevelComponent toplevelComponent = getToplevelComponent();
            if(toplevelComponent == null) return null;
            if(toplevelComponent instanceof Window) return (Window) toplevelComponent;
            return null;
        }
    }
    
    public static class Icon extends ImageContent {
        public Icon() {
            initComponent();
        }
        
        private void initComponent(){
            setHorizontalSizePolicy(this, new SameAsHeightSizePolicy());
            setVerticalSizePolicy(this, new FillParentSizePolicy());
        }
    }
    
    public static class Title extends SinglelineTextContent {
        public Title() {
            initComponent();
        }
        
        private void initComponent(){
            setVerticalAlignment(this, 0.5);
        }
    }
    
    public static abstract class TitlebarButton extends ContentButton {
        public TitlebarButton(Content content) {
            super(content);
            initComponent();
            addEventListeners();
        }
        
        private void initComponent(){
            setHorizontalSizePolicy(this, new SameAsHeightSizePolicy());
            setVerticalSizePolicy(this, new FillParentSizePolicy());
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new ActionAdapter() {
                @Override
                public void onEventEnter(ActionEvent e) {
                    e.consume();
                    Window window = getWindow();
                    if(window != null) windowAction(window);
                }
            });
        }
        
        private Window getWindow(){
            ToplevelComponent toplevelComponent = getToplevelComponent();
            if(toplevelComponent == null) return null;
            if(toplevelComponent instanceof Window) return (Window) toplevelComponent;
            return null;
        }
        
        protected abstract void windowAction(Window window);
    }
    
    public static class MinimizeButton extends TitlebarButton {
        public MinimizeButton() {
            super(new Content());
        }
        
        @Override
        protected void windowAction(Window window) {
            window.setMinimized(!window.isMinimized());
        }
        
        public static class Content extends DrawableContent {
        }
    }
    
    public static class MaximizeButton extends TitlebarButton {
        public MaximizeButton() {
            super(new Content());
        }
        
        @Override
        protected void windowAction(Window window) {
            window.setMaximized(!window.isMaximized());
        }

        public static class Content extends DrawableContent {
        }
    }
    
    public static class CloseButton extends TitlebarButton {
        public CloseButton() {
            super(new Content());
        }
        
        @Override
        protected void windowAction(Window window) {
            window.close();
        }

        public static class Content extends DrawableContent {
        }
    }
}
