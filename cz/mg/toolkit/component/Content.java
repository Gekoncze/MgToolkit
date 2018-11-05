package cz.mg.toolkit.component;


public abstract class Content extends Component {
    public static String DEFAULT_DESIGN_NAME = "content";
    
    private double contentWidth;
    private double contentHeight;
    private boolean usePrefferedSize;
    
    public Content(){
        usePrefferedSize = true;
    }
    
    public Content(double contentWidth, double contentHeight) {
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
        usePrefferedSize = false;
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

    public final boolean isUsePrefferedSize() {
        return usePrefferedSize;
    }

    public final void setUsePrefferedSize(boolean usePrefferedSize) {
        this.usePrefferedSize = usePrefferedSize;
    }
    
    @Override
    public final double computeWrapWidth() {
        return usePrefferedSize ? getPrefferedWidth() : getContentWidth();
    }

    @Override
    public final double computeWrapHeight() {
        return usePrefferedSize ? getPrefferedHeight() : getContentHeight();
    }
    
    public double getPrefferedWidth(){
        return getContentWidth();
    }
    
    public double getPrefferedHeight(){
        return getContentHeight();
    }
}
