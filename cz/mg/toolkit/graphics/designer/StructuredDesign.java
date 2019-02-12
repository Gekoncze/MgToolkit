package cz.mg.toolkit.graphics.designer;

import cz.mg.toolkit.component.Component;


public class StructuredDesign extends CompositeDesign {
    public StructuredDesign(String name) {
        super(name);
    }

    public StructuredDesign(String name, String parentDesignName) {
        super(name, parentDesignName);
    }

    @Override
    public void onDesign(Component component) {

    }
}
