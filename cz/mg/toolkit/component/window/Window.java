package cz.mg.toolkit.component.window;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.impl.swing.SwingImplWindow;
import cz.mg.toolkit.environment.cursors.ArrowCursor;
import cz.mg.toolkit.environment.cursors.NoCursor;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.WindowCloseAdapter;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardAdapter;
import cz.mg.toolkit.event.adapters.MouseAdapter;
import cz.mg.toolkit.event.adapters.RedesignAdapter;
import cz.mg.toolkit.event.adapters.RedrawAdapter;
import cz.mg.toolkit.event.adapters.RelayoutAdapter;
import cz.mg.toolkit.event.contexts.DesignerEventContext;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.DesignEvent;
import cz.mg.toolkit.event.events.WindowCloseEvent;
import cz.mg.toolkit.event.events.KeyboardEvent;
import cz.mg.toolkit.event.events.MouseEvent;
import cz.mg.toolkit.event.events.RedesignEvent;
import cz.mg.toolkit.event.events.RedrawEvent;
import cz.mg.toolkit.event.events.RelayoutEvent;
import cz.mg.toolkit.graphics.designers.DefaultDesigner;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.KeystrokeRepeater;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;
import cz.mg.toolkit.impl.ImplWindow;


public class Window extends Wrapper {
    private static final Cursor BLANK_CURSOR = new NoCursor();
    
    private final ImplWindow implWindow = new SwingImplWindow();
    private Component keyboardFocus = null;
    private Component mouseFocus = null;
    private Decoration decoration;
    private String title;
    private BitmapImage icon;
    private boolean relayout = false;
    private Cursor cursor = new ArrowCursor();
    private boolean decorated = true;
    private boolean cursorVisible = true;
    private final KeystrokeRepeater keystrokeRepeater = new KeystrokeRepeater();
    
    public Window() {
        initComponent();
        initComponents();
        addEventListeners();
        implWindow.setWindow(this);
    }
    
    private void initComponent(){
        setLayout(new OverlayLayout());
        setDesigner(this, new DefaultDesigner());
    }
    
    private void initComponents(){
        setDecoration(new SystemDecoration());
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                if(e.isRelayout()) relayout = true;
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
                implWindow.redraw();
            }
        });
        
        getEventListeners().addLast(new RelayoutAdapter() {
            @Override
            public void onEventEnter(RelayoutEvent e) {
                e.consume();
                relayout = true;
                implWindow.redraw();
            }
        });
        
        getEventListeners().addLast(new RedesignAdapter() {
            @Override
            public void onEventEnter(RedesignEvent e) {
                DesignEvent event = new DesignEvent();
                event.setEventContext(new DesignerEventContext());
                sendEvent(event);
            }
        });
    }

    public final ImplWindow getImplWindow() {
        return implWindow;
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
    
    private void updateLayout(){
        if(relayout){
            relayout = false;
            layout();
        }
    }
    
    public final void closeImmediately(){
        implWindow.close();
        keystrokeRepeater.stop();
    }
    
    public final boolean isMinimized(){
        return implWindow.isMinimized();
    }
    
    public final void setMinimized(boolean value){
        implWindow.setMinimized(value);
    }
    
    public final boolean isMaximized(){
        return implWindow.isMaximized();
    }
    
    public final void setMaximized(boolean value){
        implWindow.setMaximized(value);
    }
    
    public final boolean isActivated(){
        return implWindow.isActivated();
    }
    
    public final void setActivated(boolean value){
        implWindow.setActivated(value);
    }
    
    public final boolean isResizable(){
        return implWindow.isResizable();
    }
    
    public final void setResizable(boolean value){
        implWindow.setResizable(value);
    }
    
    public final boolean isDecorated(){
        return decorated;
    }
    
    public final void setDecorated(boolean value){
        this.decorated = value;
        if(this.decoration instanceof SystemDecoration){
            implWindow.setDecorated(value);
        } else {
            implWindow.setDecorated(false);
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
        implWindow.setDecorated(decoration instanceof SystemDecoration);
        decoration.setIcon(icon);
        decoration.setTitle(title);
    }
    
    public final void open(){
        implWindow.open();
    }
    
    public final void close(){
        sendEvent(new WindowCloseEvent(this));
    }
    
    public final void center(){
        Display display = Impl.getImplApi().getPrimaryDisplay();
        setX(display.getWidth() / 2 - getWidth() / 2);
        setY(display.getHeight() / 2 - getHeight() / 2);
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
        decoration.setTitle(title);
    }

    public final BitmapImage getIcon() {
        return icon;
    }

    public final void setIcon(BitmapImage icon) {
        this.icon = icon;
        decoration.setIcon(icon);
    }

    public final Cursor getCursor() {
        return cursor;
    }

    public final void setCursor(Cursor cursor) {
        this.cursor = cursor;
        implWindow.setCursor(cursorVisible ? cursor : BLANK_CURSOR);
    }
    
    public final boolean isCursorVisible(){
        return cursorVisible;
    }
    
    public final void setCursorVisible(boolean value){
        this.cursorVisible = value;
        implWindow.setCursor(cursorVisible ? cursor : BLANK_CURSOR);
    }

    public boolean isRelayoutNeeded() {
        return relayout;
    }

    public final KeystrokeRepeater getKeystrokeRepeater() {
        return keystrokeRepeater;
    }
}
