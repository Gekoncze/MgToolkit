package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesignName;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setDesignUsed;
import java.lang.reflect.Field;


public abstract class CompositeDesigner implements Designer {
    private final ChainList<CompositeDesign> designs = new ChainList<>();
    private final CompositeDesigner parentDesigner;

    public CompositeDesigner() {
        this(null);
    }
    
    public CompositeDesigner(CompositeDesigner parentDesigner) {
        this.parentDesigner = parentDesigner;
        for(CompositeDesign design : designs) design.setDesigner(this);
    }

    public ChainList<CompositeDesign> getDesigns() {
        return designs;
    }

    @Override
    public final void design(Component component){
        CompositeDesign design = getDesign(getDesignName(component));
        if(design == null) design = getFallbackDesign(component);
        if(design != null){
            design.design(component);
            setDesignUsed(component, design.getName());
        }
    }
    
    private CompositeDesign getFallbackDesign(Component component){
        Class c = component.getClass();
        while(c != null){
            if(!Component.class.isAssignableFrom(c)) break;
            for(Field field : c.getDeclaredFields()){
                if(field.getName().equals("DEFAULT_DESIGN_NAME") && field.getType().equals(String.class)){
                    try {
                        String designName = (String) field.get(null);
                        CompositeDesign design = getDesign(designName);
                        if(design != null) return design;
                    } catch (Exception e) {}
                }
            }
            c = c.getSuperclass();
        }
        return null;
    }
    
    public final CompositeDesign getDesign(String name){
        if(name.length() <= 0) return null;
        for(CompositeDesign design : designs) if(design.getName().equals(name)) return design;
        if(parentDesigner != null) return parentDesigner.getDesign(name);
        return null;
    }
}
