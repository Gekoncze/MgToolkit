package cz.mg.toolkit.graphics;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.device.devices.Display;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


public class Graphics {
    public static final java.awt.Graphics GRAPHICS = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
    
    private final Display display;
    private final java.awt.Graphics2D g;
    private final ChainList<AffineTransform> transforms = new ChainList<>();
    private final ChainList<Shape> clips = new ChainList<>();
    private Color color;
    private Font font;

    public Graphics(Display display, java.awt.Graphics g) {
        this.display = display;
        this.g = (Graphics2D)g;
        color = new Color(g.getColor());
        font = new Font("default", 8, Font.Style.REGULAR);
    }
    
    public void pushTransform(){
        transforms.addLast(g.getTransform());
    }
    
    public void popTransform(){
        g.setTransform(transforms.removeLast());
    }
    
    public void pushClip(){
        clips.addLast(g.getClip());
    }
    
    public void popClip(){
        g.setClip(clips.removeLast());
    }
    
    public void translate(double x, double y){
        g.translate(th(x), tv(y));
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color c){
        this.color = c;
        g.setColor(c.getImplColor());
    }

    public Font getFont(){
        return font;
    }

    public void setFont(Font font){
        this.font = font;
    }

    public Rectangle getClipBounds(){
        return g.getClipBounds();
    }

    public void clipRectangle(double x, double y, double width, double height){
        g.clipRect(th(x), tv(y), th(width), tv(height));
    }

    public void setClip(double x, double y, double width, double height){
        g.setClip(th(x), tv(y), th(width), tv(height));
    }

    public void drawLine(double x1, double y1, double x2, double y2){
        g.drawLine(th(x1), tv(y1), th(x2), tv(y2));
    }

    public void fillRectangle(double x, double y, double width, double height){
        g.fillRect(th(x), tv(y), th(width), tv(height));
    }

    public void drawRectangle(double x, double y, double width, double height) {
        g.drawRect(th(x), tv(y), th(width), tv(height));
    }
    
    public void clearRectangle(double x, double y, double width, double height){
        g.clearRect(th(x), tv(y), th(width), tv(height));
    }

    public void drawRoundRectangle(double x, double y, double width, double height, double arcWidth, double arcHeight){
        g.drawRoundRect(th(x), tv(y), th(width), tv(height), th(arcWidth), tv(arcHeight));
    }

    public void fillRoundRectangle(double x, double y, double width, double height, double arcWidth, double arcHeight){
        g.fillRoundRect(th(x), tv(y), th(width), tv(height), th(arcWidth), tv(arcHeight));
    }

    public void drawOval(double x, double y, double width, double height){
        g.drawOval(th(x), tv(y), th(width), tv(height));
    }

    public void fillOval(double x, double y, double width, double height){
        g.fillOval(th(x), tv(y), th(width), tv(height));
    }

    public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle){
        g.drawArc(th(x), tv(y), th(width), tv(height), th(startAngle), tv(arcAngle));
    }

    public void fillArc(double x, double y, double width, double height, double startAngle, double arcAngle){
        g.fillArc(th(x), tv(y), th(width), tv(height), th(startAngle), tv(arcAngle));
    }

    public final void drawText(String text, double x, double y){
        drawImage(font.render(text, color), x, y, font.getWidth(text)-1, font.getHeight()-1);
    }

    public final boolean drawImage(Image img, double x, double y){
        return g.drawImage(img.getImplImage(), th(x), tv(y), null);
    }

    public final boolean drawImage(Image img, double x, double y, double width, double height){
        return g.drawImage(img.getImplImage(), th(x), tv(y), th(width), tv(height), null);
    }

    public final void setTransparency(double value){
        if(value < 0.0) value = 0.0;
        if(value > 1.0) value = 1.0;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) value));
    }

    public void rotate(double theta){
        g.rotate(theta);
    }

    public void rotate(double theta, double x, double y){
        g.rotate(theta, x, y);
    }

    public void scale(double sx, double sy){
        g.scale(sx, sy);
    }

    public void shear(double shx, double shy){
        g.shear(shx, shy);
    }

    public final void setAntialiasing(boolean value){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private int th(double value){
        return display.millimetersToPixelsH(value);
    }
    
    private int tv(double value){
        return display.millimetersToPixelsV(value);
    }
}
