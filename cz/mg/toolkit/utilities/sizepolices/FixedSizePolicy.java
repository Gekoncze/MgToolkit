package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class FixedSizePolicy implements SizePolicy {
    private double width;
    private double height;

    public FixedSizePolicy() {
    }
    
    public FixedSizePolicy(double size) {
        this.width = size;
        this.height = size;
    }

    public FixedSizePolicy(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public final double getWidth() {
        return width;
    }

    public final void setWidth(double width) {
        this.width = width;
    }

    public final double getHeight() {
        return height;
    }

    public final void setHeight(double height) {
        this.height = height;
    }
    
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMinWidth(component, width);
        setMaxWidth(component, width);
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
        setMinHeight(component, height);
        setMaxHeight(component, height);
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}
