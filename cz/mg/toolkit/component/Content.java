package cz.mg.toolkit.component;


public abstract class Content extends Component {
    public static String DEFAULT_DESIGN_NAME = "content";
    
    private double contentWidth;
    private double contentHeight;
    private boolean autosize;
    
    public Content(){
        autosize = true;
    }
    
    public Content(double contentWidth, double contentHeight) {
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
        autosize = false;
    }
    
    @Override
    public final double getContentWidth() {
        return contentWidth;
    }

    @Override
    public final double getContentHeight() {
        return contentHeight;
    }
    
    public final void setContentWidth(double contentWidth) {
        this.contentWidth = contentWidth;
    }

    public final void setContentHeight(double contentHeight) {
        this.contentHeight = contentHeight;
    }
    
    public final void setContentSize(double width, double height){
        setContentWidth(width);
        setContentHeight(height);
    }

    public final boolean isAutosize() {
        return autosize;
    }

    public final void setAutosize(boolean autosize) {
        this.autosize = autosize;
    }
    
    @Override
    public final double computeWrapWidth() {
        return autosize ? getRequiredWidth() : getContentWidth();
    }

    @Override
    public final double computeWrapHeight() {
        return autosize ? getRequiredHeight() : getContentHeight();
    }
    
    public double getRequiredWidth(){
        return getContentWidth();
    }
    
    public double getRequiredHeight(){
        return getContentHeight();
    }
}
