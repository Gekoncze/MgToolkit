package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.graphics.*;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.ImplGraphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;


public class SwingImplGraphics implements ImplGraphics {
    public static final java.awt.Graphics GRAPHICS = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
    
    private final java.awt.Graphics2D g;
    private final ChainList<Shape> clips = new ChainList<>();
    private Color color;
    private Font font;
    private final Display display;
    private boolean debug = false;

    public SwingImplGraphics(Display display, java.awt.Graphics g) {
        this.display = display;
        this.g = (Graphics2D)g;
        setColor(Color.BLACK);
        setFont(new Font("default", 8, Font.Style.REGULAR));
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
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
    
    private void debugDetails(){
        System.out.println(" t[" + g.getTransform().getTranslateX() + ", " + g.getTransform().getTranslateY() + "]" + " c[" + g.getClip().getBounds().getX() + ", " + g.getClip().getBounds().getY() + ", " + g.getClip().getBounds().getWidth() + ", " + g.getClip().getBounds().getHeight() + "]");
    }

    @Override
    public final void clip(double x, double y, double width, double height){
        if(debug) System.out.print("SwingImplGraphics.clip(" + r(x) + ", " + r(y) + ", " + r(width) + ", " + r(height) + ")");
        if(debug) debugDetails();
        g.clipRect(r(x), r(y), r(width), r(height));
    }

    @Override
    public final void drawLine(double x1, double y1, double x2, double y2){
        if(debug) System.out.print("SwingImplGraphics.(" + r(x1) + ", " + r(y1) + ", " + r(x2) + ", " + r(y2) + ")");
        if(debug) debugDetails();
        g.drawLine(r(x1), r(y1), r(x2), r(y2));
    }
    
    @Override
    public final void drawLine(double x1, double y1, double x2, double y2, int x1c, int y1c, int x2c, int y2c){
        if(debug) System.out.print("SwingImplGraphics.(" + r(x1) + ", " + r(y1) + ", " + r(x2) + ", " + r(y2) + ", " + x1c + ", " + y1c + ", " + x2c + ", " + y2c + ")");
        if(debug) debugDetails();
        g.drawLine(r(x1) + x1c, r(y1) + y1c, r(x2) + x2c, r(y2) + y2c);
    }

    @Override
    public final void drawRectangle(double x, double y, double width, double height){
        if(debug) System.out.print("SwingImplGraphics.fillRectangle(" + r(x) + ", " + r(y) + ", " + (r(width)-1) + ", " + (r(height)-1) + ")");
        if(debug) debugDetails();
        g.fillRect(r(x), r(y), r(width)-1, r(height)-1);
    }

    @Override
    public final void drawRectangleBorder(double x, double y, double width, double height) {
        if(debug) System.out.print("SwingImplGraphics.drawRectangle(" + r(x) + ", " + r(y) + ", " + (r(width)-1) + ", " + (r(height)-1) + ")");
        if(debug) debugDetails();
        g.drawRect(r(x), r(y), r(width)-1, r(height)-1);
    }

    @Override
    public final void drawOvalBorder(double x, double y, double width, double height){
        if(debug) System.out.print("SwingImplGraphics.drawOval(" + r(x) + ", " + r(y) + ", " + (r(width)-1) + ", " + (r(height)-1) + ")");
        if(debug) debugDetails();
        g.drawOval(r(x), r(y), r(width)-1, r(height)-1);
    }

    @Override
    public final void drawOval(double x, double y, double width, double height){
        if(debug) System.out.print("SwingImplGraphics.fillOval(" + r(x) + ", " + r(y) + ", " + (r(width)-1) + ", " + (r(height)-1) + ")");
        if(debug) debugDetails();
        g.fillOval(r(x), r(y), r(width)-1, r(height)-1);
    }

    @Override
    public final void drawText(String text, double x, double y){
        if(debug) System.out.print("SwingImplGraphics.drawString(" + r(x) + ", " + r(y) + ")");
        if(debug) debugDetails();
        drawImage(renderFont(font, text, color), x, y, font.getDisplayWidth(display, text), font.getDisplayHeight(display));
    }

    @Override
    public final boolean drawImage(BitmapImage img, double x, double y, double width, double height){
        if(debug) System.out.print("SwingImplGraphics.drawImage(" + r(x) + ", " + r(y) + ", " + (r(width)-1) + ", " + (r(height)-1) + ")");
        if(debug) debugDetails();
        return g.drawImage(((SwingImplImage)img.getImplImage()).swingImage, r(x), r(y), r(width)-1, r(height)-1, null);
    }

    @Override
    public final void setTransparency(double value){
        if(value < 0.0) value = 0.0;
        if(value > 1.0) value = 1.0;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) value));
    }

    @Override
    public final void setAntialiasing(boolean value){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private BitmapImage renderFont(Font font, String text, Color color){
        SwingImplFont implFont = (SwingImplFont) font.getImplFont();
        implFont.updateResolution();
        int w = implFont.swingFontMetrics.stringWidth(text);
        int h = implFont.swingFontMetrics.getHeight();
        BitmapImage image = new BitmapImage(w, h);
        java.awt.image.BufferedImage implImage = ((SwingImplImage)image.getImplImage()).swingImage;
        java.awt.Graphics2D g = (java.awt.Graphics2D) implImage.getGraphics();
        g.setFont(implFont.swingFont);
        g.setColor(((SwingImplColor)color.getImplColor()).swingColor);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(text, 0, implFont.swingFontMetrics.getAscent());
        return image;
    }
    
    private int r(double value){
        return (int) Math.round(value);
    }
}
