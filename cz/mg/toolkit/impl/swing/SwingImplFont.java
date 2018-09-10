package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.impl.ImplFont;
import static cz.mg.toolkit.impl.swing.SwingImplGraphics.GRAPHICS;
import cz.mg.toolkit.graphics.Font.Style;
import static cz.mg.toolkit.graphics.Font.Style.*;


public class SwingImplFont implements ImplFont {
    java.awt.Font swingFont;
    java.awt.FontMetrics swingFontMetrics;
    private final String name;
    private final double height;
    private final Style style;

    public SwingImplFont(String name, double height, Style style) {
        if(height <= 0.00001) height = 0.00001;
        this.name = name;
        this.style = style;
        this.height = height;
        swingFont = new java.awt.Font(name, styleToFlags(style), 10);
        swingFontMetrics = SwingImplGraphics.GRAPHICS.getFontMetrics(swingFont);
    }
    
    @Override
    public final String getName() {
        return name;
    }
    
    @Override
    public final Style getStyle() {
        return style;
    }
    
    @Override
    public final boolean canDisplay(char ch){
        return swingFont.canDisplay(ch);
    }
    
    @Override
    public final double getLeading() {
        return swingFontMetrics.getLeading() * computeHeightMultiplier();
    }

    @Override
    public final double getAscent() {
        return swingFontMetrics.getAscent() * computeHeightMultiplier();
    }

    @Override
    public final double getDescent() {
        return swingFontMetrics.getDescent() * computeHeightMultiplier();
    }
    
    @Override
    public final double getWidth(String s) {
        return swingFontMetrics.stringWidth(s) * computeHeightMultiplier();
    }

    @Override
    public final double getHeight() {
        return height;
    }
    
    @Override
    public final double getMaxAscent() {
        return swingFontMetrics.getMaxAscent() * computeHeightMultiplier();
    }

    @Override
    public final double getMaxDescent() {
        return swingFontMetrics.getMaxDescent();
    }

    @Override
    public final double getMaxAdvance() {
        return swingFontMetrics.getMaxAdvance() * computeHeightMultiplier();
    }

    @Override
    public final boolean isUniform() {
        return swingFont.hasUniformLineMetrics();
    }
    
    private double computeHeightMultiplier(){
        return height / swingFontMetrics.getHeight();
    }
    
    void updateResolution(){
        while(computeHeightMultiplier() < 0.9){
            swingFont = new java.awt.Font(name, styleToFlags(style), swingFont.getSize() - 1);
            swingFontMetrics = GRAPHICS.getFontMetrics(swingFont);
        }
        while(computeHeightMultiplier() > 1.1){
            swingFont = new java.awt.Font(name, styleToFlags(style), swingFont.getSize() + 1);
            swingFontMetrics = GRAPHICS.getFontMetrics(swingFont);
        }
    }
    
    private static int styleToFlags(Style style){
        switch(style){
            case REGULAR: return 0;
            case BOLD: return java.awt.Font.BOLD;
            case ITALIC: return java.awt.Font.ITALIC;
            case BOLD_AND_ITALIC: return java.awt.Font.BOLD | java.awt.Font.ITALIC;
            default: throw new RuntimeException();
        }
    }
}
