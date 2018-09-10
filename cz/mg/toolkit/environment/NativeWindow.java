package cz.mg.toolkit.environment;

import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.device.devices.Display;
import cz.mg.toolkit.device.devices.Keyboard;
import cz.mg.toolkit.device.devices.Mouse;
import cz.mg.toolkit.event.contexts.GraphicsEventContext;
import cz.mg.toolkit.event.contexts.InputEventContext;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.DrawEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.DisplayResolutionEvent;
import cz.mg.toolkit.event.events.WindowCloseEvent;
import cz.mg.toolkit.event.events.WindowStateEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;


public class NativeWindow implements EventObserver {
    private JFrame jframe;
    private JPanel jpanel;
    
    private Image icon;
    private Window window;
    private boolean relayout = true;

    public NativeWindow() {
        initKeyboardButtons();
        initMouseButtons();
        initNativeComponents();
    }
    
    private void initNativeComponents(){
        jframe = new JFrame(){
            @Override
            public void paint(java.awt.Graphics g) {
                if(Display.getInstance().updateGraphicsBuffer()) sendEvent(new DisplayResolutionEvent());
                window.sendEvent(new BeforeDrawEvent(relayout));
                relayout = false;
                if(!window.isRelayoutNeeded()) sendEvent(addGraphicsContext(new DrawEvent()));
                g.drawImage(Display.getInstance().getGraphicsBuffer(), -getX(), -getY(), jframe);
            }
        };
        
        jframe.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jframe.getContentPane().setLayout(new java.awt.GridLayout());
        
        jframe.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                super.componentResized(e);
                relayout();
            }

            @Override
            public void componentMoved(java.awt.event.ComponentEvent e) {
                super.componentMoved(e);
                relayout();
            }
        });
        
        jframe.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                boolean change = Mouse.getInstance().isButtonPressed(e.getButton()) == false;
                Mouse.getInstance().pressButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(Mouse.getInstance(), e.getButton(), change, false)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                boolean change = Mouse.getInstance().isButtonReleased(e.getButton()) == false;
                Mouse.getInstance().releaseButton(e.getButton());
                sendEvent(addInputContext(new MouseButtonEvent(Mouse.getInstance(), e.getButton(), false, change)));
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
                double dx = mx - Mouse.getInstance().getScreenX();
                double dy = my - Mouse.getInstance().getScreenY();
                Mouse.getInstance().setScreenX(mx);
                Mouse.getInstance().setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(Mouse.getInstance(), dx, dy)));
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                double mx = th(e.getXOnScreen());
                double my = tv(e.getYOnScreen());
                double dx = mx - Mouse.getInstance().getScreenX();
                double dy = my - Mouse.getInstance().getScreenY();
                Mouse.getInstance().setScreenX(mx);
                Mouse.getInstance().setScreenY(my);
                sendEvent(addInputContext(new MouseMotionEvent(Mouse.getInstance(), dx, dy)));
            }
        });
        
        jframe.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                MouseWheelEvent.Direction direction = e.getWheelRotation() > 0 ? MouseWheelEvent.Direction.DOWN : MouseWheelEvent.Direction.UP;
                sendEvent(addInputContext(new MouseWheelEvent(Mouse.getInstance(), direction)));
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
                boolean repeated = Keyboard.getInstance().isButtonPressed(button);
                boolean change = Keyboard.getInstance().isButtonPressed(button) == false;
                Keyboard.getInstance().pressButton(button);
                Keyboard.getInstance().pressCharacter(ch);
                Event event = new KeyboardButtonEvent(Keyboard.getInstance(), button, ch, false, change, false);
                if(!repeated) sendEvent(addInputContext(event));
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                int button = codeLocationToButton(e.getKeyCode(), e.getKeyLocation());
                char ch = getAndSanitizeCharacter(e);
                boolean repeated = Keyboard.getInstance().isButtonReleased(button);
                boolean change = Keyboard.getInstance().isButtonReleased(button) == false;
                Keyboard.getInstance().releaseButton(button);
                Keyboard.getInstance().releaseCharacter(ch);
                Event event = new KeyboardButtonEvent(Keyboard.getInstance(), button, ch, false, false, change);
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
                Keyboard.getInstance().reset();
                Mouse.getInstance().reset();
                sendEvent(new WindowStateEvent(window));
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                sendEvent(new WindowStateEvent(window));
            }
        });
        
        jframe.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                sendEvent(new WindowStateEvent(window));
            }
        });
        
        jframe.setFocusTraversalKeysEnabled(false);
        
        jpanel = new JPanel();
        jpanel.setBackground(new Color(0,0,0,0));
        
        jpanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                super.componentResized(e);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        relayout();
                    }
                });
            }
        });
        
        jframe.getContentPane().add(jpanel);
    }
    
    private double th(int value){
        return Display.getInstance().pixelsToMillimetersH(value);
    }
    
    private double tv(int value){
        return Display.getInstance().pixelsToMillimetersV(value);
    }
    
    private int trh(double value){
        return Display.getInstance().millimetersToPixelsH(value);
    }
    
    private int trv(double value){
        return Display.getInstance().millimetersToPixelsV(value);
    }
    
    private char getAndSanitizeCharacter(java.awt.event.KeyEvent e){
        if(e.getKeyChar() == 1 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'a';
        if(e.getKeyChar() == 2 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'b';
        if(e.getKeyChar() == 3 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'c';
        if(e.getKeyChar() == 4 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'd';
        if(e.getKeyChar() == 5 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'e';
        if(e.getKeyChar() == 6 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'f';
        if(e.getKeyChar() == 7 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'g';
        if(e.getKeyChar() == 8 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'h';
        if(e.getKeyChar() == 9 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'i';
        if(e.getKeyChar() == 10 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'j';
        if(e.getKeyChar() == 11 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'k';
        if(e.getKeyChar() == 12 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'l';
        if(e.getKeyChar() == 13 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'm';
        if(e.getKeyChar() == 14 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'n';
        if(e.getKeyChar() == 15 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'o';
        if(e.getKeyChar() == 16 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'p';
        if(e.getKeyChar() == 17 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'q';
        if(e.getKeyChar() == 18 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'r';
        if(e.getKeyChar() == 19 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 's';
        if(e.getKeyChar() == 20 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 't';
        if(e.getKeyChar() == 21 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'u';
        if(e.getKeyChar() == 22 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'v';
        if(e.getKeyChar() == 23 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'w';
        if(e.getKeyChar() == 24 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'x';
        if(e.getKeyChar() == 25 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'y';
        if(e.getKeyChar() == 26 && Keyboard.getInstance().isButtonPressed(Keyboard.CTRL_LEFT_BUTTON)) return 'z';
        return e.getKeyChar();
    }

    @Override
    public void sendEvent(Event e) {
        if(window != null) window.sendEvent(e);
    }
    
    private Event addGraphicsContext(Event event){
        event.setEventContext(new GraphicsEventContext(Display.getInstance().getGraphics()));
        return event;
    }
    
    private Event addInputContext(Event event){
        event.setEventContext(new InputEventContext(Mouse.getInstance().getScreenX(), Mouse.getInstance().getScreenY()));
        return event;
    }

    public final Window getWindow() {
        return window;
    }

    public final void setWindow(Window window) {
        this.window = window;
    }
    
    public final double getX(){
        if((jframe.getExtendedState() & JFrame.MAXIMIZED_HORIZ) != 0) return 0;
        return th(jframe.getX());
    }
    
    public final double getY(){
        if((jframe.getExtendedState() & JFrame.MAXIMIZED_VERT) != 0) return 0;
        return tv(jframe.getY());
    }
    
    public final void setLocation(double x, double y){
        jframe.setLocation(trh(x), trv(y));
    }
    
    public final double getWidth(){
        return th(jframe.getWidth());
    }
    
    public final double getHeight(){
        return tv(jframe.getHeight());
    }
    
    public final double getContentWidth(){
        return th(jpanel.getWidth());
    }
    
    public final double getContentHeight(){
        return tv(jpanel.getHeight());
    }
    
    public final void setSize(double width, double height){
        jframe.setSize(trh(width), trv(height));
    }
    
    public final void setContentWidth(double width){
        jpanel.setPreferredSize(new Dimension(trh(width), jpanel.getHeight()));
        jframe.pack();
    }
    
    public final void setContentHeight(double height){
        jpanel.setPreferredSize(new Dimension(jpanel.getWidth(), trv(height)));
        jframe.pack();
    }
    
    public final Image getIcon(){
        return icon;
    }
    
    public final void setIcon(Image image){
        this.icon = image;
        if(jframe != null && image != null) jframe.setIconImage(image.getImplImage());
    }
    
    public final String getTitle(){
        return jframe.getTitle();
    }
    
    public final void setTitle(String title){
        if(jframe != null) jframe.setTitle(title);
    }
    
    public final boolean isDecorated(){
        return !jframe.isUndecorated();
    }
    
    public void setDecorated(boolean value){
        if(jframe.isUndecorated() == !value) return;
        boolean wasVisible = jframe.isVisible();
        if(wasVisible) jframe.dispose();
        jframe.setUndecorated(!value);
        if(wasVisible) jframe.setVisible(true);
    }
    
    public final double getLeftInsets(){
        return th(jframe.getInsets().left);
    }
    
    public final double getRightInsets(){
        return th(jframe.getInsets().right);
    }
    
    public final double getTopInsets(){
        return tv(jframe.getInsets().top);
    }
    
    public final double getBottomInsets(){
        return tv(jframe.getInsets().bottom);
    }
    
    public final void closeImmediately(){
        jframe.dispose();
    }
    
    public final boolean isMinimized() {
        return (jframe.getExtendedState() & Frame.ICONIFIED) != 0;
    }

    public final void setMinimized(boolean value) {
        if(value){
            jframe.setExtendedState(jframe.getExtendedState() | Frame.ICONIFIED);
        } else {
            jframe.setExtendedState(jframe.getExtendedState() & (~Frame.ICONIFIED));
        }
    }

    public final boolean isMaximized() {
        return (jframe.getExtendedState() & Frame.MAXIMIZED_BOTH) != 0;
    }
    
    public final void setMaximized(boolean value) {
        if(value){
            jframe.setExtendedState(jframe.getExtendedState() | Frame.MAXIMIZED_BOTH);
        } else {
            jframe.setExtendedState(jframe.getExtendedState() & (~Frame.MAXIMIZED_BOTH));
        }
    }

    public final boolean isActivated() {
        return jframe.isActive();
    }

    public final void setActivated(boolean value) {
        throw new UnsupportedOperationException();
    }

    public final boolean isResizable() {
        return jframe.isResizable();
    }

    public final void setResizable(boolean value) {
        jframe.setResizable(value);
    }
    
    public void minimize(){
        jframe.setState(Frame.ICONIFIED);
    }
    
    public void maximize(){
        if((jframe.getExtendedState() & JFrame.MAXIMIZED_BOTH) > 0){
            jframe.setExtendedState(jframe.getExtendedState() & (~JFrame.MAXIMIZED_BOTH));
        } else {
            jframe.setExtendedState(jframe.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
    }
    
    public void show(){
        jframe.setVisible(true);
    }
    
    public void center(){
        jframe.setLocationRelativeTo(null);
    }
    
    public void redraw(){
        jframe.repaint();
    }
    
    public void relayout(){
        relayout = true;
        jframe.repaint();
    }
    
    public void setCursor(Cursor cursor){
        jframe.setCursor(cursor.getImplCursor());
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
}
