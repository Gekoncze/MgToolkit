package cz.mg.toolkit.impl;

import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.environment.device.devices.Mouse;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.images.BitmapImage;
import java.io.IOException;
import java.io.InputStream;


public interface ImplApi {
    public ImplClipboard createClipboard();
    public ImplColor createColor(int r, int g, int b, int a);
    public ImplColor createColor(float r, float g, float b, float a);
    public ImplCursor createCursor(ImplCursor.NativeCursor nativeCursor);
    public ImplCursor createCursor(BitmapImage image, int dx, int dy);
    public ImplFont createFont(String name, double height, Font.Style style);
    public ImplImage createImage(InputStream stream) throws IOException;
    public ImplImage createImage(int horizontalResolution, int verticalResolution);
    public ImplTimer createTimer(int delay, EventObserver eventObserver);
    public ImplWindow createWindow();
    public Display getPrimaryDisplay();
    public Keyboard getPrimaryKeyboard();
    public Mouse getPrimaryMouse();
}
