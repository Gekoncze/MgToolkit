package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;
import cz.mg.toolkit.utilities.text.Options;
import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.toolkit.utilities.text.textmodels.MultiLineTextModel;


public class TextContent extends Content {
    public static final String DEFAULT_DESIGN_NAME = "text content";
    private TextModel textModel = new MultiLineTextModel();

    public TextContent() {
        addEventListeners();
    }
    
    public TextContent(String text) {
        addEventListeners();
        setText(text);
    }
    
    private void addEventListeners(){
        getEventListeners().addFirst(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                updateOptions();
            }
        });
        
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                updateOptions();
                if(textModel.update()) relayout();
            }
        });
    }
    
    public final String getText(){
        return textModel.getText();
    }
    
    public final void setText(String text){
        textModel.setText(text);
    }

    public final TextModel getTextModel() {
        return textModel;
    }

    public final void setTextModel(TextModel textModel) {
        this.textModel = textModel;
    }

    @Override
    public final double getPrefferedWidth() {
        return getTextWidth() + getLeftPadding(this) + getRightPadding(this);
    }

    @Override
    public final double getPrefferedHeight() {
        return getTextHeight() + getTopPadding(this) + getBottomPadding(this);
    }
    
    private double getTextWidth(){
        return textModel == null ? 0 : textModel.getTextWidth();
    }
    
    private double getTextHeight(){
        return textModel == null ? 0 : textModel.getTextHeight();
    }
    
    private void updateOptions(){
        Options options = textModel.getOptions();
        options.setLeftPadding(getLeftPadding(this));
        options.setRightPadding(getRightPadding(this));
        options.setTopPadding(getTopPadding(this));
        options.setBottomPadding(getBottomPadding(this));
        options.setHorizontalAlignment(getHorizontalContentAlignment(this));
        options.setVerticalAlignment(getVerticalContentAlignment(this));
        options.setFont(getFont(this));
        options.setWidth(getWidth());
        options.setHeight(getHeight());
    }
}
