package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.impl.ImplDisplay;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;


public class SwingImplDisplay implements ImplDisplay {
    private BufferedImage graphicsBuffer = new BufferedImage(getHorizontalResolution(), getVerticalResolution(), BufferedImage.TYPE_INT_RGB);

    public SwingImplDisplay() {
    }
    
    @Override
    public int getHorizontalResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    @Override
    public int getVerticalResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    
    public final boolean updateGraphicsBuffer(){
        int w = getHorizontalResolution();
        int h = getVerticalResolution();
        if(w < 1) w = 1;
        if(h < 1) h = 1;
        if(graphicsBuffer.getWidth() != w || graphicsBuffer.getHeight() != h){
            graphicsBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            return true;
        }
        return false;
    }
    
    public final Graphics getGraphics(Display display) {
        return new Graphics(display, new SwingImplGraphics(display, graphicsBuffer.getGraphics()));
    }

    public final BufferedImage getGraphicsBuffer() {
        return graphicsBuffer;
    }
}
