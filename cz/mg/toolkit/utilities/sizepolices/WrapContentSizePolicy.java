package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class WrapContentSizePolicy implements SizePolicy {
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMinWidth(component, component.computeWrapWidth());
        setMaxWidth(component, component.computeWrapWidth());
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
        setMinHeight(component, component.computeWrapHeight());
        setMaxHeight(component, component.computeWrapHeight());
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}
