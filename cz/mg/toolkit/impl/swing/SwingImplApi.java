package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplApi;
import cz.mg.toolkit.impl.ImplClipboard;
import cz.mg.toolkit.impl.ImplColor;
import cz.mg.toolkit.impl.ImplCursor;
import cz.mg.toolkit.impl.ImplFont;
import cz.mg.toolkit.impl.ImplImage;
import cz.mg.toolkit.impl.ImplTimer;
import cz.mg.toolkit.impl.ImplWindow;
import java.io.IOException;
import java.io.InputStream;


public class SwingImplApi implements ImplApi {
    public static final SwingImplDisplay SWING_DISPLAY_INSTANCE = new SwingImplDisplay();
    public static final Display DISPLAY_INSTANCE = new Display(SWING_DISPLAY_INSTANCE);
    public static final Mouse MOUSE_INSTANCE = new Mouse();
    public static final Keyboard KEYBOARD_INSTANCE = new Keyboard();
    
    @Override
    public ImplClipboard createClipboard() {
        return new SwingImplClipboard();
    }

    @Override
    public ImplColor createColor(int r, int g, int b, int a) {
        return new SwingImplColor(r, g, b, a);
    }

    @Override
    public ImplColor createColor(float r, float g, float b, float a) {
        return new SwingImplColor(r, g, b, a);
    }

    @Override
    public ImplCursor createCursor(ImplCursor.NativeCursor nativeCursor) {
        return new SwingImplCursor(nativeCursor);
    }

    @Override
    public ImplCursor createCursor(BitmapImage image, int dx, int dy) {
        return new SwingImplCursor(image, dx, dy);
    }

    @Override
    public ImplFont createFont(String name, double height, Font.Style style) {
        return new SwingImplFont(name, height, style);
    }

    @Override
    public ImplImage createImage(InputStream stream) throws IOException {
        return new SwingImplImage(stream);
    }

    @Override
    public ImplImage createImage(int horizontalResolution, int verticalResolution) {
        return new SwingImplImage(horizontalResolution, verticalResolution);
    }

    @Override
    public ImplTimer createTimer(int delay, EventObserver eventObserver) {
        return new SwingImplTimer(delay, eventObserver);
    }

    @Override
    public ImplWindow createWindow() {
        return new SwingImplWindow();
    }

    @Override
    public Display getPrimaryDisplay() {
        return DISPLAY_INSTANCE;
    }

    @Override
    public Keyboard getPrimaryKeyboard() {
        return KEYBOARD_INSTANCE;
    }

    @Override
    public Mouse getPrimaryMouse() {
        return MOUSE_INSTANCE;
    }
}
