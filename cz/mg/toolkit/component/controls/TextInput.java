package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.InteractiveTextContent;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.toolkit.utilities.text.textmodels.MultiLineTextModel;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.text.Options;


public class TextInput extends Panel {
    public static final String DEFAULT_DESIGN_NAME = "text input";
    private final TextContent textContent = new TextContent();

    public TextInput() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new OverlayLayout());
        setSizePolicy(this, new FillParentSizePolicy());
        setUnlimitedScroll(this, true);
    }

    private void initComponents() {
        textContent.setParent(this);
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
        
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                fixScroll();
            }
        });
    }
    
    public TextContent getTextContent(){
        return textContent;
    }
    
    private void fixScroll(){
        double cx = textContent.getTextModel().getBeginCaret().getX() + textContent.getX();
        double cy = textContent.getTextModel().getBeginCaret().getY() + textContent.getY();
        
        double leftPadding = getLeftPadding(this);
        double rightPadding = getRightPadding(this);
        double topPadding = getTopPadding(this);
        double bottomPadding = getBottomPadding(this);
        
        double leftBoundary = leftPadding;
        double rightBoundary = getWidth() - rightPadding;
        double topBoundary = topPadding;
        double bottomBoundary = getHeight() - bottomPadding;
        
        double lineHeight = textContent.getTextModel().getLineHeight();
        
        double leftPoint = cx;
        double rightPoint = cx;
        double topPoint = cy;
        double bottomPoint = cy + lineHeight;
        
        if((rightBoundary - leftBoundary) <= lineHeight) return; // if not enough space
        if((bottomBoundary - topBoundary) <= lineHeight) return; // if not enough space
        
        if(leftPoint < leftBoundary) {
            double delta = Math.abs(leftPoint - leftBoundary);
            scrollHorizontally(this, -delta);
            relayout();
        }
        
        if(rightPoint > rightBoundary) {
            double delta = Math.abs(rightPoint - rightBoundary);
            scrollHorizontally(this, delta);
            relayout();
        }
        
        if(topPoint < topBoundary) {
            double delta = Math.abs(topPoint - topBoundary);
            scrollVertically(this, -delta);
            relayout();
        }
        
        if(bottomPoint > bottomBoundary) {
            double delta = Math.abs(bottomPoint - bottomBoundary);
            scrollVertically(this, delta);
            relayout();
        }
    }
    
    public static class TextContent extends InteractiveTextContent {
        public static final String DEFAULT_DESIGN_NAME = "text input content";
        private TextModel placeholderTextModel = new MultiLineTextModel();

        public TextContent() {
            addEventListeners();
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
                    if(placeholderTextModel.update()) relayout();
                }
            });
        }
        
        public TextModel getPlaceholderTextModel() {
            return placeholderTextModel;
        }

        public void setPlaceholderTextModel(TextModel placeholderTextModel) {
            this.placeholderTextModel = placeholderTextModel;
        }
        
        private void updateOptions(){
            Options options = placeholderTextModel.getOptions();
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
}
