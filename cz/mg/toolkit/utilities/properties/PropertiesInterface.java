package cz.mg.toolkit.utilities.properties;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Font;


public class PropertiesInterface {
    private static final Color UNDEFINED_COLOR = new Color(255, 0, 255, 255);
    private static final Font UNDEFINED_FONT = new Font("Serif", 18, Font.Style.REGULAR);
    
    private static final int ID = Properties.generateId();
    private static final int MIN_WIDTH = Properties.generateId();
    private static final int MIN_HEIGHT = Properties.generateId();
    private static final int MAX_WIDTH = Properties.generateId();
    private static final int MAX_HEIGHT = Properties.generateId();
    private static final int WRAP_MIN_WIDTH = Properties.generateId();
    private static final int WRAP_MIN_HEIGHT = Properties.generateId();
    private static final int WRAP_MAX_WIDTH = Properties.generateId();
    private static final int WRAP_MAX_HEIGHT = Properties.generateId();
    private static final int HORIZONTAL_ALIGNMENT = Properties.generateId();
    private static final int VERTICAL_ALIGNMENT = Properties.generateId();
    private static final int HORIZONTAL_CONTENT_ALIGNMENT = Properties.generateId();
    private static final int VERTICAL_CONTENT_ALIGNMENT = Properties.generateId();
    private static final int HORIZONTAL_WEIGHT = Properties.generateId();
    private static final int VERTICAL_WEIGHT = Properties.generateId();
    private static final int LEFT_PADDING = Properties.generateId();
    private static final int RIGHT_PADDING = Properties.generateId();
    private static final int TOP_PADDING = Properties.generateId();
    private static final int BOTTOM_PADDING = Properties.generateId();
    private static final int HORIZONTAL_SPACING = Properties.generateId();
    private static final int VERTICAL_SPACING = Properties.generateId();
    private static final int HORIZONTAL_SCROLL = Properties.generateId();
    private static final int VERTICAL_SCROLL = Properties.generateId();
    private static final int UNLIMITED_HORIZONTAL_SCROLL = Properties.generateId();
    private static final int UNLIMITED_VERTICAL_SCROLL = Properties.generateId();
    private static final int COLUMN = Properties.generateId();
    private static final int ROW = Properties.generateId();
    private static final int COLUMN_SPAN = Properties.generateId();
    private static final int ROW_SPAN = Properties.generateId();
    private static final int HORIZONTALLY_OPTIONAL = Properties.generateId();
    private static final int VERTICALLY_OPTIONAL = Properties.generateId();
    private static final int BACKGROUND_COLOR = Properties.generateId();
    private static final int FOREGROUND_COLOR = Properties.generateId();
    private static final int DISABLED_BACKGROUND_COLOR = Properties.generateId();
    private static final int DISABLED_FOREGROUND_COLOR = Properties.generateId();
    private static final int HIGHLIGHTED_BACKGROUND_COLOR = Properties.generateId();
    private static final int HIGHLIGHTED_FOREGROUND_COLOR = Properties.generateId();
    private static final int BORDER = Properties.generateId();
    private static final int BACKGROUND = Properties.generateId();
    private static final int CONTEXT = Properties.generateId();
    private static final int FONT = Properties.generateId();
    private static final int CONTRAST_COLOR = Properties.generateId();
    
    public static int getId(Component component){
        return (int) component.getProperties().get(ID, 0);
    }
    
    public static void setId(Component component, int id){
        component.getProperties().set(ID, id);
    }
    
    public static double getX(Component component){
        return component.getX();
    }
    
    public static void setX(Component component, double x){
        component.setX(x);
    }
    
    public static double getY(Component component){
        return component.getY();
    }
    
    public static void setY(Component component, double y){
        component.setY(y);
    }
    
    public static double getWidth(Component component){
        return component.getWidth();
    }
    
    public static void setWidth(Component component, double width){
        component.setWidth(width);
    }
    
    public static double getHeight(Component component){
        return component.getHeight();
    }
    
    public static void setHeight(Component component, double height){
        component.setHeight(height);
    }
    
    public static double getMinWidth(Component component){
        return (double) component.getProperties().get(MIN_WIDTH, 0.0);
    }
    
    public static void setMinWidth(Component component, double minWidth){
        if(minWidth < 0.0) minWidth = 0.0;
        component.getProperties().set(MIN_WIDTH, minWidth);
    }
    
    public static double getMinHeight(Component component){
        return (double) component.getProperties().get(MIN_HEIGHT, 0.0);
    }
    
    public static void setMinHeight(Component component, double minHeight){
        if(minHeight < 0.0) minHeight = 0.0;
        component.getProperties().set(MIN_HEIGHT, minHeight);
    }
    
    public static double getMaxWidth(Component component){
        return (double) component.getProperties().get(MAX_WIDTH, Integer.MAX_VALUE);
    }
    
    public static void setMaxWidth(Component component, double maxWidth){
        if(maxWidth < 0.0) maxWidth = 0.0;
        component.getProperties().set(MAX_WIDTH, maxWidth);
    }
    
    public static double getMaxHeight(Component component){
        return (double) component.getProperties().get(MAX_HEIGHT, Integer.MAX_VALUE);
    }
    
    public static void setMaxHeight(Component component, double maxHeight){
        if(maxHeight < 0.0) maxHeight = 0.0;
        component.getProperties().set(MAX_HEIGHT, maxHeight);
    }
    
    public static boolean isWrapMinWidth(Component component){
        return (boolean) component.getProperties().get(WRAP_MIN_WIDTH, true);
    }
    
    public static void setWrapMinWidth(Component component, boolean wrapMinWidth){
        component.getProperties().set(WRAP_MIN_WIDTH, wrapMinWidth);
    }
    
    public static boolean isWrapMinHeight(Component component){
        return (boolean) component.getProperties().get(WRAP_MIN_HEIGHT, true);
    }
    
    public static void setWrapMinHeight(Component component, boolean wrapMinHeight){
        component.getProperties().set(WRAP_MIN_HEIGHT, wrapMinHeight);
    }
    
    public static boolean isWrapMaxWidth(Component component){
        return (boolean) component.getProperties().get(WRAP_MAX_WIDTH, true);
    }
    
    public static void setWrapMaxWidth(Component component, boolean wrapMaxWidth){
        component.getProperties().set(WRAP_MAX_WIDTH, wrapMaxWidth);
    }
    
    public static boolean isWrapMaxHeight(Component component){
        return (boolean) component.getProperties().get(WRAP_MAX_HEIGHT, true);
    }
    
    public static void setWrapMaxHeight(Component component, boolean wrapMaxHeight){
        component.getProperties().set(WRAP_MAX_HEIGHT, wrapMaxHeight);
    }
    
    public static double getLeftPadding(Component component){
        return (double) component.getProperties().get(LEFT_PADDING, 0.0);
    }
    
    public static void setLeftPadding(Component component, double leftPadding){
        if(leftPadding < 0.0) leftPadding = 0.0;
        component.getProperties().set(LEFT_PADDING, leftPadding);
    }
    
    public static double getRightPadding(Component component){
        return (double) component.getProperties().get(RIGHT_PADDING, 0.0);
    }
    
    public static void setRightPadding(Component component, double rightPadding){
        if(rightPadding < 0.0) rightPadding = 0.0;
        component.getProperties().set(RIGHT_PADDING, rightPadding);
    }
    
    public static double getTopPadding(Component component){
        return (double) component.getProperties().get(TOP_PADDING, 0.0);
    }
    
    public static void setTopPadding(Component component, double topPadding){
        if(topPadding < 0.0) topPadding = 0.0;
        component.getProperties().set(TOP_PADDING, topPadding);
    }
    
    public static double getBottomPadding(Component component){
        return (double) component.getProperties().get(BOTTOM_PADDING, 0.0);
    }
    
    public static void setBottomPadding(Component component, double bottomPadding){
        if(bottomPadding < 0.0) bottomPadding = 0.0;
        component.getProperties().set(BOTTOM_PADDING, bottomPadding);
    }
    
    public static double getHorizontalSpacing(Component component){
        return (double) component.getProperties().get(HORIZONTAL_SPACING, 0.0);
    }
    
    public static void setHorizontalSpacing(Component component, double horizontalSpacing){
        if(horizontalSpacing < 0.0) horizontalSpacing = 0.0;
        component.getProperties().set(HORIZONTAL_SPACING, horizontalSpacing);
    }
    
    public static double getVerticalSpacing(Component component){
        return (double) component.getProperties().get(VERTICAL_SPACING, 0.0);
    }
    
    public static void setVerticalSpacing(Component component, double verticalSpacing){
        if(verticalSpacing < 0.0) verticalSpacing = 0.0;
        component.getProperties().set(VERTICAL_SPACING, verticalSpacing);
    }
    
    public static double getHorizontalScroll(Component component){
        return (double) component.getProperties().get(HORIZONTAL_SCROLL, 0.0);
    }
    
    public static void setHorizontalScroll(Component component, double horizontalScroll){
        component.getProperties().set(HORIZONTAL_SCROLL, horizontalScroll);
    }
    
    public static double getVerticalScroll(Component component){
        return (double) component.getProperties().get(VERTICAL_SCROLL, 0.0);
    }
    
    public static void setVerticalScroll(Component component, double verticalScroll){
        component.getProperties().set(VERTICAL_SCROLL, verticalScroll);
    }
    
    public static boolean isUnlimitedHorizontalScroll(Component component){
        return (boolean) component.getProperties().get(UNLIMITED_HORIZONTAL_SCROLL, false);
    }
    
    public static void setUnlimitedHorizontalScroll(Component component, boolean unlimitedHorizontalScroll){
        component.getProperties().set(UNLIMITED_HORIZONTAL_SCROLL, unlimitedHorizontalScroll);
    }
    
    public static boolean isUnlimitedVerticalScroll(Component component){
        return (boolean) component.getProperties().get(UNLIMITED_VERTICAL_SCROLL, false);
    }
    
    public static void setUnlimitedVerticalScroll(Component component, boolean unlimitedVerticalScroll){
        component.getProperties().set(UNLIMITED_VERTICAL_SCROLL, unlimitedVerticalScroll);
    }
    
    public static double getHorizontalWeight(Component component){
        return (double) component.getProperties().get(HORIZONTAL_WEIGHT, 1.0);
    }
    
    public static void setHorizontalWeight(Component component, double horizontalWeight){
        if(horizontalWeight < 0.0) horizontalWeight = 0.0;
        component.getProperties().set(HORIZONTAL_WEIGHT, horizontalWeight);
    }
    
    public static double getVerticalWeight(Component component){
        return (double) component.getProperties().get(VERTICAL_WEIGHT, 1.0);
    }
    
    public static void setVerticalWeight(Component component, double verticalWeight){
        if(verticalWeight < 0.0) verticalWeight = 0.0;
        component.getProperties().set(VERTICAL_WEIGHT, verticalWeight);
    }
    
    public static double getHorizontalAlignment(Component component){
        return (double) component.getProperties().get(HORIZONTAL_ALIGNMENT, 0.0);
    }
    
    public static void setHorizontalAlignment(Component component, double horizontalAlignment){
        if(horizontalAlignment < 0.0) horizontalAlignment = 0.0;
        if(horizontalAlignment > 1.0) horizontalAlignment = 1.0;
        component.getProperties().set(HORIZONTAL_ALIGNMENT, horizontalAlignment);
    }
    
    public static double getVerticalAlignment(Component component){
        return (double) component.getProperties().get(VERTICAL_ALIGNMENT, 0.0);
    }
    
    public static void setVerticalAlignment(Component component, double verticalAlignment){
        if(verticalAlignment < 0.0) verticalAlignment = 0.0;
        if(verticalAlignment > 1.0) verticalAlignment = 1.0;
        component.getProperties().set(VERTICAL_ALIGNMENT, verticalAlignment);
    }
    
    public static double getHorizontalContentAlignment(Component component){
        return (double) component.getProperties().get(HORIZONTAL_CONTENT_ALIGNMENT, 0.0);
    }
    
    public static void setHorizontalContentAlignment(Component component, double horizontalContentAlignment){
        if(horizontalContentAlignment < 0.0) horizontalContentAlignment = 0.0;
        if(horizontalContentAlignment > 1.0) horizontalContentAlignment = 1.0;
        component.getProperties().set(HORIZONTAL_CONTENT_ALIGNMENT, horizontalContentAlignment);
    }
    
    public static double getVerticalContentAlignment(Component component){
        return (double) component.getProperties().get(VERTICAL_CONTENT_ALIGNMENT, 0.0);
    }
    
    public static void setVerticalContentAlignment(Component component, double verticalContentAlignment){
        if(verticalContentAlignment < 0.0) verticalContentAlignment = 0.0;
        if(verticalContentAlignment > 1.0) verticalContentAlignment = 1.0;
        component.getProperties().set(VERTICAL_CONTENT_ALIGNMENT, verticalContentAlignment);
    }
    
    public static int getColumn(Component component){
        return (int) component.getProperties().get(COLUMN, 0);
    }
     
    public static void setColumn(Component component, int column){
        if(column < 0) column = 0;
        component.getProperties().set(COLUMN, column);
    }
    
    public static int getRow(Component component){
        return (int) component.getProperties().get(ROW, 0);
    }
    
    public static void setRow(Component component, int row){
        if(row < 0) row = 0;
        component.getProperties().set(ROW, row);
    }
    
    public static int getColumnSpan(Component component){
        return (int) component.getProperties().get(COLUMN_SPAN, 1);
    }
    
    public static void setColumnSpan(Component component, int columnSpan){
        if(columnSpan < 1) columnSpan = 1;
        component.getProperties().set(COLUMN_SPAN, columnSpan);
    }
    
    public static int getRowSpan(Component component){
        return (int) component.getProperties().get(ROW_SPAN, 1);
    }
    
    public static void setRowSpan(Component component, int rowSpan){
        if(rowSpan < 1) rowSpan = 1;
        component.getProperties().set(ROW_SPAN, rowSpan);
    }
    
    public static boolean isHorizontallyOptional(Component component){
        return (boolean) component.getProperties().get(HORIZONTALLY_OPTIONAL, false);
    }
    
    public static void setHorizontallyOptional(Component component, boolean horizontallyOptional){
        component.getProperties().set(HORIZONTALLY_OPTIONAL, horizontallyOptional);
    }
    
    public static boolean isVerticallyOptional(Component component){
        return (boolean) component.getProperties().get(VERTICALLY_OPTIONAL, false);
    }
    
    public static void setVerticallyOptional(Component component, boolean verticallyOptional){
        component.getProperties().set(VERTICALLY_OPTIONAL, verticallyOptional);
    }
    
    public static final Color getBackgroundColor(Component component){
        return (Color) component.getProperties().get(BACKGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setBackgroundColor(Component component, Color color){
        component.getProperties().set(BACKGROUND_COLOR, color);
    }
    
    public static final Color getForegroundColor(Component component){
        return (Color) component.getProperties().get(FOREGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setForegroundColor(Component component, Color color){
        component.getProperties().set(FOREGROUND_COLOR, color);
    }
    
    public static final Color getDisabledBackgroundColor(Component component){
        return (Color) component.getProperties().get(DISABLED_BACKGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setDisabledBackgroundColor(Component component, Color color){
        component.getProperties().set(DISABLED_BACKGROUND_COLOR, color);
    }
    
    public static final Color getDisabledForegroundColor(Component component){
        return (Color) component.getProperties().get(DISABLED_FOREGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setDisabledForegroundColor(Component component, Color color){
        component.getProperties().set(DISABLED_FOREGROUND_COLOR, color);
    }
    
    public static final Color getHighlightedBackgroundColor(Component component){
        return (Color) component.getProperties().get(HIGHLIGHTED_BACKGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setHighlightedBackgroundColor(Component component, Color color){
        component.getProperties().set(HIGHLIGHTED_BACKGROUND_COLOR, color);
    }
    
    public static final Color getHighlightedForegroundColor(Component component){
        return (Color) component.getProperties().get(HIGHLIGHTED_FOREGROUND_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setHighlightedForegroundColor(Component component, Color color){
        component.getProperties().set(HIGHLIGHTED_FOREGROUND_COLOR, color);
    }
    
    public static final Border getBorder(Component component){
        return (Border) component.getProperties().get(BORDER, null);
    }
    
    public static final void setBorder(Component component, Border border){
        component.getProperties().set(BORDER, border);
    }
    
    public static final Background getBackground(Component component){
        return (Background) component.getProperties().get(BACKGROUND, null);
    }
    
    public static final void setBackground(Component component, Background background){
        component.getProperties().set(BACKGROUND, background);
    }
    
    public static final String getContext(Component component){
        return (String) component.getProperties().get(CONTEXT, null);
    }
    
    public static final void setContext(Component component, String context){
        component.getProperties().set(CONTEXT, context);
    }
    
    public static final Font getFont(Component component){
        return (Font) component.getProperties().get(FONT, UNDEFINED_FONT);
    }
    
    public static final void setFont(Component component, Font font){
        component.getProperties().set(FONT, font);
    }
    
    public static final Color getContrastColor(Component component){
        return (Color) component.getProperties().get(CONTRAST_COLOR, UNDEFINED_COLOR);
    }
    
    public static final void setContrastColor(Component component, Color color){
        component.getProperties().set(CONTRAST_COLOR, color);
    }
}
