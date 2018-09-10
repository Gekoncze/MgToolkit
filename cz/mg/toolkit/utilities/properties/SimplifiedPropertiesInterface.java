package cz.mg.toolkit.utilities.properties;

import cz.mg.toolkit.component.Component;


public class SimplifiedPropertiesInterface extends PropertiesInterface {
    public static void setMinSize(Component component, double minWidth, double minHeight){
        setMinWidth(component, minWidth);
        setMinHeight(component, minHeight);
    }
    
    public static void setMaxSize(Component component, double maxWidth, double maxHeight){
        setMaxWidth(component, maxWidth);
        setMaxHeight(component, maxHeight);
    }
    
    public static void setWrapContent(Component component){
        setWrapMinWidth(component, true);
        setWrapMinHeight(component, true);
        setWrapMaxWidth(component, true);
        setWrapMaxHeight(component, true);
    }
    
    public static void setWrapContentWidth(Component component){
        setWrapMinWidth(component, true);
        setWrapMaxWidth(component, true);
    }
    
    public static void setWrapContentHeight(Component component){
        setWrapMinHeight(component, true);
        setWrapMaxHeight(component, true);
    }
    
    public static void setFillParent(Component component){
        setWrapMinWidth(component, false);
        setWrapMinHeight(component, false);
        setWrapMaxWidth(component, false);
        setWrapMaxHeight(component, false);
        setMinWidth(component, 0.0);
        setMinHeight(component, 0.0);
        setMaxWidth(component, Integer.MAX_VALUE);
        setMaxHeight(component, Integer.MAX_VALUE);
    }
    
    public static void setFillParentWidth(Component component){
        setWrapMinWidth(component, false);
        setWrapMaxWidth(component, false);
        setMinWidth(component, 0.0);
        setMaxWidth(component, Integer.MAX_VALUE);
    }
    
    public static void setFillParentHeight(Component component){
        setWrapMinHeight(component, false);
        setWrapMaxHeight(component, false);
        setMinHeight(component, 0.0);
        setMaxHeight(component, Integer.MAX_VALUE);
    }
    
    public static void setWrapAndFill(Component component){
        setWrapMinWidth(component, true);
        setWrapMinHeight(component, true);
        setWrapMaxWidth(component, false);
        setWrapMaxHeight(component, false);
        setMaxWidth(component, Integer.MAX_VALUE);
        setMaxHeight(component, Integer.MAX_VALUE);
    }
    
    public static void setWrapAndFillWidth(Component component){
        setWrapMinWidth(component, true);
        setWrapMaxWidth(component, false);
        setMaxWidth(component, Integer.MAX_VALUE);
    }
    
    public static void setWrapAndFillHeight(Component component){
        setWrapMinHeight(component, true);
        setWrapMaxHeight(component, false);
        setMaxHeight(component, Integer.MAX_VALUE);
    }
    
    public static void setFixedSize(Component component, double width, double height){
        setWrapMinWidth(component, false);
        setWrapMinHeight(component, false);
        setWrapMaxWidth(component, false);
        setWrapMaxHeight(component, false);
        setMinWidth(component, width);
        setMinHeight(component, height);
        setMaxWidth(component, width);
        setMaxHeight(component, height);
    }
    
    public static void setFixedWidth(Component component, double width){
        setWrapMinWidth(component, false);
        setWrapMaxWidth(component, false);
        setMinWidth(component, width);
        setMaxWidth(component, width);
    }
    
    public static void setFixedHeight(Component component, double height){
        setWrapMinHeight(component, false);
        setWrapMaxHeight(component, false);
        setMinHeight(component, height);
        setMaxHeight(component, height);
    }
    
    public static void setLimitedSize(Component component, double minWidth, double minHeight, double maxWidth, double maxHeight){
        setWrapMinWidth(component, false);
        setWrapMinHeight(component, false);
        setWrapMaxWidth(component, false);
        setWrapMaxHeight(component, false);
        setMinWidth(component, minWidth);
        setMinHeight(component, minHeight);
        setMaxWidth(component, maxWidth);
        setMaxHeight(component, maxHeight);
    }
    
    public static void setLimitedWidth(Component component, double minWidth, double maxWidth){
        setWrapMinWidth(component, false);
        setWrapMaxWidth(component, false);
        setMinWidth(component, minWidth);
        setMaxWidth(component, maxWidth);
    }
    
    public static void setLimitedHeight(Component component, double minHeight, double maxHeight){
        setWrapMinHeight(component, false);
        setWrapMaxHeight(component, false);
        setMinHeight(component, minHeight);
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
