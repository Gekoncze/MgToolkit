package cz.mg.toolkit.impl;

import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.graphics.images.BitmapImage;


public interface ImplWindow {
    public Window getWindow();
    public void setWindow(Window window);
    public double getX(); 
    public double getY();
    public void setLocation(double x, double y);
    public double getWidth();
    public double getHeight();
    public void setSize(double width, double height);
    public double getContentWidth();
    public double getContentHeight();
    public void setContentWidth(double width);
    public void setContentHeight(double height);
    public Image getIcon();
    public void setIcon(BitmapImage image);
    public String getTitle();
    public void setTitle(String title);
    public boolean isDecorated();
    public void setDecorated(boolean value);
    public double getLeftInsets();
    public double getRightInsets();
    public double getTopInsets();
    public double getBottomInsets();
    public boolean isMinimized();
    public void setMinimized(boolean value);
    public boolean isMaximized();
    public void setMaximized(boolean value);
    public boolean isActivated();
    public void setActivated(boolean value);
    public boolean isResizable();
    public void setResizable(boolean value);
    public Cursor getCursor();
    public void setCursor(Cursor cursor);
    public void open();
    public void close();
    public void center();
    public void redraw();
}
