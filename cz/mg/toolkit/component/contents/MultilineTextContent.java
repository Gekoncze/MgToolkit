package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.MultilineTextModel;
import cz.mg.toolkit.utilities.text.textmodels.StringMultilineTextModel;


public class MultilineTextContent extends DrawableContent {
    private MultilineTextModel textModel = new StringMultilineTextModel();
    
    public MultilineTextContent() {
        setText("");
        addEventListeners();
    }

    public MultilineTextContent(String text) {
        setText(text);
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                Font font = getFont(MultilineTextContent.this);
                g.setColor(getCurrentForegroundColor());
                g.setFont(font);
                double y = getVerticalTextPosition();
                for(int iy = 0; iy < textModel.lineCount(); iy++){
                    String line = textModel.getLine(iy);
                    double x = getHorizontalLinePosition(line);
                    g.drawText(line, x, y);
                    y += font.getHeight();
                }
            }
        });
    }
    
    protected double getHorizontalLinePosition(String line){
        return Reshape.align(getWidth(), getFont(this).getWidth(line), getHorizontalContentAlignment(this));
    }
    
    protected double getHorizontalTextPosition(){
        return Reshape.align(getWidth(), getTextWidth(), getHorizontalContentAlignment(this));
    }
    
    protected double getVerticalTextPosition(){
        return Reshape.align(getHeight(), getTextHeight(), getVerticalContentAlignment(this));
    }
    
    private double getTextWidth(){
        double maxWidth = 0;
        for(int iy = 0; iy < textModel.lineCount(); iy++) maxWidth = Math.max(maxWidth, getFont(this).getWidth(textModel.getLine(iy)));
        return maxWidth;
    }
    
    private double getTextHeight(){
        return getFont(this).getHeight() * textModel.lineCount();
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
}
