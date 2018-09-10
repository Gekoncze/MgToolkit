package cz.mg.toolkit.component.window;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.environment.NativeWindow;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Wrapper;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.environment.cursors.ArrowCursor;
import cz.mg.toolkit.environment.cursors.NoCursor;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.WindowCloseAdapter;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardAdapter;
import cz.mg.toolkit.event.adapters.MouseAdapter;
import cz.mg.toolkit.event.adapters.RedesignAdapter;
import cz.mg.toolkit.event.adapters.RedrawAdapter;
import cz.mg.toolkit.event.adapters.RelayoutAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.DesignEvent;
import cz.mg.toolkit.event.events.WindowCloseEvent;
import cz.mg.toolkit.event.events.KeyboardEvent;
import cz.mg.toolkit.event.events.MouseEvent;
import cz.mg.toolkit.event.events.RedesignEvent;
import cz.mg.toolkit.event.events.RedrawEvent;
import cz.mg.toolkit.event.events.RelayoutEvent;
import cz.mg.toolkit.graphics.Designer;
import cz.mg.toolkit.graphics.designers.DefaultDesigner;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.KeystrokeRepeater;


public class Window extends Wrapper {
    private static final Cursor BLANK_CURSOR = new NoCursor();
    
    private final NativeWindow nativeWindow = new NativeWindow();
    private Component keyboardFocus = null;
    private Component mouseFocus = null;
    private Decoration decoration;
    private String title;
    private Image icon;
    private boolean relayout = false;
    private boolean reshapeLock = false;
    private Cursor cursor = new ArrowCursor();
    private boolean decorated = true;
    private boolean cursorVisible = true;
    private final KeystrokeRepeater keystrokeRepeater = new KeystrokeRepeater();
    private Designer designer = new DefaultDesigner();
    
    public Window() {
        setLayout(new OverlayLayout());
        initComponents();
        addEventListeners();
        nativeWindow.setWindow(this);
    }
    
    private void initComponents(){
        setDecoration(new SystemDecoration());
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                if(e.isRelayout()) relayout = true;
                updatePosition();
                updateSize();
                updateLayout();
            }
        });
        
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                g.setAntialiasing(true);
            }
        });
        
        getEventListeners().addLast(new KeyboardAdapter() {
            @Override
            public void onEventEnter(KeyboardEvent e) {
                if(keyboardFocus != null){
                    e.setFocus(keyboardFocus);
                }
            }
        });
        
        getEventListeners().addLast(new MouseAdapter() {
            @Override
            public void onEventEnter(MouseEvent e) {
                if(mouseFocus != null){
                    e.setFocus(mouseFocus);
                }
            }
        });
        
        getEventListeners().addLast(new WindowCloseAdapter() {
            @Override
            public void onEventLeave(WindowCloseEvent e) {
                closeImmediately();
            }
        });
        
        getEventListeners().addLast(new RedrawAdapter() {
            @Override
            public void onEventEnter(RedrawEvent e) {
                e.consume();
                nativeWindow.redraw();
            }
        });
        
        getEventListeners().addLast(new RelayoutAdapter() {
            @Override
            public void onEventEnter(RelayoutEvent e) {
                e.consume();
                relayout = true;
                nativeWindow.redraw();
            }
        });
        
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventLeave(AfterLayoutEvent e) {
                if(!equalsD(getWidth(), nativeWindow.getWidth()) || !equalsD(getHeight(), nativeWindow.getHeight())){
                    nativeWindow.setSize(getWidth(), getHeight());
                }
            }
        });
        
        getEventListeners().addLast(new RedesignAdapter() {
            @Override
            public void onEventEnter(RedesignEvent e) {
                sendEvent(new DesignEvent(designer));
            }
        });
    }

    public final NativeWindow getNativeWindow() {
        return nativeWindow;
    }

    public final Component getKeyboardFocus() {
        return keyboardFocus;
    }
    
    public final Component getMouseFocus() {
        return mouseFocus;
    }

    public void setKeyboardFocus(Component component) {
        if(component == this) keyboardFocus = null;
        else keyboardFocus = component;
    }

    public void setMouseFocus(Component component) {
        if(component == this) mouseFocus = null;
        else mouseFocus = component;
    }
    
    private void updatePosition(){
        reshapeLock = true;
        if(getX() != nativeWindow.getX()) { setX(nativeWindow.getX()); relayout = true; };
        if(getY() != nativeWindow.getY()) { setY(nativeWindow.getY()); relayout = true; };
        reshapeLock = false;
    }
    
    private void updateSize(){
        reshapeLock = true;
        if(getWidth() != nativeWindow.getWidth()){ setWidth(nativeWindow.getWidth()); relayout = true; };
        if(getHeight() != nativeWindow.getHeight()){ setHeight(nativeWindow.getHeight()); relayout = true; };
        reshapeLock = false;
    }
    
    private void updateLayout(){
        if(relayout){
            relayout = false;
            layout();
        }
    }
    
    public final void close(){
        sendEvent(new WindowCloseEvent(this));
    }
    
    public final void closeImmediately(){
        nativeWindow.closeImmediately();
        keystrokeRepeater.stop();
    }
    
    public final boolean isMinimized(){
        return nativeWindow.isMinimized();
    }
    
    public final void setMinimized(boolean value){
        nativeWindow.setMinimized(value);
    }
    
    public final boolean isMaximized(){
        return nativeWindow.isMaximized();
    }
    
    public final void setMaximized(boolean value){
        nativeWindow.setMaximized(value);
    }
    
    public final boolean isActivated(){
        return nativeWindow.isActivated();
    }
    
    public final void setActivated(boolean value){
        nativeWindow.setActivated(value);
    }
    
    public final boolean isResizable(){
        return nativeWindow.isResizable();
    }
    
    public final void setResizable(boolean value){
        nativeWindow.setResizable(value);
    }
    
    public final boolean isDecorated(){
        return decorated;
    }
    
    public final void setDecorated(boolean value){
        this.decorated = value;
        if(this.decoration instanceof SystemDecoration){
            nativeWindow.setDecorated(value);
        } else {
            nativeWindow.setDecorated(false);
            if(value){
                decoration.setParent(this);
                getContentPanel().setParent(decoration.getContentPanel());
            } else {
                decoration.setParent(null);
                getContentPanel().setParent(this);
            }
        }
    }
    
    public final Decoration getDecoration() {
        return decoration;
    }

    public final void setDecoration(Decoration decoration) {
        this.decoration = decoration;
        decoration.setParent(this);
        getContentPanel().setParent(decoration.getContentPanel());
        nativeWindow.setDecorated(decoration instanceof SystemDecoration);
        decoration.setIcon(icon);
        decoration.setTitle(title);
    }

    public final Designer getDesigner() {
        return designer;
    }

    public final void setDesigner(Designer designer) {
        this.designer = designer;
    }
    
    public final void open(){
        nativeWindow.show();
    }
    
    public final void center(){
        nativeWindow.center();
    }

    @Override
    public final void setX(double x) {
        super.setX(x);
        if(!reshapeLock) nativeWindow.setLocation((int) Math.round(x), (int) Math.round(getY()));
    }

    @Override
    public final void setY(double y) {
        super.setY(y);
        if(!reshapeLock) nativeWindow.setLocation((int) Math.round(getX()), (int) Math.round(y));
    }

    @Override
    public final void setWidth(double width) {
        super.setWidth(width);
        if(!reshapeLock) nativeWindow.setSize((int) Math.round(width), (int) Math.round(getHeight()));
    }

    @Override
    public final void setHeight(double height) {
        super.setHeight(height);
        if(!reshapeLock) nativeWindow.setSize((int) Math.round(getWidth()), (int) Math.round(height));
    }
    
    public final void setContentWidth(double width){
        nativeWindow.setContentWidth((int) Math.round(width));
    }
    
    public final void setContentHeight(double height){
        nativeWindow.setContentHeight((int) Math.round(height));
    }
    
    public final void setContentSize(double width, double height){
        setContentWidth(width);
        setContentHeight(height);
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
        decoration.setTitle(title);
    }

    public final Image getIcon() {
        return icon;
    }

    public final void setIcon(Image icon) {
        this.icon = icon;
        decoration.setIcon(icon);
    }

    public final Cursor getCursor() {
        return cursor;
    }

    public final void setCursor(Cursor cursor) {
        this.cursor = cursor;
        nativeWindow.setCursor(cursorVisible ? cursor : BLANK_CURSOR);
    }
    
    public final boolean isCursorVisible(){
        return cursorVisible;
    }
    
    public final void setCursorVisible(boolean value){
        this.cursorVisible = value;
        nativeWindow.setCursor(cursorVisible ? cursor : BLANK_CURSOR);
    }

    public boolean isRelayoutNeeded() {
        return relayout;
    }

    public final KeystrokeRepeater getKeystrokeRepeater() {
        return keystrokeRepeater;
    }
    
    private static boolean equalsD(double a, double b){
        return Math.abs(a - b) < 0.0001;
    }
}
