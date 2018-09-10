package cz.mg.toolkit.graphics;

import cz.mg.toolkit.impl.ImplFont;
import cz.mg.toolkit.impl.swing.SwingImplFont;


public class Font {
    private final ImplFont implFont;
    
    public Font(String name, double height, Style style) {
        this.implFont = new SwingImplFont(name, height, style);
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
