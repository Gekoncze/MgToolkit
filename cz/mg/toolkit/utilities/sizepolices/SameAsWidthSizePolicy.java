package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SameAsWidthSizePolicy implements SizePolicy {
    private double height = 0;
    
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
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
        if(component.getHeight() != component.getWidth()){
            height = component.getWidth();
            component.relayout();
        }
    }
}
