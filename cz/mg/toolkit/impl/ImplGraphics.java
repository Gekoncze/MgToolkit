package cz.mg.toolkit.impl;

import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.images.BitmapImage;


public interface ImplGraphics {
    public boolean isDebug();
    public void setDebug(boolean debug);
    public void pushClip();
    public void popClip();
    public Color getColor();
    public void setColor(Color color);
    public Font getFont();
    public void setFont(Font font);
    public void clip(double x, double y, double width, double height);
    public void drawLine(double x1, double y1, double x2, double y2);
    public void drawRectangle(double x, double y, double width, double height);
    public void drawRectangleBorder(double x, double y, double width, double height);
    public void drawOval(double x, double y, double width, double height);
    public void drawOvalBorder(double x, double y, double width, double height);
    public void drawText(String text, double x, double y);
    public boolean drawImage(BitmapImage img, double x, double y, double width, double height);
    public void setTransparency(double value);
    public void setAntialiasing(boolean value);
}
