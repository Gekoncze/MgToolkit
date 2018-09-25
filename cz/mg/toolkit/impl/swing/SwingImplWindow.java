package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.device.devices.Mouse;
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
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplCursor;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import cz.mg.toolkit.impl.ImplWindow;
import static cz.mg.toolkit.impl.swing.SwingImplApi.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import javax.swing.Timer;


public class SwingImplWindow implements EventObserver, ImplWindow {
    private JFrame jframe;
    private Image icon;
    private Window window;
    private Cursor cursor;
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

    public SwingImplWindow() {
        initKeyboardButtons();
        initMouseButtons();
        initNativeComponents();
    }
    
    private void initNativeComponents(){
        jframe = new JFrame(){
            @Override
            public void paint(java.awt.Graphics g) {
                synchronizeWindow();
                if(SWING_DISPLAY_INSTANCE.updateGraphicsBuffer()) sendEvent(new DisplayResolutionEvent());
                window.sendEvent(new BeforeDrawEvent(relayout));
                relayout = false;
                if(!window.isRelayoutNeeded()) sendEvent(addGraphicsContext(new DrawEvent()));
                g.drawImage(SWING_DISPLAY_INSTANCE.getGraphicsBuffer(), -getX(), -getY(), jframe);
            }
        };
        
        jframe.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jframe.getContentPane().setLayout(new java.awt.GridLayout());
        
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
                boolean change = MOUSE_INSTANCE.isButtonPressed(e.getButton()) == false;
                MOUSE_INSTANCE.pressButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(MOUSE_INSTANCE, e.getButton(), change, false)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                boolean change = MOUSE_INSTANCE.isButtonReleased(e.getButton()) == false;
                MOUSE_INSTANCE.releaseButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(MOUSE_INSTANCE, e.getButton(), false, change)));
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
                double dx = mx - MOUSE_INSTANCE.getScreenX();
                double dy = my - MOUSE_INSTANCE.getScreenY();
                MOUSE_INSTANCE.setScreenX(mx);
                MOUSE_INSTANCE.setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(MOUSE_INSTANCE, dx, dy)));
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                ignoreExternalChange = false;
                double mx = th(e.getXOnScreen());
                double my = tv(e.getYOnScreen());
                double dx = mx - MOUSE_INSTANCE.getScreenX();
                double dy = my - MOUSE_INSTANCE.getScreenY();
                MOUSE_INSTANCE.setScreenX(mx);
                MOUSE_INSTANCE.setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(MOUSE_INSTANCE, dx, dy)));
            }
        });
        
        jframe.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                MouseWheelEvent.Direction direction = e.getWheelRotation() > 0 ? MouseWheelEvent.Direction.DOWN : MouseWheelEvent.Direction.UP;
                sendEvent(addInputContext(new MouseWheelEvent(MOUSE_INSTANCE, direction)));
            }
        });
        
        jframe.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int button = codeLocationToButton(e.getKeyCode(), e.getKeyLocation());
                char ch = getAndSanitizeCharacter(e);
                boolean repeated = KEYBOARD_INSTANCE.isButtonPressed(button);
                boolean change = KEYBOARD_INSTANCE.isButtonPressed(button) == false;
                KEYBOARD_INSTANCE.pressButton(button);
                KEYBOARD_INSTANCE.pressCharacter(ch);
                Event event = new KeyboardButtonEvent(KEYBOARD_INSTANCE, button, ch, false, change, false);
                if(!repeated) sendEvent(addInputContext(event));
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                int button = codeLocationToButton(e.getKeyCode(), e.getKeyLocation());
                char ch = getAndSanitizeCharacter(e);
                boolean repeated = KEYBOARD_INSTANCE.isButtonReleased(button);
                boolean change = KEYBOARD_INSTANCE.isButtonReleased(button) == false;
                KEYBOARD_INSTANCE.releaseButton(button);
                KEYBOARD_INSTANCE.releaseCharacter(ch);
                Event event = new KeyboardButtonEvent(KEYBOARD_INSTANCE, button, ch, false, false, change);
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
                KEYBOARD_INSTANCE.reset();
                MOUSE_INSTANCE.reset();
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
        
        jframe.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                sendEvent(new WindowStateEvent(window));
            }
        });
        
        jframe.setFocusTraversalKeysEnabled(false);
        cursor = new Cursor(ImplCursor.NativeCursor.ARROW);
    }
    
    private void onWindowShown(){
        synchronizationTimer.start();
    }
    
    private void onWindowHidden(){
        synchronizationTimer.stop();
    }
    
    private double th(int value){
        return DISPLAY_INSTANCE.pixelsToMillimetersH(value);
    }
    
    private double tv(int value){
        return DISPLAY_INSTANCE.pixelsToMillimetersV(value);
    }
    
    private int trh(double value){
        return (int) Math.round(DISPLAY_INSTANCE.millimetersToPixelsH(value));
    }
    
    private int trv(double value){
        return (int) Math.round(DISPLAY_INSTANCE.millimetersToPixelsV(value));
    }
    
    private char getAndSanitizeCharacter(java.awt.event.KeyEvent e){
        if(e.getKeyChar() == 1 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'a';
        if(e.getKeyChar() == 2 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'b';
        if(e.getKeyChar() == 3 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'c';
        if(e.getKeyChar() == 4 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'd';
        if(e.getKeyChar() == 5 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'e';
        if(e.getKeyChar() == 6 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'f';
        if(e.getKeyChar() == 7 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'g';
        if(e.getKeyChar() == 8 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'h';
        if(e.getKeyChar() == 9 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'i';
        if(e.getKeyChar() == 10 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'j';
        if(e.getKeyChar() == 11 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'k';
        if(e.getKeyChar() == 12 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'l';
        if(e.getKeyChar() == 13 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'm';
        if(e.getKeyChar() == 14 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'n';
        if(e.getKeyChar() == 15 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'o';
        if(e.getKeyChar() == 16 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'p';
        if(e.getKeyChar() == 17 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'q';
        if(e.getKeyChar() == 18 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'r';
        if(e.getKeyChar() == 19 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 's';
        if(e.getKeyChar() == 20 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 't';
        if(e.getKeyChar() == 21 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'u';
        if(e.getKeyChar() == 22 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'v';
        if(e.getKeyChar() == 23 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'w';
        if(e.getKeyChar() == 24 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'x';
        if(e.getKeyChar() == 25 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'y';
        if(e.getKeyChar() == 26 && KEYBOARD_INSTANCE.isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'z';
        return e.getKeyChar();
    }

    @Override
    public final void sendEvent(Event e) {
        if(window != null) window.sendEvent(e);
    }
    
    private Event addGraphicsContext(Event event){
        event.setEventContext(new GraphicsEventContext(SWING_DISPLAY_INSTANCE.getGraphics(DISPLAY_INSTANCE)));
        return event;
    }
    
    private Event addInputContext(Event event){
        event.setEventContext(new InputEventContext(MOUSE_INSTANCE.getScreenX(), MOUSE_INSTANCE.getScreenY()));
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
    public final Image getIcon(){
        return icon;
    }
    
    @Override
    public final void setIcon(BitmapImage image){
        this.icon = image;
        if(jframe != null && image != null) jframe.setIconImage(((SwingImplImage)image.getImplImage()).swingImage);
    }
    
    @Override
    public final String getTitle(){
        return jframe.getTitle();
    }
    
    @Override
    public final void setTitle(String title){
        if(jframe != null) jframe.setTitle(title);
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
    public final void setActivated(boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean isResizable() {
        return jframe.isResizable();
    }

    @Override
    public final void setResizable(boolean value) {
        jframe.setResizable(value);
    }
    
    @Override
    public final Cursor getCursor(){
        return cursor;
    }
    
    @Override
    public final void setCursor(Cursor cursor){
        this.cursor = cursor;
        jframe.setCursor(((SwingImplCursor)cursor.getImplCursor()).swingCursor);
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
    
    private void repaint(){
        jframe.repaint();
    }
    
    private void synchronizeWindow(){
        synchronizeWindowPosition();
        synchronizeWindowSize();
    }
    
    private void synchronizeWindowPosition(){
        boolean minimizedOrMaximized = isMinimized() || isMaximized();
        
        double currentWindowX = window.getX();
        double currentWindowY = window.getY();
        boolean internalChange = currentWindowX != lastWindowX || currentWindowY != lastWindowY;
        
        int currentX = jframe.getX();
        int currentY = jframe.getY();
        boolean externalChange = currentX != lastX || currentY != lastY;
        if(ignoreExternalChange) externalChange = false;
        
        int expectedX = trh(window.getX());
        int expectedY = trv(window.getY());
        boolean mismatch = expectedX != currentX || expectedY != currentY;
        
        if(externalChange && minimizedOrMaximized){
            window.setX(th(currentX));
            window.setY(tv(currentY));
            repaint();
        } else if(internalChange){
            jframe.setLocation(expectedX, expectedY);
            repaint();
        } else if(externalChange){
            window.setX(th(currentX));
            window.setY(tv(currentY));
            repaint();
        } else if(mismatch){
            jframe.setLocation(expectedX, expectedY);
            repaint();
        }
        
        lastWindowX = window.getX();
        lastWindowY = window.getY();
        lastX = jframe.getX();
        lastY = jframe.getY();
    }
    
    private void synchronizeWindowSize(){
        boolean minimizedOrMaximized = isMinimized() || isMaximized();
        
        double currentWindowWidth = window.getWidth();
        double currentWindowHeight = window.getHeight();
        boolean internalChange = currentWindowWidth != lastWindowWidth || currentWindowHeight != lastWindowHeight;
        
        int currentWidth = jframe.getWidth();
        int currentHeight = jframe.getHeight();
        boolean externalChange = currentWidth != lastWidth || currentHeight != lastHeight;
        if(ignoreExternalChange) externalChange = false;
        
        int expectedWidth = trh(window.getWidth());
        int expectedHeight = trv(window.getHeight());
        boolean mismatch = expectedWidth != currentWidth || expectedHeight != currentHeight;
        
        if(externalChange && minimizedOrMaximized){
            window.setWidth(th(currentWidth));
            window.setHeight(tv(currentHeight));
            relayout();
        } else if(internalChange){
            jframe.setSize(expectedWidth, expectedHeight);
            relayout();
        } else if(externalChange){
            window.setWidth(th(currentWidth));
            window.setHeight(tv(currentHeight));
            relayout();
        } else if(mismatch){
            jframe.setSize(expectedWidth, expectedHeight);
            relayout();
        }
        lastWindowWidth = window.getWidth();
        lastWindowHeight = window.getHeight();
        lastWidth = jframe.getWidth();
        lastHeight = jframe.getHeight();
    }
    
    private static int codeLocationToButton(int code, int location){
        return code + locationToDeltaCode(location);
    }
    
    private static int locationToDeltaCode(int location){
        switch(location){
            case KeyEvent.KEY_LOCATION_LEFT: return 1000000;
            case KeyEvent.KEY_LOCATION_RIGHT: return 2000000;
            case KeyEvent.KEY_LOCATION_STANDARD: return 3000000;
            case KeyEvent.KEY_LOCATION_NUMPAD: return 4000000;
            case KeyEvent.KEY_LOCATION_UNKNOWN: return 5000000;
            default: throw new RuntimeException();
        }
    }
    
    private boolean initialisedKeyboard = false;
    private synchronized void initKeyboardButtons(){
        if(initialisedKeyboard) return;
        initialisedKeyboard = true;
        
        Keyboard.CTRL_LEFT_BUTTON = codeLocationToButton(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_LEFT);
        Keyboard.CTRL_RIGHT_BUTTON = codeLocationToButton(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_RIGHT);
        Keyboard.ALT_LEFT_BUTTON = codeLocationToButton(KeyEvent.VK_ALT, KeyEvent.KEY_LOCATION_LEFT);
        Keyboard.ALT_RIGHT_BUTTON = codeLocationToButton(KeyEvent.VK_ALT_GRAPH, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.SHIFT_LEFT_BUTTON = codeLocationToButton(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_LEFT);
        Keyboard.SHIFT_RIGHT_BUTTON = codeLocationToButton(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_RIGHT);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.CTRL_LEFT_BUTTON, "L-Ctrl");
        Keyboard.BUTTON_LABELS.put(Keyboard.CTRL_RIGHT_BUTTON, "R-Ctrl");
        Keyboard.BUTTON_LABELS.put(Keyboard.ALT_LEFT_BUTTON, "L-Alt");
        Keyboard.BUTTON_LABELS.put(Keyboard.ALT_RIGHT_BUTTON, "R-Alt");
        Keyboard.BUTTON_LABELS.put(Keyboard.SHIFT_LEFT_BUTTON, "L-Shift");
        Keyboard.BUTTON_LABELS.put(Keyboard.SHIFT_RIGHT_BUTTON, "R-Shift");

        Keyboard.ESC_BUTTON = codeLocationToButton(KeyEvent.VK_ESCAPE, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.ESC_BUTTON, "Esc");

        Keyboard.F1_BUTTON = codeLocationToButton(KeyEvent.VK_F1, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F2_BUTTON = codeLocationToButton(KeyEvent.VK_F2, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F3_BUTTON = codeLocationToButton(KeyEvent.VK_F3, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F4_BUTTON = codeLocationToButton(KeyEvent.VK_F4, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F5_BUTTON = codeLocationToButton(KeyEvent.VK_F5, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F6_BUTTON = codeLocationToButton(KeyEvent.VK_F6, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F7_BUTTON = codeLocationToButton(KeyEvent.VK_F7, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F8_BUTTON = codeLocationToButton(KeyEvent.VK_F8, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F9_BUTTON = codeLocationToButton(KeyEvent.VK_F9, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F10_BUTTON = codeLocationToButton(KeyEvent.VK_F10, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F11_BUTTON = codeLocationToButton(KeyEvent.VK_F11, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.F12_BUTTON = codeLocationToButton(KeyEvent.VK_F12, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.F1_BUTTON, "F1");
        Keyboard.BUTTON_LABELS.put(Keyboard.F2_BUTTON, "F2");
        Keyboard.BUTTON_LABELS.put(Keyboard.F3_BUTTON, "F3");
        Keyboard.BUTTON_LABELS.put(Keyboard.F4_BUTTON, "F4");
        Keyboard.BUTTON_LABELS.put(Keyboard.F5_BUTTON, "F5");
        Keyboard.BUTTON_LABELS.put(Keyboard.F6_BUTTON, "F6");
        Keyboard.BUTTON_LABELS.put(Keyboard.F7_BUTTON, "F7");
        Keyboard.BUTTON_LABELS.put(Keyboard.F8_BUTTON, "F8");
        Keyboard.BUTTON_LABELS.put(Keyboard.F9_BUTTON, "F9");
        Keyboard.BUTTON_LABELS.put(Keyboard.F10_BUTTON, "F10");
        Keyboard.BUTTON_LABELS.put(Keyboard.F11_BUTTON, "F11");
        Keyboard.BUTTON_LABELS.put(Keyboard.F12_BUTTON, "F12");

        Keyboard.PRINT_SCREEN_BUTTON = codeLocationToButton(KeyEvent.VK_PRINTSCREEN, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.PAUSE_BUTTON = codeLocationToButton(KeyEvent.VK_PAUSE, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.PRINT_SCREEN_BUTTON, "Print screen");
        Keyboard.BUTTON_LABELS.put(Keyboard.PAUSE_BUTTON, "Pause");

        Keyboard.INSERT_BUTTON = codeLocationToButton(KeyEvent.VK_INSERT, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.DELETE_BUTTON = codeLocationToButton(KeyEvent.VK_DELETE, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.HOME_BUTTON = codeLocationToButton(KeyEvent.VK_HOME, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.END_BUTTON = codeLocationToButton(KeyEvent.VK_END, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.PAGE_UP_BUTTON = codeLocationToButton(KeyEvent.VK_PAGE_UP, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.PAGE_DOWN_BUTTON = codeLocationToButton(KeyEvent.VK_PAGE_DOWN, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.INSERT_BUTTON, "Insert");
        Keyboard.BUTTON_LABELS.put(Keyboard.DELETE_BUTTON, "Delete");
        Keyboard.BUTTON_LABELS.put(Keyboard.HOME_BUTTON, "Home");
        Keyboard.BUTTON_LABELS.put(Keyboard.END_BUTTON, "End");
        Keyboard.BUTTON_LABELS.put(Keyboard.PAGE_UP_BUTTON, "Page up");
        Keyboard.BUTTON_LABELS.put(Keyboard.PAGE_DOWN_BUTTON, "Page down");

        Keyboard.CAPS_LOCK_BUTTON = codeLocationToButton(KeyEvent.VK_CAPS_LOCK, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.NUM_LOCK_BUTTON = codeLocationToButton(KeyEvent.VK_NUM_LOCK, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.SCROLL_LOCK_BUTTON = codeLocationToButton(KeyEvent.VK_SCROLL_LOCK, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.CAPS_LOCK_BUTTON, "Caps lock");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_LOCK_BUTTON, "Num lock");
        Keyboard.BUTTON_LABELS.put(Keyboard.SCROLL_LOCK_BUTTON, "Scroll lock");

        Keyboard.ENTER_BUTTON = codeLocationToButton(KeyEvent.VK_ENTER, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.BACKSPACE_BUTTON = codeLocationToButton(KeyEvent.VK_BACK_SPACE, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.TAB_BUTTON = codeLocationToButton(KeyEvent.VK_TAB, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.SPACE_BUTTON = codeLocationToButton(KeyEvent.VK_SPACE, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.ENTER_BUTTON, "Enter");
        Keyboard.BUTTON_LABELS.put(Keyboard.BACKSPACE_BUTTON, "Backspace");
        Keyboard.BUTTON_LABELS.put(Keyboard.TAB_BUTTON, "Tab");
        Keyboard.BUTTON_LABELS.put(Keyboard.SPACE_BUTTON, "Space");

        Keyboard.LEFT_BUTTON = codeLocationToButton(KeyEvent.VK_LEFT, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.RIGHT_BUTTON = codeLocationToButton(KeyEvent.VK_RIGHT, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.UP_BUTTON = codeLocationToButton(KeyEvent.VK_UP, KeyEvent.KEY_LOCATION_STANDARD);
        Keyboard.DOWN_BUTTON = codeLocationToButton(KeyEvent.VK_DOWN, KeyEvent.KEY_LOCATION_STANDARD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.LEFT_BUTTON, "Left");
        Keyboard.BUTTON_LABELS.put(Keyboard.RIGHT_BUTTON, "Right");
        Keyboard.BUTTON_LABELS.put(Keyboard.UP_BUTTON, "Up");
        Keyboard.BUTTON_LABELS.put(Keyboard.DOWN_BUTTON, "Down");

        Keyboard.NUM_0_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD0, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_1_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD1, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_2_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD2, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_3_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD3, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_4_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD4, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_5_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD5, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_6_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD6, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_7_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD7, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_8_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD8, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_9_BUTTON = codeLocationToButton(KeyEvent.VK_NUMPAD9, KeyEvent.KEY_LOCATION_NUMPAD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_0_BUTTON, "Num 0");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_1_BUTTON, "Num 1");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_2_BUTTON, "Num 2");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_3_BUTTON, "Num 3");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_4_BUTTON, "Num 4");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_5_BUTTON, "Num 5");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_6_BUTTON, "Num 6");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_7_BUTTON, "Num 7");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_8_BUTTON, "Num 8");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_9_BUTTON, "Num 9");
        
        Keyboard.NUM_ENTER_BUTTON = codeLocationToButton(KeyEvent.VK_ENTER, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_DIVIDE_BUTTON = codeLocationToButton(KeyEvent.VK_DIVIDE, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_MULTIPLY_BUTTON = codeLocationToButton(KeyEvent.VK_MULTIPLY, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_MINUS_BUTTON = codeLocationToButton(KeyEvent.VK_SUBTRACT, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_PLUS_BUTTON = codeLocationToButton(KeyEvent.VK_ADD, KeyEvent.KEY_LOCATION_NUMPAD);
        Keyboard.NUM_COMMA_BUTTON = codeLocationToButton(KeyEvent.VK_DECIMAL, KeyEvent.KEY_LOCATION_NUMPAD);
        
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_ENTER_BUTTON, "Num Enter");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_DIVIDE_BUTTON, "Num /");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_MULTIPLY_BUTTON, "Num *");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_MINUS_BUTTON, "Num -");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_PLUS_BUTTON, "Num +");
        Keyboard.BUTTON_LABELS.put(Keyboard.NUM_COMMA_BUTTON, "Num .");
    }
    
    private boolean initialisedMouse = false;
    private synchronized void initMouseButtons(){
        if(initialisedMouse) return;
        initialisedMouse = true;
        
        Mouse.LEFT_BUTTON = java.awt.event.MouseEvent.BUTTON1;
        Mouse.MIDDLE_BUTTON = java.awt.event.MouseEvent.BUTTON2;
        Mouse.RIGHT_BUTTON = java.awt.event.MouseEvent.BUTTON3;
    }
    
//    private void printAllActiveThreads(){
//        System.out.println("\n");
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        for(Thread t : threadSet) System.out.println("T: " + t);
//        System.out.println("\n");
//    }
//
//    private void printAllWindowState(){
//        System.out.println("\n");
//        for(java.awt.Window window : java.awt.Window.getWindows()){
//            System.out.println("" + window.isDisplayable());
//        }
//        System.out.println("\n");
//    }
}
