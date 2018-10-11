package cz.mg.toolkit.graphics;

import cz.mg.toolkit.graphics.images.VectorImage;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplGraphics;


public class Graphics {
    private final Display display;
    private final ImplGraphics implGraphics;
    private boolean debug = false;

    public Graphics(Display display, ImplGraphics implGraphics) {
        this.display = display;
        this.implGraphics = implGraphics;
    }

    public final boolean isDebug() {
        return debug;
    }

    public final void setDebug(boolean debug) {
        this.debug = debug;
        implGraphics.setDebug(debug);
    }

    public final Display getDisplay() {
        return display;
    }
    
    public final void pushTransform(){
        implGraphics.pushTransform();
    }
    
    public final void popTransform(){
        implGraphics.popTransform();
    }
    
    public final void pushClip(){
        implGraphics.pushClip();
    }
    
    public final void popClip(){
        implGraphics.popClip();
    }

    public final Color getColor(){
        return implGraphics.getColor();
    }

    public final void setColor(Color color){
        implGraphics.setColor(color);
    }

    public final Font getFont(){
        return implGraphics.getFont();
    }

    public final void setFont(Font font){
        implGraphics.setFont(font);
    }

    public final void clip(double x, double y, double width, double height){
        if(debug) System.out.println("Graphics.clip(" + x + ", " + y + ", " + width + ", " + height + ")");
        implGraphics.clip(th(x), tv(y), th(width), tv(height));
    }

    public final void drawLine(double x1, double y1, double x2, double y2){
        if(debug) System.out.println("Graphics.drawLine(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")");
        implGraphics.drawLine(th(x1), tv(y1), th(x2), tv(y2));
    }

    public final void drawRectangle(double x, double y, double width, double height){
        if(debug) System.out.println("Graphics.fillRectangle(" + x + ", " + y + ", " + width + ", " + height + ")");
        implGraphics.drawRectangle(th(x), tv(y), th(width), tv(height));
    }

    public final void drawRectangleBorder(double x, double y, double width, double height) {
        if(debug) System.out.println("Graphics.drawRectangle(" + x + ", " + y + ", " + width + ", " + height + ")");
        implGraphics.drawRectangleBorder(th(x), tv(y), th(width), tv(height));
    }

    public final void drawOval(double x, double y, double width, double height){
        if(debug) System.out.println("Graphics.fillOval(" + x + ", " + y + ", " + width + ", " + height + ")");
        implGraphics.drawOval(th(x), tv(y), th(width), tv(height));
    }
    
    public final void drawOvalBorder(double x, double y, double width, double height){
        if(debug) System.out.println("Graphics.drawOval(" + x + ", " + y + ", " + width + ", " + height + ")");
        implGraphics.drawOvalBorder(th(x), tv(y), th(width), tv(height));
    }

    public final void drawText(String text, double x, double y){
        if(debug) System.out.println("Graphics.drawText(" + x + ", " + y + ")");
        implGraphics.drawText(text, th(x), tv(y));
    }
    
    public final void drawImage(Image image, double x, double y, double width, double height){
        if(debug) System.out.println("Graphics.drawImage(" + x + ", " + y + ", " + width + ", " + height + ")");
        if(image instanceof BitmapImage) drawImage((BitmapImage)image, x, y, width, height);
        if(image instanceof VectorImage) drawImage((VectorImage)image, x, y, width, height);
    }

    public final void drawImage(BitmapImage image, double x, double y, double width, double height){
        implGraphics.drawImage(image, th(x), tv(y), th(width), tv(height));
    }
    
    public final void drawImage(VectorImage image, double x, double y, double width, double height){
        pushTransform();
        translate(x, y);
        image.onDraw(this, width, height);
        popTransform();
    }

    public final void setTransparency(double value){
        implGraphics.setTransparency(value);
    }
    
    public final void translate(double x, double y){
        implGraphics.translate(th(x), tv(y));
    }

    public final void setAntialiasing(boolean value){
        implGraphics.setAntialiasing(value);
    }
    
    private double th(double value){
        return display.millimetersToPixelsH(value);
    }
    
    private double tv(double value){
        return display.millimetersToPixelsV(value);
    }
}
