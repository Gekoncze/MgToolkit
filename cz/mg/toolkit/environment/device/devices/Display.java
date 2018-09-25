package cz.mg.toolkit.environment.device.devices;

import cz.mg.toolkit.environment.device.Device;
import cz.mg.toolkit.impl.ImplDisplay;


public class Display extends Device {
    private double physicalWidth = 476.0;
    private double physicalHeight = 270.0;
    private double horizontalZoom = 1.0;
    private double verticalZoom = 1.0;
    private final ImplDisplay implDisplay;
    
    public Display(ImplDisplay implDisplay) {
        this.implDisplay = implDisplay;
    }

    public ImplDisplay getImplDisplay() {
        return implDisplay;
    }
    
    public final int getHorizontalResolution(){
        return implDisplay.getHorizontalResolution();
    }
    
    public final int getVerticalResolution(){
        return implDisplay.getVerticalResolution();
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
    
    public final double getWidth(){
        return pixelsToMillimetersH(getHorizontalResolution());
    }
    
    public final double getHeight(){
        return pixelsToMillimetersV(getVerticalResolution());
    }

    public final double getHorizontalZoom() {
        return horizontalZoom;
    }

    public final void setHorizontalZoom(double horizontalZoom) {
        if(horizontalZoom < 0.0000001) horizontalZoom = 0.0000001;
        this.horizontalZoom = horizontalZoom;
    }

    public final double getVerticalZoom() {
        return verticalZoom;
    }

    public final void setVerticalZoom(double verticalZoom) {
        if(verticalZoom < 0.0000001) verticalZoom = 0.0000001;
        this.verticalZoom = verticalZoom;
    }
    
    public final double millimetersToPixelsH(double millimeters){
        return (millimeters * getHorizontalResolution() * horizontalZoom) / (physicalWidth);
    }
    
    public final double millimetersToPixelsV(double millimeters){
        return (millimeters * getVerticalResolution() * verticalZoom) / (physicalHeight);
    }
    
    public final double pixelsToMillimetersH(double pixels){
        return (pixels * physicalWidth) / (getHorizontalResolution() * horizontalZoom);
    }
    
    public final double pixelsToMillimetersV(double pixels){
        return (pixels * physicalHeight) / (getVerticalResolution() * verticalZoom);
    }
}
