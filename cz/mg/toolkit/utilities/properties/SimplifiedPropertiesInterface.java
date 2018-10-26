package cz.mg.toolkit.utilities.properties;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.SizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setHorizontalSizePolicy;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setVerticalSizePolicy;


public class SimplifiedPropertiesInterface extends PropertiesInterface {
    public static void setSizePolicy(Component component, SizePolicy sizePolicy){
        setHorizontalSizePolicy(component, sizePolicy);
        setVerticalSizePolicy(component, sizePolicy);
    }
    
    public static void setMinSize(Component component, double minWidth, double minHeight){
        setMinWidth(component, minWidth);
        setMinHeight(component, minHeight);
    }
    
    public static void setMaxSize(Component component, double maxWidth, double maxHeight){
        setMaxWidth(component, maxWidth);
        setMaxHeight(component, maxHeight);
    }
    
    public static void setPadding(Component component, double padding){
        setLeftPadding(component, padding);
        setRightPadding(component, padding);
        setTopPadding(component, padding);
        setBottomPadding(component, padding);
    }
    
    public static void setPadding(Component component, double top, double right, double bottom, double left){
        setLeftPadding(component, top);
        setRightPadding(component, right);
        setTopPadding(component, bottom);
        setBottomPadding(component, left);
    }
    
    public static void setHorizontalPadding(Component component, double padding){
        setLeftPadding(component, padding);
        setRightPadding(component, padding);
    }
    
    public static void setVerticalPadding(Component component, double padding){
        setTopPadding(component, padding);
        setBottomPadding(component, padding);
    }
    
    public static void setSpacing(Component component, double spacing){
        setHorizontalSpacing(component, spacing);
        setVerticalSpacing(component, spacing);
    }
    
    public static void setSpacing(Component component, double horizontalSpacing, double verticalSpacing){
        setHorizontalSpacing(component, horizontalSpacing);
        setVerticalSpacing(component, verticalSpacing);
    }
    
    public static void setScroll(Component component, double scroll){
        setHorizontalScroll(component, scroll);
        setVerticalScroll(component, scroll);
    }
    
    public static void setScroll(Component component, double horizontalScroll, double verticalScroll){
        setHorizontalScroll(component, horizontalScroll);
        setVerticalScroll(component, verticalScroll);
    }
    
    public static void setUnlimitedScroll(Component component, boolean unlimitedScroll){
        setUnlimitedHorizontalScroll(component, unlimitedScroll);
        setUnlimitedVerticalScroll(component, unlimitedScroll);
    }
    
    public static void setWeight(Component component, double weight){
        setHorizontalWeight(component, weight);
        setVerticalWeight(component, weight);
    }
    
    public static void setWeight(Component component, double horizontalWeight, double verticalWeight){
        setHorizontalWeight(component, horizontalWeight);
        setVerticalWeight(component, verticalWeight);
    }
    
    public static void setAlignment(Component component, double alignment){
        setHorizontalAlignment(component, alignment);
        setVerticalAlignment(component, alignment);
    }
    
    public static void setAlignment(Component component, double horizontalAlignment, double verticalAlignment){
        setHorizontalAlignment(component, horizontalAlignment);
        setVerticalAlignment(component, verticalAlignment);
    }
    
    public static void setContentAlignment(Component component, double alignment){
        setHorizontalContentAlignment(component, alignment);
        setVerticalContentAlignment(component, alignment);
    }
    
    public static void setContentAlignment(Component component, double horizontalAlignment, double verticalAlignment){
        setHorizontalContentAlignment(component, horizontalAlignment);
        setVerticalContentAlignment(component, verticalAlignment);
    }
    
    public static void setCell(Component component, int column, int row){
        setColumn(component, column);
        setRow(component, row);
    }
    
    public static void setCellSpan(Component component, int span){
        setColumnSpan(component, span);
        setRowSpan(component, span);
    }
    
    public static void setCellSpan(Component component, int columnSpan, int rowSpan){
        setColumnSpan(component, columnSpan);
        setRowSpan(component, rowSpan);
    }
    
    public static void scrollHorizontally(Component component, double scroll){
        setHorizontalScroll(component, getHorizontalScroll(component) + scroll);
    }
    
    public static void scrollVertically(Component component, double scroll){
        setVerticalScroll(component, getVerticalScroll(component) + scroll);
    }
}
