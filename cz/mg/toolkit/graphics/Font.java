package cz.mg.toolkit.graphics;

import java.awt.RenderingHints;
import java.awt.Toolkit;


public class Font {
    private java.awt.Font implFontP;
    private java.awt.FontMetrics implFontMetricsP;
    private final String name;
    private final double height;
    private final Style style;

    public Font(String name, double height, Style style) {
        if(height <= 0.00001) height = 0.00001;
        this.name = name;
        this.style = style;
        this.height = height;
        implFontP = new java.awt.Font(name, style.toFlags(), 10);
        implFontMetricsP = Graphics.GRAPHICS.getFontMetrics(implFontP);
    }
    
    public final String getName() {
        return name;
    }
    
    public final Style getStyle() {
        return style;
    }
    
    public final boolean canDisplay(char ch){
        return implFontP.canDisplay(ch);
    }
    
    public final double getLeading() {
        return implFontMetricsP.getLeading() * computeHeightMultiplier();
    }

    public final double getAscent() {
        return implFontMetricsP.getAscent() * computeHeightMultiplier();
    }

    public final double getDescent() {
        return implFontMetricsP.getDescent() * computeHeightMultiplier();
    }
    
    public final double getWidth(String str) {
        return implFontMetricsP.stringWidth(str) * computeHeightMultiplier();
    }

    public final double getHeight() {
        return height;
    }
    
    public final double getMaxAscent() {
        return implFontMetricsP.getMaxAscent() * computeHeightMultiplier();
    }

    public final double getMaxDescent() {
        return implFontMetricsP.getMaxDescent();
    }

    public final double getMaxAdvance() {
        return implFontMetricsP.getMaxAdvance() * computeHeightMultiplier();
    }

    public final boolean isUniform() {
        return implFontP.hasUniformLineMetrics();
    }
    
    private double computeHeightMultiplier(){
        return height / implFontMetricsP.getHeight();
    }
    
    private void updateResolution(){
        while(computeHeightMultiplier() < 0.9){
            implFontP = new java.awt.Font(name, style.toFlags(), implFontP.getSize() - 1);
            implFontMetricsP = Graphics.GRAPHICS.getFontMetrics(implFontP);
        }
        
        while(computeHeightMultiplier() > 1.1){
            implFontP = new java.awt.Font(name, style.toFlags(), implFontP.getSize() + 1);
            implFontMetricsP = Graphics.GRAPHICS.getFontMetrics(implFontP);
        }
    }
    
    private static boolean equalsD(double a, double b){
        return Math.abs(a - b) < 0.0001;
    }
    
    public Image render(String text, Color color){
        updateResolution();
        int w = implFontMetricsP.stringWidth(text);
        int h = implFontMetricsP.getHeight();
        Image image = new Image(w, h, Image.ColorModel.RGBA);
        java.awt.image.BufferedImage implImage = image.getImplImage();
        java.awt.Graphics2D g = (java.awt.Graphics2D) implImage.getGraphics();
        g.setFont(implFontP);
        g.setColor(color.getImplColor());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(text, 0, implFontMetricsP.getAscent());
        return image;
    }
    
    public static enum Style {
        REGULAR,
        BOLD,
        ITALIC,
        BOLD_AND_ITALIC;
        
        public int toFlags(){
            switch(this){
                case REGULAR: return 0;
                case BOLD: return java.awt.Font.BOLD;
                case ITALIC: return java.awt.Font.ITALIC;
                case BOLD_AND_ITALIC: return java.awt.Font.BOLD | java.awt.Font.ITALIC;
                default: throw new RuntimeException();
            }
        }
        
        public static Style fromFlags(int flags){
            switch(flags){
                case 0: return REGULAR;
                case java.awt.Font.BOLD: return BOLD;
                case java.awt.Font.ITALIC: return ITALIC;
                case (java.awt.Font.BOLD | java.awt.Font.ITALIC): return BOLD_AND_ITALIC;
                default: throw new RuntimeException();
            }
        }
    }
}
