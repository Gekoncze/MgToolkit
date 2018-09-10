package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.graphics.*;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.impl.ImplGraphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


public class SwingImplGraphics implements ImplGraphics {
    public static final java.awt.Graphics GRAPHICS = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
    
    private final Display display;
    private final java.awt.Graphics2D g;
    private final ChainList<AffineTransform> transforms = new ChainList<>();
    private final ChainList<Shape> clips = new ChainList<>();
    private Color color;
    private Font font;

    public SwingImplGraphics(Display display, java.awt.Graphics g) {
        this.display = display;
        this.g = (Graphics2D)g;
        setColor(Color.BLACK);
        setFont(new Font("default", 8, Font.Style.REGULAR));
    }
    
    @Override
    public final void pushTransform(){
        transforms.addLast(g.getTransform());
    }
    
    @Override
    public final void popTransform(){
        g.setTransform(transforms.removeLast());
    }
    
    @Override
    public final void pushClip(){
        clips.addLast(g.getClip());
    }
    
    @Override
    public final void popClip(){
        g.setClip(clips.removeLast());
    }

    @Override
    public final Color getColor(){
        return color;
    }

    @Override
    public final void setColor(Color color){
        this.color = color;
        g.setColor(((SwingImplColor)color.getImplColor()).swingColor);
    }

    @Override
    public final Font getFont(){
        return font;
    }

    @Override
    public final void setFont(Font font){
        this.font = font;
    }

    @Override
    public final void clip(double x, double y, double width, double height){
        g.clipRect(th(x), tv(y), th(width), tv(height));
    }

    @Override
    public final void drawLine(double x1, double y1, double x2, double y2){
        g.drawLine(th(x1), tv(y1), th(x2), tv(y2));
    }

    @Override
    public final void fillRectangle(double x, double y, double width, double height){
        g.fillRect(th(x), tv(y), th(width), tv(height));
    }

    @Override
    public final void drawRectangle(double x, double y, double width, double height) {
        g.drawRect(th(x), tv(y), th(width), tv(height));
    }

    @Override
    public final void drawOval(double x, double y, double width, double height){
        g.drawOval(th(x), tv(y), th(width), tv(height));
    }

    @Override
    public final void fillOval(double x, double y, double width, double height){
        g.fillOval(th(x), tv(y), th(width), tv(height));
    }

    @Override
    public final void drawText(String text, double x, double y){
        drawImage(render(font, text, color), x, y, font.getWidth(text)-1, font.getHeight()-1);
    }

    @Override
    public final boolean drawImage(Image img, double x, double y){
        return g.drawImage(((SwingImplImage)img.getImplImage()).swingImage, th(x), tv(y), null);
    }

    @Override
    public final boolean drawImage(Image img, double x, double y, double width, double height){
        return g.drawImage(((SwingImplImage)img.getImplImage()).swingImage, th(x), tv(y), th(width), tv(height), null);
    }

    @Override
    public final void setTransparency(double value){
        if(value < 0.0) value = 0.0;
        if(value > 1.0) value = 1.0;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) value));
    }
    
    @Override
    public final void translate(double x, double y){
        g.translate(th(x), tv(y));
    }

    @Override
    public final void rotate(double theta){
        g.rotate(theta);
    }

    @Override
    public final void rotate(double theta, double x, double y){
        g.rotate(theta, x, y);
    }

    @Override
    public final void scale(double sx, double sy){
        g.scale(sx, sy);
    }

    @Override
    public final void shear(double shx, double shy){
        g.shear(shx, shy);
    }

    @Override
    public final void setAntialiasing(boolean value){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private int th(double value){
        return display.millimetersToPixelsH(value);
    }
    
    private int tv(double value){
        return display.millimetersToPixelsV(value);
    }
    
    private Image render(Font font, String text, Color color){
        SwingImplFont implFont = (SwingImplFont) font.getImplFont();
        implFont.updateResolution();
        int w = implFont.swingFontMetrics.stringWidth(text);
        int h = implFont.swingFontMetrics.getHeight();
        Image image = new Image(w, h);
        java.awt.image.BufferedImage implImage = ((SwingImplImage)image.getImplImage()).swingImage;
        java.awt.Graphics2D g = (java.awt.Graphics2D) implImage.getGraphics();
        g.setFont(implFont.swingFont);
        g.setColor(((SwingImplColor)color.getImplColor()).swingColor);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(text, 0, implFont.swingFontMetrics.getAscent());
        return image;
    }
}
