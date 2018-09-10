package cz.mg.toolkit.environment.device.devices;

import cz.mg.toolkit.environment.device.Device;
import cz.mg.toolkit.graphics.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;


public class Display extends Device {
    private static int nextId = 0;
    
    private static synchronized int generateId(){
        return nextId++;
    }
    
    private static Display instance;
    private final int id = generateId();
    private BufferedImage graphicsBuffer = new BufferedImage(getHorizontalResolution(), getVerticalResolution(), BufferedImage.TYPE_INT_RGB);
    private double physicalWidth = 476.0;
    private double physicalHeight = 270.0;
    private double horizontalZoom = 1.0;
    private double verticalZoom = 1.0;
    
    public static Display getInstance(){
        if(instance == null) instance = new Display();
        return instance;
    }
    
    private Display() {
    }

    public int getId() {
        return id;
    }
    
    public final int getHorizontalResolution(){
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
    
    public final int getVerticalResolution(){
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public final double getPhysicalWidth() {
        return physicalWidth;
    }

    public final double getPhysicalHeight() {
        return physicalHeight;
    }

    public final void setPhysicalWidth(double physicalWidth) {
        if(physicalWidth <= 0) physicalWidth = 1.0;
        this.physicalWidth = physicalWidth;
    }

    public final void setPhysicalHeight(double physicalHeight) {
        if(physicalHeight <= 0) physicalHeight = 1.0;
        this.physicalHeight = physicalHeight;
    }

    public final double getHorizontalZoom() {
        return horizontalZoom;
    }

    public final void setHorizontalZoom(double horizontalZoom) {
        if(horizontalZoom < 0.0000001) horizontalZoom = 0.0000001;
        this.horizontalZoom = horizontalZoom;
    }

    public final double getVerticalZoom() {
        if(verticalZoom < 0.0000001) verticalZoom = 0.0000001;
        return verticalZoom;
    }

    public final void setVerticalZoom(double verticalZoom) {
        this.verticalZoom = verticalZoom;
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
    
    public final Graphics getGraphics() {
        return new Graphics(this, graphicsBuffer.getGraphics());
    }

    public final BufferedImage getGraphicsBuffer() {
        return graphicsBuffer;
    }
    
    public final int millimetersToPixelsH(double millimeters){
        return (int) Math.round((millimeters * getHorizontalResolution() * horizontalZoom) / (physicalWidth));
    }
    
    public final int millimetersToPixelsV(double millimeters){
        return (int) Math.round((millimeters * getVerticalResolution() * verticalZoom) / (physicalHeight));
    }
    
    public final double pixelsToMillimetersH(int pixels){
        return (pixels * physicalWidth) / (getHorizontalResolution() * horizontalZoom);
    }
    
    public final double pixelsToMillimetersV(int pixels){
        return (pixels * physicalHeight) / (getVerticalResolution() * verticalZoom);
    }
}
