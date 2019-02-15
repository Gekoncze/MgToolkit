package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;


public class StructuredDesign implements Design {
    private final String name;
    private final ChainList<StructuredSetter> setters = new ChainList<>();

    public StructuredDesign(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    public ChainList<StructuredSetter> getSetters() {
        return setters;
    }

    @Override
    public void design(Component component) {
        for(StructuredSetter setter : setters){
            setter.set(component);
        }
    }
}
