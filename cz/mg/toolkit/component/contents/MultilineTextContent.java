package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.MultilineTextModel;
import cz.mg.toolkit.utilities.text.textmodels.StringMultilineTextModel;


public class MultilineTextContent extends TextContent {
    public static final String DEFAULT_DESIGN_NAME = "multiline text content";
    private MultilineTextModel textModel = new StringMultilineTextModel();
    
    public MultilineTextContent() {
        this("");
    }

    public MultilineTextContent(String text) {
        setText(text);
    }
    
    public final MultilineTextModel getTextModel() {
        return textModel;
    }

    public final void setTextModel(MultilineTextModel textModel) {
        this.textModel = textModel;
    }

    public final String getText() {
        return textModel.getText();
    }

    public final void setText(String text) {
        textModel.setText(text);
    }
    
    public final double getHorizontalLinePosition(int line){
        return Reshape.align(getWidth(), getFont(this).getWidth(textModel.getLine(line)), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
    }
    
    public final double getVerticalLinePosition(int line){
        return getVerticalTextPosition() + line*getLineHeight();
    }
    
    public final double getHorizontalLinePosition(int line, String text){
        return Reshape.align(getWidth(), getFont(this).getWidth(text), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
    }
    
    public final double getVerticalLinePosition(int line, String text){
        return getVerticalTextPosition() + line*getLineHeight();
    }
    
    public final double getHorizontalTextPosition(){
        return Reshape.align(getWidth(), getTextWidth(), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
    }
    
    public final double getVerticalTextPosition(){
        return Reshape.align(getHeight(), getTextHeight(), getVerticalContentAlignment(this), getTopPadding(this), getBottomPadding(this));
    }
    
    public final double getLineWidth(int line){
        return getFont(this).getWidth(textModel.getLine(line));
    }
    
    public final double getLineHeight(){
        return getFont(this).getHeight();
    }
    
    public double getTextWidth(){
        double maxWidth = 0;
        for(int iy = 0; iy < textModel.lineCount(); iy++) maxWidth = Math.max(maxWidth, getLineWidth(iy));
        return maxWidth;
    }
    
    public double getTextHeight(){
        return getLineHeight() * textModel.lineCount();
    }
    
    @Override
    public final double getPrefferedWidth() {
        double maxWidth = 0;
        Font font = getFont(this);
        for(int iy = 0; iy < textModel.lineCount(); iy++){
            String line = textModel.getLine(iy);
            maxWidth = Math.max(maxWidth, font.getWidth(line));
        }
        return maxWidth;
    }

    @Override
    public final double getPrefferedHeight() {
        return getFont(this).getHeight() * textModel.lineCount();
    }
}
