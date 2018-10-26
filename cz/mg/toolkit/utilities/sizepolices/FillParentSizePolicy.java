package cz.mg.toolkit.utilities.sizepolices;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class FillParentSizePolicy implements SizePolicy {
    @Override
    public void beforeLayoutUpdateHorizontal(Component component) {
        setMinWidth(component, 0.0);
        setMaxWidth(component, Integer.MAX_VALUE);
    }

    @Override
    public void beforeLayoutUpdateVertical(Component component) {
        setMinHeight(component, 0.0);
        setMaxHeight(component, Integer.MAX_VALUE);
    }

    @Override
    public void afterLayoutUpdateHorizontal(Component component) {
    }

    @Override
    public void afterLayoutUpdateVertical(Component component) {
    }
}