package cz.mg.toolkit.graphics;


public class Color {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255, 255);
    public static final Color LIGHT_GREY = new Color(192, 192, 192, 255);
    public static final Color GREY = new Color(128, 128, 128, 255);
    public static final Color DARK_GREY = new Color(64, 64, 64, 255);
    public static final Color BLACK = new Color(0, 0, 0, 255);
    public static final Color RED = new Color(255, 0, 0, 255);
    public static final Color YELLOW = new Color(255, 255, 0, 255);
    public static final Color GREEN = new Color(0, 255, 0, 255);
    public static final Color TURQUOISE = new Color(0, 255, 0, 255);
    public static final Color BLUE = new Color(0, 0, 255, 255);
    public static final Color HOT_PINK = new Color(0, 255, 0, 255);
    
    private java.awt.Color implColor;
    
    public Color(int r, int g, int b, int a){
        if(r < 0) r = 0;
        if(g < 0) g = 0;
        if(b < 0) b = 0;
        if(a < 0) a = 0;
        if(r > 255) r = 255;
        if(g > 255) g = 255;
        if(b > 255) b = 255;
        if(a > 255) a = 255;
        implColor = new java.awt.Color(r, g, b, a);
    }
    
    public Color(float r, float g, float b, float a){
        if(r < 0) r = 0;
        if(g < 0) g = 0;
        if(b < 0) b = 0;
        if(a < 0) a = 0;
        if(r > 1) r = 1;
        if(g > 1) g = 1;
        if(b > 1) b = 1;
        if(a > 1) a = 1;
        implColor = new java.awt.Color(r, g, b, a);
    }

    public Color(java.awt.Color implColor) {
        this.implColor = implColor;
    }
    
    public int getRed(){
        return implColor.getRed();
    }
    
    public int getGreen(){
        return implColor.getGreen();
    }
    
    public int getBlue(){
        return implColor.getBlue();
    }
    
    public int getAlpha(){
        return implColor.getAlpha();
    }
    
    public float getRedF(){
        return implColor.getRed() / 255.0f;
    }
    
    public float getGreenF(){
        return implColor.getGreen() / 255.0f;
    }
    
    public float getBlueF(){
        return implColor.getBlue() / 255.0f;
    }
    
    public float getAlphaF(){
        return implColor.getAlpha() / 255.0f;
    }
    
    public java.awt.Color getImplColor(){
        return implColor;
    }
}
