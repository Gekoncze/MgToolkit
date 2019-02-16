package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesignName;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setDesignUsed;


public class StructuredDesigner implements Designer {
    private final ChainList<StructuredDesign> designs = new ChainList<>();

    public StructuredDesigner() {
    }

    public ChainList<StructuredDesign> getDesigns() {
        return designs;
    }

    @Override
    public Design getDesign(String name) {
        if(name == null) return null;
        if(name.length() <= 0) return null;
        for(Design design : getDesigns()) if(design.getName().equals(name)) return design;
        return null;
    }

    @Override
    public void design(Component component) {
        Design design = getObjectDesign(component);
        if(design == null) design = getClassDesign(component);
        if(design != null){
            design.design(component);
            setDesignUsed(component, design.getName());
        }
    }

    private Design getObjectDesign(Component component){
        return getDesign(getDesignName(component));
    }

    private Design getClassDesign(Component component){
        Class c = component.getClass();
        while(c != null){
            if(!Component.class.isAssignableFrom(c)) break;
            for(Field field : c.getDeclaredFields()){
                if(Modifier.isStatic(field.getModifiers())){
                    if(field.getType().equals(String.class)){
                        if(field.getName().equals("DEFAULT_DESIGN_NAME")){
                            try {
                                String designName = (String) field.get(null);
                                Design design = getDesign(designName);
                                if(design != null) return design;
                            } catch (ReflectiveOperationException e) {}
                        }
                    }
                }
            }
            c = c.getSuperclass();
        }
        return null;
    }
}
