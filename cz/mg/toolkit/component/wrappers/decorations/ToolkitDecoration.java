package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.contents.HorizontalSpacer;
import cz.mg.toolkit.component.controls.buttons.special.MaximizeButton;
import cz.mg.toolkit.component.controls.buttons.special.MinimizeButton;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.buttons.CloseButton;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ToolkitDecoration extends Decoration {
    private static final double DEFAULT_TITLEBAR_WRAP_HEIGHT = 24;
    private static final double DEFAULT_WRAP_BORDER = 2;
    
    private final Panel titleBar = new Panel();
    
    private final ImageContent icon = new ImageContent();
    private final TextContent title = new TextContent();
    private final MinimizeButton minimizeButton = new MinimizeButton();
    private final MaximizeButton maximizeButton = new MaximizeButton();
    private final CloseButton closeButton = new CloseButton();
    
    private double windowMoveStartX;
    private double windowMoveStartY;
    private double mouseMoveStartX;
    private double mouseMoveStartY;
    
    public ToolkitDecoration() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent(){
        setLayout(new VerticalLayout());
        setPadding(this, DEFAULT_WRAP_BORDER);
        setVerticalSpacing(this, DEFAULT_WRAP_BORDER);
    }
    
    private void initComponents(){
        double innerSize = DEFAULT_TITLEBAR_WRAP_HEIGHT - 2*DEFAULT_WRAP_BORDER;
        
        titleBar.setParent(this);
        titleBar.setLayout(new HorizontalLayout());
        setHorizontalSpacing(titleBar, DEFAULT_WRAP_BORDER);
        setFillParentWidth(titleBar);
        setFixedHeight(titleBar, innerSize);
        titleBar.getChildren().addLast(icon);
        titleBar.getChildren().addLast(new HorizontalSpacer());
        titleBar.getChildren().addLast(title);
        titleBar.getChildren().addLast(new HorizontalSpacer());
        titleBar.getChildren().addLast(minimizeButton);
        titleBar.getChildren().addLast(maximizeButton);
        titleBar.getChildren().addLast(closeButton);
        setContext(titleBar, "title");
        setVerticalAlignment(title, 0.5);
        setFixedWidth(icon, innerSize);
        setFixedHeight(icon, innerSize);
        
        setFixedWidth(minimizeButton, innerSize);
        setFixedHeight(minimizeButton, innerSize);
        setFixedWidth(maximizeButton, innerSize);
        setFixedHeight(maximizeButton, innerSize);
        setFixedWidth(closeButton, innerSize);
        setFixedHeight(closeButton, innerSize);
        
        getContentPanel().setParent(null);
        getContentPanel().setParent(this);
        
        setBorder(titleBar, null);
        setPadding(closeButton, 4);
    }
    
    private void addEventListeners(){
        titleBar.getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    Window window = getWindow();
                    titleBar.requestMouseFocus();
                    windowMoveStartX = window.getX();
                    windowMoveStartY = window.getY();
                    mouseMoveStartX = e.getMouse().getScreenX();
                    mouseMoveStartY = e.getMouse().getScreenY();
                }
            }
        });
        titleBar.getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(wasReleased(e) && wasLeftButton(e) && titleBar.hasMouseFocus()){
                    e.consume();
                    titleBar.releaseMouseFocus();
                }
            }
        });
        titleBar.getEventListeners().addLast(new MouseMotionAdapter() {
            @Override
            public void onMouseMotionEventEnter(MouseMotionEvent e) {
                if(titleBar.hasMouseFocus()){
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
        closeButton.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                Window window = getWindow();
                if(window != null) window.close();
            }
        });
    }

    @Override
    public void setIcon(Image icon) {
       this.icon.setImage(icon);
    }
    
    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }
}
