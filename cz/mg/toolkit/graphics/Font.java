package cz.mg.toolkit.graphics;

import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.ImplFont;


public class Font {
    private ImplFont implFont;
    
    public Font(String name, double height, Style style) {
        this.implFont = Impl.getImplApi().createFont(name, height, style);
    }

    public ImplFont getImplFont() {
        return implFont;
    }
    
    public final String getName() {
        return implFont.getName();
    }
    
    public final Style getStyle() {
        return implFont.getStyle();
    }
    
    public final boolean canDisplay(char ch){
        return implFont.canDisplay(ch);
    }
    
    public final double getLeading() {
        return implFont.getLeading();
    }

    public final double getAscent() {
        return implFont.getAscent();
    }

    public final double getDescent() {
        return implFont.getDescent();
    }
    
    public final double getWidth(String s) {
        return implFont.getWidth(s);
    }

    public final double getHeight() {
        return implFont.getHeight();
    }
    
    public final void setHeight(double height) {
        this.implFont = Impl.getImplApi().createFont(getName(), height, getStyle());
    }
    
    public final double getDisplayWidth(Display display, String s) {
        return display.millimetersToPixelsH(implFont.getWidth(s));
    }

    public final double getDisplayHeight(Display display) {
        return display.millimetersToPixelsV(implFont.getHeight());
    }
    
    public final double getMaxAscent() {
        return implFont.getMaxAscent();
    }

    public final double getMaxDescent() {
        return implFont.getMaxDescent();
    }

    public final double getMaxAdvance() {
        return implFont.getMaxAdvance();
    }

    public final boolean isUniform() {
        return implFont.isUniform();
    }
    
    public static enum Style {
        REGULAR,
        BOLD,
        ITALIC,
        BOLD_AND_ITALIC;
    }
}
