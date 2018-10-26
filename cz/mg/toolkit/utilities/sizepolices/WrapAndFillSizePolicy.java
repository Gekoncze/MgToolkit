package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class WrapAndFillSizePolicy implements SizePolicy {
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMaxWidth(component, Integer.MAX_VALUE);
        setMinWidth(component, component.computeWrapWidth());
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
        setMaxHeight(component, Integer.MAX_VALUE);
        setMinHeight(component, component.computeWrapHeight());
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}
