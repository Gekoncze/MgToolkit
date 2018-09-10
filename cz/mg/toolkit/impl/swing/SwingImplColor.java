package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.impl.ImplColor;


public class SwingImplColor implements ImplColor {
    final java.awt.Color swingColor;
    
    public SwingImplColor(int r, int g, int b, int a){
        if(r < 0) r = 0;
        if(g < 0) g = 0;
        if(b < 0) b = 0;
        if(a < 0) a = 0;
        if(r > 255) r = 255;
        if(g > 255) g = 255;
        if(b > 255) b = 255;
        if(a > 255) a = 255;
        swingColor = new java.awt.Color(r, g, b, a);
    }
    
    public SwingImplColor(float r, float g, float b, float a){
        if(r < 0) r = 0;
        if(g < 0) g = 0;
        if(b < 0) b = 0;
        if(a < 0) a = 0;
        if(r > 1) r = 1;
        if(g > 1) g = 1;
        if(b > 1) b = 1;
        if(a > 1) a = 1;
        swingColor = new java.awt.Color(r, g, b, a);
    }

    public SwingImplColor(java.awt.Color implColor) {
        this.swingColor = implColor;
    }
    
    @Override
    public int getRed(){
        return swingColor.getRed();
    }
    
    @Override
    public int getGreen(){
        return swingColor.getGreen();
    }
    
    @Override
    public int getBlue(){
        return swingColor.getBlue();
    }
    
    @Override
    public int getAlpha(){
        return swingColor.getAlpha();
    }
}
