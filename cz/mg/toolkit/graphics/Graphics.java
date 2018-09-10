package cz.mg.toolkit.graphics;

import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.impl.ImplGraphics;
import cz.mg.toolkit.impl.swing.SwingImplGraphics;


public class Graphics {
    private final ImplGraphics implGraphics;

    public Graphics(Display display, java.awt.Graphics g) {
        this.implGraphics = new SwingImplGraphics(display, g);
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
        implGraphics.clip(x, y, width, height);
    }

    public final void drawLine(double x1, double y1, double x2, double y2){
        implGraphics.drawLine(x1, y1, x2, y2);
    }

    public final void fillRectangle(double x, double y, double width, double height){
        implGraphics.fillRectangle(x, y, width, height);
    }

    public final void drawRectangle(double x, double y, double width, double height) {
        implGraphics.drawRectangle(x, y, width, height);
    }

    public final void drawOval(double x, double y, double width, double height){
        implGraphics.drawOval(x, y, width, height);
    }

    public final void fillOval(double x, double y, double width, double height){
        implGraphics.fillOval(x, y, width, height);
    }

    public final void drawText(String text, double x, double y){
        implGraphics.drawText(text, x, y);
    }

    public final void drawImage(Image img, double x, double y){
        implGraphics.drawImage(img, x, y);
    }

    public final void drawImage(Image img, double x, double y, double width, double height){
        implGraphics.drawImage(img, x, y, width, height);
    }

    public final void setTransparency(double value){
        implGraphics.setTransparency(value);
    }
    
    public final void translate(double x, double y){
        implGraphics.translate(x, y);
    }

    public final void rotate(double theta){
        implGraphics.rotate(theta);
    }

    public final void rotate(double theta, double x, double y){
        implGraphics.rotate(theta, x, y);
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
}
