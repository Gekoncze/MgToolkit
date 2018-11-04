package cz.mg.toolkit.impl;

import cz.mg.toolkit.component.window.Window;


public interface ImplWindow {
    public Window getWindow();
    public void setWindow(Window window);
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
    public void open();
    public void close();
    public void redraw();
}
