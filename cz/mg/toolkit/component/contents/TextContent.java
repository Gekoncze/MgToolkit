package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class TextContent extends DrawableContent {
    private String text = "";

    public TextContent() {
        this(null);
    }

    public TextContent(String text) {
        setText(text);
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.setFont(getFont(TextContent.this));
                g.drawText(text, getHorizontalTextPosition(), getVerticalTextPosition());
            }
        });
    }
    
    public final double getHorizontalTextPosition(){
        return Reshape.align(getWidth(), getFont(this).getWidth(text), getHorizontalContentAlignment(this), getLeftPadding(this), getRightPadding(this));
    }
    
    public final double getVerticalTextPosition(){
        return Reshape.align(getHeight(), getFont(this).getHeight(), getVerticalContentAlignment(this), getTopPadding(this), getBottomPadding(this));
    }
    
    @Override
    public final double getPrefferedWidth() {
        return getFont(this).getWidth(text) + getLeftPadding(this) + getRightPadding(this);
    }

    @Override
    public final double getPrefferedHeight() {
        return getFont(this).getHeight() + getTopPadding(this) + getBottomPadding(this);
    }

    public final String getText() {
        return text;
    }

    public final void setText(String text) {
        if(text == null) text = "";
        this.text = text;
    }
    
    public final void setFontSize(int size) {
        Font oldFont = getFont(this);
        Font newFont = new Font(oldFont.getName(), size, oldFont.getStyle());
        setFont(this, newFont);
    }
}
