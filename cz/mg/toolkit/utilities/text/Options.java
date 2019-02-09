package cz.mg.toolkit.utilities.text;

import cz.mg.toolkit.graphics.Font;


public class Options {
    private double width;
    private double height;
    private double leftPadding;
    private double rightPadding;
    private double topPadding;
    private double bottomPadding;
    private double verticalAlignment;
    private double horizontalAlignment;
    private double verticalSpacing;
    private Font font = new Font("default", 10, Font.Style.REGULAR);
    private boolean changed = true;
    
    public Options() {
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if(this.width != width) changed = true;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if(this.height != height) changed = true;
        this.height = height;
    }

    public double getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(double leftPadding) {
        if(this.leftPadding != leftPadding) changed = true;
        this.leftPadding = leftPadding;
    }

    public double getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(double rightPadding) {
        if(this.rightPadding != rightPadding) changed = true;
        this.rightPadding = rightPadding;
    }

    public double getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(double topPadding) {
        if(this.topPadding != topPadding) changed = true;
        this.topPadding = topPadding;
    }

    public double getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(double bottomPadding) {
        if(this.bottomPadding != bottomPadding) changed = true;
        this.bottomPadding = bottomPadding;
    }

    public double getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(double verticalAlignment) {
        if(this.verticalAlignment != verticalAlignment) changed = true;
        this.verticalAlignment = verticalAlignment;
    }

    public double getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(double horizontalAlignment) {
        if(this.horizontalAlignment != horizontalAlignment) changed = true;
        this.horizontalAlignment = horizontalAlignment;
    }

    public double getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(double verticalSpacing) {
        if(this.verticalSpacing != verticalSpacing) changed = true;
        this.verticalSpacing = verticalSpacing;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        if(this.font != font) changed = true;
        this.font = font;
    }
}
