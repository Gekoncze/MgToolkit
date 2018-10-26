package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SameAsHeightSizePolicy implements SizePolicy {
    private double width = 0;
    
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMinWidth(component, width);
        setMaxWidth(component, width);
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
        if(component.getWidth() != component.getHeight()){
            width = component.getHeight();
            component.relayout();
        }
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}
