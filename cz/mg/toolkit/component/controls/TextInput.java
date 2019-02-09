package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.contents.InteractiveTextContent;
import cz.mg.toolkit.component.wrappers.ScrollArea;
import cz.mg.toolkit.event.adapters.AfterDrawAdapter;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.events.AfterDrawEvent;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.toolkit.utilities.text.Options;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class TextInput extends ScrollArea {
    public static final String DEFAULT_DESIGN_NAME = "text input";
    private final TextContent textContent = new TextContent();
    private boolean needsScrollFix = true;

    public TextInput() {
        initComponents();
        addEventListeners();
    }

    private void initComponents() {
        textContent.setParent(getContentPanel());
        setSizePolicy(textContent, new WrapAndFillSizePolicy());
        textContent.setEditable(true);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(TextInput.this, textContent.hasKeyboardFocus());
            }
        });
        
        getEventListeners().addLast(new AfterDrawAdapter() {
            @Override
            public void onEventEnter(AfterDrawEvent e) {
                if(needsScrollFix) if(fixScroll()) relayout();
                needsScrollFix = false;
            }
        });
        
        textContent.getEventListeners().addFirst(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                needsScrollFix = true;
            }
        });
    }
    
    public TextContent getTextContent(){
        return textContent;
    }

    // needs to be called after relayout has been finished (to use correct measurements)
    private boolean fixScroll(){
        boolean fixed = false;

        double cx = textContent.getTextModel().getTextArrangement().getBeginCaret().getX() + textContent.getX();
        double cy = textContent.getTextModel().getTextArrangement().getBeginCaret().getY() + textContent.getY();
        
        double leftBoundary = 0;
        double rightBoundary = getContentPanel().getWidth();
        double topBoundary = 0;
        double bottomBoundary = getContentPanel().getHeight();
        
        double lineHeight = textContent.getTextModel().getTextArrangement().getLineHeight();
        
        double leftPoint = cx - lineHeight / 2;
        double rightPoint = cx + lineHeight / 2;
        double topPoint = cy;
        double bottomPoint = cy + lineHeight;
        
        if((rightBoundary - leftBoundary) <= lineHeight) return false; // if not enough space
        if((bottomBoundary - topBoundary) <= lineHeight) return false; // if not enough space
        
        if(leftPoint < leftBoundary) {
            double delta = Math.abs(leftPoint - leftBoundary);
            scroll(-delta, 0);
            fixed = true;
        }
        
        if(rightPoint > rightBoundary) {
            double delta = Math.abs(rightPoint - rightBoundary);
            scroll(delta, 0);
            fixed = true;
        }
        
        if(topPoint < topBoundary) {
            double delta = Math.abs(topPoint - topBoundary);
            scroll(0, -delta);
            fixed = true;
        }
        
        if(bottomPoint > bottomBoundary) {
            double delta = Math.abs(bottomPoint - bottomBoundary);
            scroll(0, delta);
            fixed = true;
        }

        return fixed;
    }
    
    public static class TextContent extends InteractiveTextContent {
        public static final String DEFAULT_DESIGN_NAME = "text input content";
        private TextModel placeholderTextModel = new TextModel();

        public TextContent() {
            addEventListeners();
        }
        
        private void addEventListeners(){
            getEventListeners().addFirst(new BeforeDrawAdapter() {
                @Override
                public void onEventEnter(BeforeDrawEvent e) {
                    updatePlaceholderOptions();
                }
            });

            getEventListeners().addLast(new AfterLayoutAdapter() {
                @Override
                public void onEventEnter(AfterLayoutEvent e) {
                    if(placeholderTextModel.getTextArrangement().update()) relayout();
                }
            });
        }
        
        public TextModel getPlaceholderTextModel() {
            return placeholderTextModel;
        }
        
        private void updatePlaceholderOptions(){
            Options options = placeholderTextModel.getTextArrangement().getOptions();
            options.setLeftPadding(getLeftPadding(this));
            options.setRightPadding(getRightPadding(this));
            options.setTopPadding(getTopPadding(this));
            options.setBottomPadding(getBottomPadding(this));
            options.setHorizontalAlignment(getHorizontalContentAlignment(this));
            options.setVerticalAlignment(getVerticalContentAlignment(this));
            options.setVerticalSpacing(getVerticalSpacing(this));
            options.setFont(getFont(this));
            options.setWidth(getWidth());
            options.setHeight(getHeight());
        }
    }
}
