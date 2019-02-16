package cz.mg.toolkit.component.window;

import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.ToplevelComponent;
import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
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
import cz.mg.toolkit.graphics.designer.Designer;
import cz.mg.toolkit.graphics.designer.designers.DesignersLocation;
import cz.mg.toolkit.graphics.designer.loader.task.DesignerLoader;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.KeystrokeRepeater;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;
import cz.mg.toolkit.impl.ImplWindow;


public class Window extends Wrapper implements ToplevelComponent {
    public static final String DEFAULT_DESIGN_NAME = "window";
    public static Designer defaultDesigner = loadDesigner();
    
    private final ImplWindow implWindow = Impl.getImplApi().createWindow();
    private Component keyboardFocus = null;
    private Component mouseFocus = null;
    private Decoration decoration;
    private String title;
    private BitmapImage icon;
    private boolean relayout = true;
    private boolean decorated = true;
    private boolean cursorVisible = true;
    private final KeystrokeRepeater keystrokeRepeater = new KeystrokeRepeater();
    private boolean autoWrap = false;
    private boolean opened = false;
    
    public Window() {
        initComponent();
        initComponents();
        addEventListeners();
        implWindow.setWindow(this);
    }
    
    private void initComponent(){
        setLayout(new OverlayLayout());
        setDesigner(this, defaultDesigner);
        design();
    }

    private static Designer loadDesigner(){
        DesignerLoader loader = new DesignerLoader();
        return loader.load(DesignersLocation.class.getResourceAsStream("DefaultDesigner"));
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

    @Override
    public final Component getKeyboardFocus() {
        return keyboardFocus;
    }
    
    @Override
    public final Component getMouseFocus() {
        return mouseFocus;
    }

    @Override
    public void setKeyboardFocus(Component component) {
        if(component == this) keyboardFocus = null;
        else keyboardFocus = component;
    }

    @Override
    public void setMouseFocus(Component component) {
        if(component == this) mouseFocus = null;
        else mouseFocus = component;
    }
    
    private void updateLayout(){
        if(relayout){
            relayout = false;
            if(autoWrap) wrap();
            else layout();
        }
    }
    
    public final void closeImmediately(){
        opened = false;
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
        if(this.decoration != null) this.decoration.setParent(null);
        this.decoration = decoration;
        decoration.setParent(this);
        getContentPanel().setParent(decoration.getContentPanel());
        implWindow.setDecorated(decoration instanceof SystemDecoration);
    }

    public final boolean isOpened() {
        return opened;
    }
    
    public final void open(){
        opened = true;
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
    }

    public final BitmapImage getIcon() {
        return icon;
    }

    public final void setIcon(BitmapImage icon) {
        this.icon = icon;
    }
    
    public final boolean isCursorVisible(){
        return cursorVisible;
    }
    
    public final void setCursorVisible(boolean value){
        this.cursorVisible = value;
    }

    public boolean isRelayoutNeeded() {
        return relayout;
    }

    @Override
    public final KeystrokeRepeater getKeystrokeRepeater() {
        return keystrokeRepeater;
    }

    public final boolean isAutoWrap() {
        return autoWrap;
    }

    public final void setAutoWrap(boolean autoWrap) {
        this.autoWrap = autoWrap;
    }
}
