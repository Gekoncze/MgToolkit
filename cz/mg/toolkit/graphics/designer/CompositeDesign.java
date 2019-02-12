package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;


public abstract class CompositeDesign {
    private CompositeDesigner designer;
    private final String name;
    private final String parentDesignName;
    private CompositeDesign parentDesign = null;

    public CompositeDesign(String name) {
        this(name, null);
    }

    public CompositeDesign(String name, String parentDesignName) {
        this.name = name;
        this.parentDesignName = parentDesignName;
    }

    public final void setDesigner(CompositeDesigner designer) {
        this.designer = designer;
    }

    public final String getName() {
        return name;
    }

    public void design(Component component){
        if(parentDesign == null && parentDesignName != null) findParentDesign();
        if(parentDesign != null) parentDesign.design(component);
        onDesign(component);
    }

    private void findParentDesign(){
        parentDesign = designer.getDesign(parentDesignName);
        if(parentDesign == null) throw new RuntimeException("Could not find parent designer " + parentDesignName + " for " + name + ".");
        checkCycle();
    }

    private void checkCycle(){
        CompositeDesign currentDesign = parentDesign;
        while(currentDesign != null) {
            if(currentDesign == this) throw new RuntimeException("Cycle detected in designer " + name + ": " + getCyclePath());
            currentDesign = currentDesign.parentDesign;
        }
    }

    private String getCyclePath(){
        ChainList<String> path = new ChainList<>();
        path.addLast(name);
        CompositeDesign currentDesign = parentDesign;
        while(currentDesign != null){
            path.addLast(currentDesign.name);
            if(currentDesign == this) return path.toString(" -> ");
            currentDesign = currentDesign.parentDesign;
        }
        return "<error>";
    }

    public abstract void onDesign(Component component);
}