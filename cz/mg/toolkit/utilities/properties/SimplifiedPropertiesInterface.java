package cz.mg.toolkit.utilities.properties;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.utilities.annotations.ComponentProperties;
import cz.mg.toolkit.utilities.SizePolicy;
import cz.mg.toolkit.utilities.annotations.ComponentProperty;


@ComponentProperties
public class SimplifiedPropertiesInterface extends PropertiesInterface {
    @ComponentProperty
    public static void setSizePolicy(Component component, SizePolicy sizePolicy){
        setHorizontalSizePolicy(component, sizePolicy);
        setVerticalSizePolicy(component, sizePolicy);
    }

    @ComponentProperty
    public static void setFixedWidth(Component component, double fixedWidth){
        setMinWidth(component, fixedWidth);
        setMaxWidth(component, fixedWidth);
    }

    @ComponentProperty
    public static void setFixedHeight(Component component, double fixedHeight){
        setMinHeight(component, fixedHeight);
        setMaxHeight(component, fixedHeight);
    }

    @ComponentProperty
    public static void setMinSize(Component component, double minWidth, double minHeight){
        setMinWidth(component, minWidth);
        setMinHeight(component, minHeight);
    }

    @ComponentProperty
    public static void setMinSize(Component component, double size){
        setMinWidth(component, size);
        setMinHeight(component, size);
    }

    @ComponentProperty
    public static void setMaxSize(Component component, double maxWidth, double maxHeight){
        setMaxWidth(component, maxWidth);
        setMaxHeight(component, maxHeight);
    }

    @ComponentProperty
    public static void setMaxSize(Component component, double size){
        setMaxWidth(component, size);
        setMaxHeight(component, size);
    }

    @ComponentProperty
    public static void setFixedSize(Component component, double fixedWidth, double fixedHeight){
        setFixedWidth(component, fixedWidth);
        setFixedHeight(component, fixedHeight);
    }

    @ComponentProperty
    public static void setFixedSize(Component component, double size){
        setFixedWidth(component, size);
        setFixedHeight(component, size);
    }

    @ComponentProperty
    public static void setPadding(Component component, double padding){
        setLeftPadding(component, padding);
        setRightPadding(component, padding);
        setTopPadding(component, padding);
        setBottomPadding(component, padding);
    }

    @ComponentProperty
    public static void setPadding(Component component, double top, double right, double bottom, double left){
        setLeftPadding(component, top);
        setRightPadding(component, right);
        setTopPadding(component, bottom);
        setBottomPadding(component, left);
    }

    @ComponentProperty
    public static void setHorizontalPadding(Component component, double padding){
        setLeftPadding(component, padding);
        setRightPadding(component, padding);
    }

    @ComponentProperty
    public static void setVerticalPadding(Component component, double padding){
        setTopPadding(component, padding);
        setBottomPadding(component, padding);
    }

    @ComponentProperty
    public static void setSpacing(Component component, double spacing){
        setHorizontalSpacing(component, spacing);
        setVerticalSpacing(component, spacing);
    }

    @ComponentProperty
    public static void setSpacing(Component component, double horizontalSpacing, double verticalSpacing){
        setHorizontalSpacing(component, horizontalSpacing);
        setVerticalSpacing(component, verticalSpacing);
    }

    @ComponentProperty
    public static void setScroll(Component component, double scroll){
        setHorizontalScroll(component, scroll);
        setVerticalScroll(component, scroll);
    }

    @ComponentProperty
    public static void setScroll(Component component, double horizontalScroll, double verticalScroll){
        setHorizontalScroll(component, horizontalScroll);
        setVerticalScroll(component, verticalScroll);
    }

    @ComponentProperty
    public static void setUnlimitedScroll(Component component, boolean unlimitedScroll){
        setUnlimitedHorizontalScroll(component, unlimitedScroll);
        setUnlimitedVerticalScroll(component, unlimitedScroll);
    }

    @ComponentProperty
    public static void setWeight(Component component, double weight){
        setHorizontalWeight(component, weight);
        setVerticalWeight(component, weight);
    }

    @ComponentProperty
    public static void setWeight(Component component, double horizontalWeight, double verticalWeight){
        setHorizontalWeight(component, horizontalWeight);
        setVerticalWeight(component, verticalWeight);
    }

    @ComponentProperty
    public static void setAlignment(Component component, double alignment){
        setHorizontalAlignment(component, alignment);
        setVerticalAlignment(component, alignment);
    }

    @ComponentProperty
    public static void setAlignment(Component component, double horizontalAlignment, double verticalAlignment){
        setHorizontalAlignment(component, horizontalAlignment);
        setVerticalAlignment(component, verticalAlignment);
    }

    @ComponentProperty
    public static void setContentAlignment(Component component, double alignment){
        setHorizontalContentAlignment(component, alignment);
        setVerticalContentAlignment(component, alignment);
    }

    @ComponentProperty
    public static void setContentAlignment(Component component, double horizontalAlignment, double verticalAlignment){
        setHorizontalContentAlignment(component, horizontalAlignment);
        setVerticalContentAlignment(component, verticalAlignment);
    }

    @ComponentProperty
    public static void setCell(Component component, int column, int row){
        setColumn(component, column);
        setRow(component, row);
    }

    @ComponentProperty
    public static void setCellSpan(Component component, int span){
        setColumnSpan(component, span);
        setRowSpan(component, span);
    }

    @ComponentProperty
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
