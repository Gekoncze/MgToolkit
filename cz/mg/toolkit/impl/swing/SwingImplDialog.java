package cz.mg.toolkit.impl.swing;

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
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.DisplayResolutionEvent;
import cz.mg.toolkit.event.events.WindowCloseEvent;
import cz.mg.toolkit.event.events.WindowStateEvent;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplDialog;
import cz.mg.toolkit.impl.synchronization.OneWaySynchronization;
import cz.mg.toolkit.impl.synchronization.Synchronization;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import javax.swing.JDialog;
import javax.swing.Timer;


public class SwingImplDialog implements EventObserver, ImplDialog {
    private final SwingImplApi api;
    
    JDialog jdialog;
    private Window window;
    
    private boolean relayout = true;
    
    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;
    private int lastWidth = Integer.MIN_VALUE;
    private int lastHeight = Integer.MIN_VALUE;
    
    private double lastWindowX = Double.MIN_VALUE;
    private double lastWindowY = Double.MIN_VALUE;
    private double lastWindowWidth = Double.MIN_VALUE;
    private double lastWindowHeight = Double.MIN_VALUE;
    
    private boolean ignoreExternalChange = true;
    
    private final Timer synchronizationTimer = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronizeWindow();
        }
    });

    public SwingImplDialog(SwingImplApi api, SwingImplWindow window) {
        this.api = api;
        initNativeComponents(window.jframe);
    }
    
    public SwingImplDialog(SwingImplApi api, SwingImplDialog dialog) {
        this.api = api;
        initNativeComponents(dialog.jdialog);
    }
    
    private void initNativeComponents(java.awt.Window parent){
        jdialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL){
            @Override
            public void paint(java.awt.Graphics g) {
                synchronizeWindow();
                if(api.SWING_DISPLAY_INSTANCE.updateGraphicsBuffer()) sendEvent(new DisplayResolutionEvent());
                window.sendEvent(new BeforeDrawEvent(relayout));
                relayout = false;
                if(!window.isRelayoutNeeded()) sendEvent(addGraphicsContext(new DrawEvent()));
                g.drawImage(api.SWING_DISPLAY_INSTANCE.getGraphicsBuffer(), -getX(), -getY(), jdialog);
            }
        };
        
        jdialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        jdialog.addComponentListener(new java.awt.event.ComponentAdapter() {
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
        
        jdialog.addMouseListener(new java.awt.event.MouseListener() {
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
        
        jdialog.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
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
                ignoreExternalChange = false;
                double mx = th(e.getXOnScreen());
                double my = tv(e.getYOnScreen());
                double dx = mx - api.MOUSE_INSTANCE.getScreenX();
                double dy = my - api.MOUSE_INSTANCE.getScreenY();
                api.MOUSE_INSTANCE.setScreenX(mx);
                api.MOUSE_INSTANCE.setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(api.MOUSE_INSTANCE, dx, dy)));
            }
        });
        
        jdialog.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                MouseWheelEvent.Direction direction = e.getWheelRotation() > 0 ? MouseWheelEvent.Direction.DOWN : MouseWheelEvent.Direction.UP;
                sendEvent(addInputContext(new MouseWheelEvent(api.MOUSE_INSTANCE, direction)));
            }
        });
        
        jdialog.addKeyListener(new java.awt.event.KeyListener() {
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
        
        jdialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendEvent(new WindowCloseEvent(window));
            }

            @Override
            public void windowActivated(WindowEvent e) {
                api.KEYBOARD_INSTANCE.reset();
                api.MOUSE_INSTANCE.reset();
                sendEvent(new WindowStateEvent(window));
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                sendEvent(new WindowStateEvent(window));
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
        
        jdialog.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                sendEvent(new WindowStateEvent(window));
            }
        });
        
        jdialog.setSize(1, 1); // bug fix
        
        jdialog.setFocusTraversalKeysEnabled(false);
    }
    
    private void onWindowShown(){
        synchronizationTimer.start();
        relayout();
    }
    
    private void onWindowHidden(){
        synchronizationTimer.stop();
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
        return !jdialog.isUndecorated();
    }
    
    @Override
    public final void setDecorated(boolean value){
        if(jdialog.isUndecorated() == !value) return;
        boolean wasVisible = jdialog.isVisible();
        if(wasVisible) jdialog.dispose();
        jdialog.setUndecorated(!value);
        if(wasVisible) jdialog.setVisible(true);
    }
    
    @Override
    public final double getLeftInsets(){
        return th(jdialog.getInsets().left);
    }
    
    @Override
    public final double getRightInsets(){
        return th(jdialog.getInsets().right);
    }
    
    @Override
    public final double getTopInsets(){
        return tv(jdialog.getInsets().top);
    }
    
    @Override
    public final double getBottomInsets(){
        return tv(jdialog.getInsets().bottom);
    }

    @Override
    public final boolean isResizable() {
        return jdialog.isResizable();
    }

    @Override
    public final void setResizable(boolean value) {
        jdialog.setResizable(value);
    }
    
    @Override
    public final void open(){
        jdialog.setVisible(true);
    }
    
    @Override
    public final void close(){
        jdialog.dispose();
    }
    
    @Override
    public final void redraw(){
        jdialog.repaint();
    }
    
    private void relayout(){
        relayout = true;
        jdialog.repaint();
    }
    
    private void synchronizeWindow(){
        synchronizeWindowPosition();
        synchronizeWindowSize();
        synchronizeWindowOther();
    }
    
    private void synchronizeWindowPosition(){
        boolean minimizedOrMaximized = false;
        
        double currentWindowX = window.getX();
        double currentWindowY = window.getY();
        boolean internalChange = currentWindowX != lastWindowX || currentWindowY != lastWindowY;
        
        int currentX = jdialog.getX();
        int currentY = jdialog.getY();
        boolean externalChange = currentX != lastX || currentY != lastY;
        if(ignoreExternalChange) externalChange = false;
        
        int expectedX = trh(window.getX());
        int expectedY = trv(window.getY());
        boolean mismatch = expectedX != currentX || expectedY != currentY;
        
        if(externalChange && minimizedOrMaximized){
            window.setX(th(currentX));
            window.setY(tv(currentY));
            redraw();
        } else if(internalChange){
            jdialog.setLocation(expectedX, expectedY);
            redraw();
        } else if(externalChange){
            window.setX(th(currentX));
            window.setY(tv(currentY));
            redraw();
        } else if(mismatch){
            jdialog.setLocation(expectedX, expectedY);
            redraw();
        }
        
        lastWindowX = window.getX();
        lastWindowY = window.getY();
        lastX = jdialog.getX();
        lastY = jdialog.getY();
    }
    
    private void synchronizeWindowSize(){
        double currentWindowWidth = window.getWidth();
        double currentWindowHeight = window.getHeight();
        
        int currentWidth = jdialog.getWidth();
        int currentHeight = jdialog.getHeight();
        
        int expectedWidth = trh(window.getWidth());
        int expectedHeight = trv(window.getHeight());
        
        boolean mismatch = expectedWidth != currentWidth || expectedHeight != currentHeight;
        if(mismatch) {
            boolean minimizedOrMaximized = false;
            if(minimizedOrMaximized){
                window.setWidth(th(currentWidth));
                window.setHeight(tv(currentHeight));
                relayout();
            } else {
                boolean internalChange = currentWindowWidth != lastWindowWidth || currentWindowHeight != lastWindowHeight;
                boolean externalChange = currentWidth != lastWidth || currentHeight != lastHeight;
                if(ignoreExternalChange) externalChange = false;
                if(internalChange){
                    jdialog.setSize(expectedWidth, expectedHeight);
                    relayout();
                } else if(externalChange){
                    window.setWidth(th(currentWidth));
                    window.setHeight(tv(currentHeight));
                    relayout();
                } else {
                    jdialog.setSize(expectedWidth, expectedHeight);
                    relayout();
                }
            }
        }
        
        lastWindowWidth = window.getWidth();
        lastWindowHeight = window.getHeight();
        lastWidth = jdialog.getWidth();
        lastHeight = jdialog.getHeight();
    }
    
    
    private void synchronizeWindowOther(){
        iconSynchronization.updateValue();
        titleSynchronization.updateValue();
        cursorSynchronization.updateValue();
    }
    
    private final Synchronization iconSynchronization = new OneWaySynchronization<BitmapImage>() {
        @Override
        public BitmapImage getToolkitValue() {
            return window.getIcon();
        }

        @Override
        public void setImplValue(BitmapImage value) {
            if(value == null) jdialog.setIconImage(null);
            else jdialog.setIconImage(((SwingImplImage)value.getImplImage()).swingImage);
        }
    };
    
    private final Synchronization titleSynchronization = new OneWaySynchronization<String>() {
        @Override
        public String getToolkitValue() {
            if(window.getTitle() == null) return "";
            else return window.getTitle();
        }

        @Override
        public void setImplValue(String value) {
            jdialog.setTitle(value);
        }
    };
    
    private final Synchronization cursorSynchronization = new OneWaySynchronization<Cursor>() {
        @Override
        public Cursor getToolkitValue() {
            return api.MOUSE_INSTANCE.getCursor();
        }

        @Override
        public void setImplValue(Cursor value) {
            if(value == null) jdialog.setCursor(null);
            else jdialog.setCursor(((SwingImplCursor)value.getImplCursor()).swingCursor);
        }
    };
}
