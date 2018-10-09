package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.textmodels.StringSinglelineTextModel;
import cz.mg.toolkit.utilities.text.SinglelineTextModel;


public class SinglelineTextContent extends DrawableContent {
    private SinglelineTextModel textModel = new StringSinglelineTextModel();

    public SinglelineTextContent() {
        this(null);
    }

    public SinglelineTextContent(String text) {
        setText(text);
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.setFont(getFont(SinglelineTextContent.this));
                g.drawText(textModel.getText(), getHorizontalTextPosition(), getVerticalTextPosition());
            }
        });
    }
    
    public final double getHorizontalTextPosition(){
        return Reshape.align(getWidth(), getFont(this).getWidth(textModel.getText()), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
    }
    
    public final double getVerticalTextPosition(){
        return Reshape.align(getHeight(), getFont(this).getHeight(), getVerticalContentAlignment(this), getTopPadding(this), getBottomPadding(this));
    }
    
    public final double getLineHeight(){
        return getFont(this).getHeight();
    }
    
    @Override
    public final double getPrefferedWidth() {
        return getFont(this).getWidth(textModel.getText()) + getLeftPadding(this) + getRightPadding(this);
    }

    @Override
    public final double getPrefferedHeight() {
        return getFont(this).getHeight() + getTopPadding(this) + getBottomPadding(this);
    }

    public final SinglelineTextModel getTextModel() {
        return textModel;
    }

    public final void setTextModel(SinglelineTextModel textModel) {
        this.textModel = textModel;
    }

    public final String getText() {
        return textModel.getText();
    }

    public final void setText(String text) {
        textModel.setText(text);
    }
    
    public final void setFontSize(int size) {
        Font oldFont = getFont(this);
        Font newFont = new Font(oldFont.getName(), size, oldFont.getStyle());
        setFont(this, newFont);
    }
}
