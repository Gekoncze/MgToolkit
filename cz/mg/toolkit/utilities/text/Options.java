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
    private Font font = new Font("default", 10, Font.Style.REGULAR);
    
    public Options() {
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(double leftPadding) {
        this.leftPadding = leftPadding;
    }

    public double getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(double rightPadding) {
        this.rightPadding = rightPadding;
    }

    public double getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(double topPadding) {
        this.topPadding = topPadding;
    }

    public double getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(double bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public double getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(double verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public double getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(double horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
//    public final double getHorizontalTextPosition(String text){
//        return Reshape.align(getWidth(), getFont(this).getWidth(text), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
//    }
//    
//    public final double getVerticalTextPosition(String text){
//        return Reshape.align(getHeight(), getFont(this).getHeight(), getVerticalContentAlignment(this), getTopPadding(this), getBottomPadding(this));
//    }
}
