package cz.mg.toolkit.graphics;

import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.ImplColor;


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
    
    private final ImplColor implColor;
    
    public Color(int r, int g, int b, int a){
        this.implColor = Impl.getImplApi().createColor(r, g, b, a);
    }
    
    public Color(float r, float g, float b, float a){
        this.implColor = Impl.getImplApi().createColor(r, g, b, a);
    }

    public ImplColor getImplColor() {
        return implColor;
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
}
