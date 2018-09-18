package cz.mg.toolkit.graphics;

import cz.mg.toolkit.graphics.images.VectorImage;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplGraphics;


public class Graphics {
    private final Display display;
    private final ImplGraphics implGraphics;

    public Graphics(Display display, ImplGraphics implGraphics) {
        this.display = display;
        this.implGraphics = implGraphics;
    }

    public Display getDisplay() {
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
        implGraphics.clip(th(x), tv(y), th(width), tv(height));
    }

    public final void drawLine(double x1, double y1, double x2, double y2){
        implGraphics.drawLine(th(x1), tv(y1), th(x2), tv(y2));
    }

    public final void fillRectangle(double x, double y, double width, double height){
        implGraphics.fillRectangle(th(x), tv(y), th(width), tv(height));
    }

    public final void drawRectangle(double x, double y, double width, double height) {
        implGraphics.drawRectangle(th(x), tv(y), th(width), tv(height));
    }

    public final void drawOval(double x, double y, double width, double height){
        implGraphics.drawOval(th(x), tv(y), th(width), tv(height));
    }

    public final void fillOval(double x, double y, double width, double height){
        implGraphics.fillOval(th(x), tv(y), th(width), tv(height));
    }

    public final void drawText(String text, double x, double y){
        implGraphics.drawText(text, th(x), tv(y));
    }
    
    public final void drawImage(Image image, double x, double y, double width, double height){
        if(image instanceof BitmapImage) drawImage((BitmapImage)image, x, y, width, height);
        if(image instanceof VectorImage) drawImage((VectorImage)image, x, y, width, height);
    }

    public final void drawImage(BitmapImage image, double x, double y, double width, double height){
        implGraphics.drawImage(image, th(x), tv(y), th(width), tv(height));
    }
    
    public final void drawImage(VectorImage image, double x, double y, double width, double height){
        pushTransform();
        translate(x, y);
        scale(width, height); // does not work - makes lines thicker!!!
        image.onDraw(this);
        popTransform();
    }

    public final void setTransparency(double value){
        implGraphics.setTransparency(value);
    }
    
    public final void translate(double x, double y){
        implGraphics.translate(th(x), tv(y));
    }

    public final void rotate(double theta){
        implGraphics.rotate(theta);
    }

    public final void rotate(double theta, double x, double y){
        implGraphics.rotate(theta, th(x), tv(y));
    }

    public final void scale(double sx, double sy){
        implGraphics.scale(sx, sy);
    }

    public final void shear(double shx, double shy){
        implGraphics.shear(shx, shy);
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
