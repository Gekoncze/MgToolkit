package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;


public class StructuredDesign implements Design {
    private final String name;
    private final String parentName;
    private final ChainList<StructuredSetter> setters = new ChainList<>();

    public StructuredDesign(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
    }

    @Override
    public final String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
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
