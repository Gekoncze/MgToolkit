package cz.mg.toolkit.graphics.designer;

import cz.mg.collections.Collection;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesignName;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setDesignUsed;
import java.lang.reflect.Field;


public abstract class CompositeDesigner implements Designer {
    private final Collection<Design> designs;
    private final CompositeDesigner parentDesigner;

    public CompositeDesigner(Collection<Design> designs) {
        this(null, designs);
    }
    
    public CompositeDesigner(CompositeDesigner parentDesigner, Collection<Design> designs) {
        this.designs = designs;
        this.parentDesigner = parentDesigner;
        for(Design design : designs) design.setDesigner(this);
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
    
    public final Design getDesign(String name){
        if(name.length() <= 0) return null;
        for(Design design : designs) if(design.getName().equals(name)) return design;
        if(parentDesigner != null) return parentDesigner.getDesign(name);
        return null;
    }
    
    public static abstract class Design {
        private CompositeDesigner designer;
        private final String name;
        private final String parentDesignName;
        private Design parentDesign = null;

        public Design(String name) {
            this(name, null);
        }

        public Design(String name, String parentDesignName) {
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
            Design currentDesign = parentDesign;
            while(currentDesign != null) {
                if(currentDesign == this) throw new RuntimeException("Cycle detected in designer " + name + ": " + getCyclePath());
                currentDesign = currentDesign.parentDesign;
            }
        }

        private String getCyclePath(){
            ChainList<String> path = new ChainList<>();
            path.addLast(name);
            Design currentDesign = parentDesign;
            while(currentDesign != null){
                path.addLast(currentDesign.name);
                if(currentDesign == this) return path.toString(" -> ");
                currentDesign = currentDesign.parentDesign;
            }
            return "<error>";
        }

        public abstract void onDesign(Component component);
    }
}
