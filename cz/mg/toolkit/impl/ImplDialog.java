package cz.mg.toolkit.impl;

import cz.mg.toolkit.component.window.Window;


public interface ImplDialog {
    public Window getWindow();
    public void setWindow(Window window);
    public boolean isDecorated();
    public void setDecorated(boolean value);
    public double getLeftInsets();
    public double getRightInsets();
    public double getTopInsets();
    public double getBottomInsets();
    public boolean isResizable();
    public void setResizable(boolean value);
    public void open();
    public void close();
    public void redraw();
}
