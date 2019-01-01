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
    
    public Options copy(){
        Options copy = new Options();
        copy.width = width;
        copy.height = height;
        copy.leftPadding = leftPadding;
        copy.rightPadding = rightPadding;
        copy.topPadding = topPadding;
        copy.bottomPadding = bottomPadding;
        copy.verticalAlignment = verticalAlignment;
        copy.horizontalAlignment = horizontalAlignment;
        copy.font = font;
        return copy;
    }
    
    public static boolean equals(Options o1, Options o2){
        return
                o1.width == o2.width &&
                o1.height == o2.height &&
                o1.leftPadding == o2.leftPadding &&
                o1.rightPadding == o2.rightPadding &&
                o1.topPadding == o2.topPadding &&
                o1.bottomPadding == o2.bottomPadding &&
                o1.verticalAlignment == o2.verticalAlignment &&
                o1.horizontalAlignment == o2.horizontalAlignment &&
                o1.font == o2.font;
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
}
