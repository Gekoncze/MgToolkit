package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesignName;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setDesignUsed;
import java.lang.reflect.Field;


public abstract class CompositeDesigner implements Designer {
    private final ChainList<Design> designs = new ChainList<>();
    private final Designer parentDesigner;

    public CompositeDesigner() {
        this(null);
    }
    
    public CompositeDesigner(Designer parentDesigner) {
        this.parentDesigner = parentDesigner;
    }

    public ChainList<Design> getDesigns() {
        return designs;
    }

    @Override
    public final Design getDesign(String name){
        if(name.length() <= 0) return null;
        for(Design design : designs) if(design.getName().equals(name)) return design;
        if(parentDesigner != null) return parentDesigner.getDesign(name);
        return null;
    }

    @Override
    public final void design(Component component){
        Design design = getDesign(getDesignName(component));
        if(design == null) design = getFallbackDesign(component);
        if(design != null){
            design.design(component);
            setDesignUsed(component, design.getName());
        }
    }

    private Design getFallbackDesign(Component component){
        Class c = component.getClass();
        while(c != null){
            if(!Component.class.isAssignableFrom(c)) break;
            for(Field field : c.getDeclaredFields()){
                if(field.getName().equals("DEFAULT_DESIGN_NAME") && field.getType().equals(String.class)){
                    try {
                        String designName = (String) field.get(null);
                        Design design = getDesign(designName);
                        if(design != null) return design;
                    } catch (Exception e) {}
                }
            }
            c = c.getSuperclass();
        }
        return null;
    }
}
