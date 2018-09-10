package cz.mg.toolkit.layout.reshapes;

import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class Reshape {
    public static double getEffectiveMinWidth(Component component){
        if(component.isHidden()) return 0.0;
        else return getMinWidth(component);
    }
    
    public static double getEffectiveMaxWidth(Component component){
        if(component.isHidden()) return 0.0;
        else return getMaxWidth(component);
    }
    
    public static double getEffectiveMinHeight(Component component){
        if(component.isHidden()) return 0.0;
        else return getMinHeight(component);
    }
    
    public static double getEffectiveMaxHeight(Component component){
        if(component.isHidden()) return 0.0;
        else return getMaxHeight(component);
    }
    
    public static double align(double parentSize, double contentSize, double alignment){
        return alignment*(parentSize - contentSize);
    }
    
    public static double align(double parentSize, double contentSize, double alignment, double leadingPadding, double trailingPadding){
        return alignment*(parentSize - contentSize) + (1.0 - alignment)*(leadingPadding) - alignment*(trailingPadding);
    }
}
