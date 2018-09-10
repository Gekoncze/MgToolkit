package cz.mg.toolkit.impl;

import cz.mg.toolkit.graphics.Font;


public interface ImplFont {
    public String getName();
    public Font.Style getStyle();
    public boolean canDisplay(char ch);
    public double getLeading();
    public double getAscent();
    public double getDescent();
    public double getWidth(String str);
    public double getHeight();
    public double getMaxAscent();
    public double getMaxDescent();
    public double getMaxAdvance();
    public boolean isUniform();
}
