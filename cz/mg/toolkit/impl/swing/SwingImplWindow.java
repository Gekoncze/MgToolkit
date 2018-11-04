package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.component.window.PopupWindow;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.contexts.GraphicsEventContext;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.DrawEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import java.awt.Frame;
import javax.swing.JFrame;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.DisplayResolutionEvent;
import cz.mg.toolkit.event.events.WindowCloseEvent;
import cz.mg.toolkit.event.events.WindowStateEvent;
import cz.mg.toolkit.event.events.WindowStateEvent.StateChange;
import cz.mg.toolkit.graphics.images.BitmapImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import cz.mg.toolkit.impl.ImplWindow;
import cz.mg.toolkit.impl.synchronization.OneWaySynchronization;
import cz.mg.toolkit.impl.synchronization.Synchronization;
import cz.mg.toolkit.impl.synchronization.TwoWaySynchronization;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import javax.swing.Timer;


public class SwingImplWindow implements EventObserver, ImplWindow {
    private final SwingImplApi api;
    
    JFrame jframe;
    private Window window;
    private boolean relayout = true;
    
    private final Timer synchronizationTimer = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronizeWindow();
        }
    });

    public SwingImplWindow(SwingImplApi api) {
        this.api = api;
        initNativeComponents();
        disableExternalSynchronization();
    }
    
    private void initNativeComponents(){
        jframe = new JFrame(){
            @Override
            public void paint(java.awt.Graphics g) {
                synchronizeWindow();
                if(api.SWING_DISPLAY_INSTANCE.updateGraphicsBuffer()) sendEvent(new DisplayResolutionEvent());
                window.sendEvent(new BeforeDrawEvent(relayout));
                relayout = false;
                if(!window.isRelayoutNeeded()) sendEvent(addGraphicsContext(new DrawEvent()));
                g.drawImage(api.SWING_DISPLAY_INSTANCE.getGraphicsBuffer(), -getX(), -getY(), jframe);
            }
        };
        
        jframe.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        jframe.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                relayout();
            }

            @Override
            public void componentMoved(java.awt.event.ComponentEvent e) {
                relayout();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                onWindowShown();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                onWindowHidden();
            }
        });
        
        jframe.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                boolean change = api.MOUSE_INSTANCE.isButtonPressed(e.getButton()) == false;
                api.MOUSE_INSTANCE.pressButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(api.MOUSE_INSTANCE, e.getButton(), change, false)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                boolean change = api.MOUSE_INSTANCE.isButtonReleased(e.getButton()) == false;
                api.MOUSE_INSTANCE.releaseButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(api.MOUSE_INSTANCE, e.getButton(), false, change)));
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
            }
        });
        
        jframe.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                double mx = th(e.getXOnScreen());
                double my = tv(e.getYOnScreen());
                double dx = mx - api.MOUSE_INSTANCE.getScreenX();
                double dy = my - api.MOUSE_INSTANCE.getScreenY();
                api.MOUSE_INSTANCE.setScreenX(mx);
                api.MOUSE_INSTANCE.setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(api.MOUSE_INSTANCE, dx, dy)));
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                enableExternalSynchronization();
                double mx = th(e.getXOnScreen());
                double my = tv(e.getYOnScreen());
                double dx = mx - api.MOUSE_INSTANCE.getScreenX();
                double dy = my - api.MOUSE_INSTANCE.getScreenY();
                api.MOUSE_INSTANCE.setScreenX(mx);
                api.MOUSE_INSTANCE.setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(api.MOUSE_INSTANCE, dx, dy)));
            }
        });
        
        jframe.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                MouseWheelEvent.Direction direction = e.getWheelRotation() > 0 ? MouseWheelEvent.Direction.DOWN : MouseWheelEvent.Direction.UP;
                sendEvent(addInputContext(new MouseWheelEvent(api.MOUSE_INSTANCE, direction)));
            }
        });
        
        jframe.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int button = SwingImplKeyboard.codeLocationToButton(e.getKeyCode(), e.getKeyLocation());
                char ch = SwingImplKeyboard.getAndSanitizeCharacter(api, e);
                boolean repeated = api.KEYBOARD_INSTANCE.isButtonPressed(button);
                boolean change = api.KEYBOARD_INSTANCE.isButtonPressed(button) == false;
                api.KEYBOARD_INSTANCE.pressButton(button);
                api.KEYBOARD_INSTANCE.pressCharacter(ch);
                Event event = new KeyboardButtonEvent(api.KEYBOARD_INSTANCE, button, ch, false, change, false);
                if(!repeated) sendEvent(addInputContext(event));
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                int button = SwingImplKeyboard.codeLocationToButton(e.getKeyCode(), e.getKeyLocation());
                char ch = SwingImplKeyboard.getAndSanitizeCharacter(api, e);
                boolean repeated = api.KEYBOARD_INSTANCE.isButtonReleased(button);
                boolean change = api.KEYBOARD_INSTANCE.isButtonReleased(button) == false;
                api.KEYBOARD_INSTANCE.releaseButton(button);
                api.KEYBOARD_INSTANCE.releaseCharacter(ch);
                Event event = new KeyboardButtonEvent(api.KEYBOARD_INSTANCE, button, ch, false, false, change);
                if(!repeated) sendEvent(addInputContext(event));
            }
        });
        
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendEvent(new WindowCloseEvent(window));
            }

            @Override
            public void windowActivated(WindowEvent e) {
                onWindowActivated();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                onWindowDeactivated();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                onWindowHidden();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                onWindowShown();
            }
        });
        
        jframe.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                onWindowStateChanged(StateChange.OTHER);
            }
        });
        
        jframe.setSize(1, 1); // bug fix
        
        jframe.setFocusTraversalKeysEnabled(false);
    }
    
    private void onWindowActivated(){
        api.KEYBOARD_INSTANCE.reset();
        api.MOUSE_INSTANCE.reset();
        onWindowStateChanged(StateChange.ACTIVATED);
    }
    
    private void onWindowDeactivated(){
        api.KEYBOARD_INSTANCE.reset();
        api.MOUSE_INSTANCE.reset();
        onWindowStateChanged(StateChange.DEACTIVATED);
    }
    
    private void onWindowShown(){
        synchronizationTimer.start();
        relayout();
        onWindowStateChanged(StateChange.SHOWN);
    }
    
    private void onWindowHidden(){
        disableExternalSynchronization();
        synchronizationTimer.stop();
        onWindowStateChanged(StateChange.HIDDEN);
    }
    
    private void onWindowStateChanged(StateChange stateChange){
        sendEvent(new WindowStateEvent(window, stateChange));
    }
    
    private double th(int value){
        return api.DISPLAY_INSTANCE.pixelsToMillimetersH(value);
    }
    
    private double tv(int value){
        return api.DISPLAY_INSTANCE.pixelsToMillimetersV(value);
    }
    
    private int trh(double value){
        return (int) Math.round(api.DISPLAY_INSTANCE.millimetersToPixelsH(value));
    }
    
    private int trv(double value){
        return (int) Math.round(api.DISPLAY_INSTANCE.millimetersToPixelsV(value));
    }
    
    @Override
    public final void sendEvent(Event e) {
        if(window != null) window.sendEvent(e);
    }
    
    private Event addGraphicsContext(Event event){
        event.setEventContext(new GraphicsEventContext(api.SWING_DISPLAY_INSTANCE.getGraphics(api.DISPLAY_INSTANCE)));
        return event;
    }
    
    private Event addInputContext(Event event){
        event.setEventContext(new InputEventContext(api.MOUSE_INSTANCE.getScreenX(), api.MOUSE_INSTANCE.getScreenY()));
        return event;
    }

    @Override
    public final Window getWindow() {
        return window;
    }

    @Override
    public final void setWindow(Window window) {
        this.window = window;
    }
    
    @Override
    public final boolean isDecorated(){
        return !jframe.isUndecorated();
    }
    
    @Override
    public final void setDecorated(boolean value){
        if(jframe.isUndecorated() == !value) return;
        boolean wasVisible = jframe.isVisible();
        if(wasVisible) jframe.dispose();
        jframe.setUndecorated(!value);
        if(wasVisible) jframe.setVisible(true);
    }
    
    @Override
    public final double getLeftInsets(){
        return th(jframe.getInsets().left);
    }
    
    @Override
    public final double getRightInsets(){
        return th(jframe.getInsets().right);
    }
    
    @Override
    public final double getTopInsets(){
        return tv(jframe.getInsets().top);
    }
    
    @Override
    public final double getBottomInsets(){
        return tv(jframe.getInsets().bottom);
    }
    
    @Override
    public final boolean isMinimized() {
        return (jframe.getExtendedState() & Frame.ICONIFIED) != 0;
    }

    @Override
    public final void setMinimized(boolean value) {
        if(value){
            jframe.setExtendedState(jframe.getExtendedState() | Frame.ICONIFIED);
        } else {
            jframe.setExtendedState(jframe.getExtendedState() & (~Frame.ICONIFIED));
        }
    }

    @Override
    public final boolean isMaximized() {
        return (jframe.getExtendedState() & Frame.MAXIMIZED_BOTH) != 0;
    }
    
    @Override
    public final void setMaximized(boolean value) {
        if(value){
            jframe.setExtendedState(jframe.getExtendedState() | Frame.MAXIMIZED_BOTH);
        } else {
            jframe.setExtendedState(jframe.getExtendedState() & (~Frame.MAXIMIZED_BOTH));
        }
    }

    @Override
    public final boolean isActivated() {
        return jframe.isActive();
    }
    
    @Override
    public final void open(){
        jframe.setVisible(true);
    }
    
    @Override
    public final void close(){
        jframe.dispose();
    }
    
    @Override
    public final void redraw(){
        jframe.repaint();
    }
    
    private void relayout(){
        relayout = true;
        jframe.repaint();
    }
    
    private void synchronizeWindow(){
        if(isMinimized() || isMaximized()) disableInternalSynchronization();
        else enableInternalSynchronization();
        horizontalPositionSynchronization.updateValue();
        verticalPositionSynchronization.updateValue();
        horizontalSizeSynchronization.updateValue();
        verticalSizeSynchronization.updateValue();
        iconSynchronization.updateValue();
        titleSynchronization.updateValue();
        cursorSynchronization.updateValue();
    }
    
    private void enableExternalSynchronization(){
        horizontalPositionSynchronization.setExternalToInternalEnabled(true);
        verticalPositionSynchronization.setExternalToInternalEnabled(true);
        horizontalSizeSynchronization.setExternalToInternalEnabled(true);
        verticalSizeSynchronization.setExternalToInternalEnabled(true);
    }
    
    private void disableExternalSynchronization(){
        horizontalPositionSynchronization.setExternalToInternalEnabled(false);
        verticalPositionSynchronization.setExternalToInternalEnabled(false);
        horizontalSizeSynchronization.setExternalToInternalEnabled(false);
        verticalSizeSynchronization.setExternalToInternalEnabled(false);
    }
    
    private void enableInternalSynchronization(){
        horizontalPositionSynchronization.setInternalToExternalEnabled(true);
        verticalPositionSynchronization.setInternalToExternalEnabled(true);
        horizontalSizeSynchronization.setInternalToExternalEnabled(true);
        verticalSizeSynchronization.setInternalToExternalEnabled(true);
    }
    
    private void disableInternalSynchronization(){
        horizontalPositionSynchronization.setInternalToExternalEnabled(false);
        verticalPositionSynchronization.setInternalToExternalEnabled(false);
        horizontalSizeSynchronization.setInternalToExternalEnabled(false);
        verticalSizeSynchronization.setInternalToExternalEnabled(false);
    }
    
    private final TwoWaySynchronization horizontalPositionSynchronization = new TwoWaySynchronization<Integer>() {
        @Override
        public Integer getInternalValue() {
            return trh(window.getX());
        }

        @Override
        public void setInternalValue(Integer value) {
            window.setX(th(value));
        }

        @Override
        public Integer getExternalValue() {
            return jframe.getX();
        }

        @Override
        public void setExternalValue(Integer value) {
            jframe.setLocation(value, jframe.getY());
        }
    };
    
    private final TwoWaySynchronization verticalPositionSynchronization = new TwoWaySynchronization<Integer>() {
        @Override
        public Integer getInternalValue() {
            return trv(window.getY());
        }

        @Override
        public void setInternalValue(Integer value) {
            window.setY(tv(value));
        }

        @Override
        public Integer getExternalValue() {
            return jframe.getY();
        }

        @Override
        public void setExternalValue(Integer value) {
            jframe.setLocation(jframe.getX(), value);
        }
    };
    
    private final TwoWaySynchronization horizontalSizeSynchronization = new TwoWaySynchronization<Integer>() {
        @Override
        public Integer getInternalValue() {
            return trh(window.getWidth());
        }

        @Override
        public void setInternalValue(Integer value) {
            window.setWidth(th(value));
        }

        @Override
        public Integer getExternalValue() {
            return jframe.getWidth();
        }

        @Override
        public void setExternalValue(Integer value) {
            jframe.setSize(value, jframe.getHeight());
        }
    };
    
    private final TwoWaySynchronization verticalSizeSynchronization = new TwoWaySynchronization<Integer>() {
        @Override
        public Integer getInternalValue() {
            return trv(window.getHeight());
        }

        @Override
        public void setInternalValue(Integer value) {
            window.setHeight(tv(value));
        }

        @Override
        public Integer getExternalValue() {
            return jframe.getHeight();
        }

        @Override
        public void setExternalValue(Integer value) {
            jframe.setSize(jframe.getWidth(), value);
        }
    };
    
    private final Synchronization iconSynchronization = new OneWaySynchronization<BitmapImage>() {
        @Override
        public BitmapImage getInternalValue() {
            return window.getIcon();
        }

        @Override
        public void setExternalValue(BitmapImage value) {
            if(value == null) jframe.setIconImage(null);
            else jframe.setIconImage(((SwingImplImage)value.getImplImage()).swingImage);
        }
    };
    
    private final Synchronization titleSynchronization = new OneWaySynchronization<String>() {
        @Override
        public String getInternalValue() {
            if(window.getTitle() == null) return "";
            else return window.getTitle();
        }

        @Override
        public void setExternalValue(String value) {
            jframe.setTitle(value);
        }
    };
    
    private final Synchronization cursorSynchronization = new OneWaySynchronization<Cursor>() {
        @Override
        public Cursor getInternalValue() {
            return api.MOUSE_INSTANCE.getCursor();
        }

        @Override
        public void setExternalValue(Cursor value) {
            if(value == null) jframe.setCursor(null);
            else jframe.setCursor(((SwingImplCursor)value.getImplCursor()).swingCursor);
        }
    };
}
