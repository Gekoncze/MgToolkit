package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.InteractiveSinglelineTextContent;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SinglelineTextInput extends Panel {
    private final TextContent textContent = new TextContent();
    private String placeholderText;

    public SinglelineTextInput() {
        initComponent();
        initComponents();
        addEventListeners();
    }

    private void initComponent() {
        setLayout(new OverlayLayout());
        setFillParentWidth(this);
        setUnlimitedHorizontalScroll(this, true);
    }

    private void initComponents() {
        textContent.setParent(this);
        setWrapAndFillWidth(textContent);
        textContent.setEditable(true);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(SinglelineTextInput.this, textContent.hasKeyboardFocus());
            }
        });
        
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                fixScroll();
            }
        });
        
        textContent.getEventListeners().addFirst(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                if(!textContent.hasKeyboardFocus() && textContent.getText().length() <= 0){
                    if(placeholderText == null) return;
                    g.setTransparency(0.5);
                    g.setColor(getCurrentForegroundColor());
                    g.setFont(getFont(textContent));
                    g.drawText(placeholderText, textContent.getHorizontalTextPosition(placeholderText), textContent.getVerticalTextPosition(placeholderText));
                    g.setTransparency(1.0);
                }
            }
        });
    }
    
    private void fixScroll(){
        double cp = textContent.getCaretPosition() + textContent.getX();
        
        double leftPadding = getLeftPadding(this);
        double rightPadding = getRightPadding(this);
        
        double leftBoundary = leftPadding;
        double rightBoundary = getWidth() - rightPadding;
        
        if((rightBoundary - leftBoundary) <= textContent.getLineHeight()) return; // not enough space
        
        if(cp < leftBoundary) {
            double delta = Math.abs(cp - leftBoundary);
            scrollHorizontally(this, -delta);
            relayout();
        }
        
        if(cp > rightBoundary){
            double delta = Math.abs(cp - rightBoundary);
            scrollHorizontally(this, delta);
            relayout();
        }
    }

    public final String getPlaceholderText() {
        return placeholderText;
    }

    public final void setPlaceholderText(String placeholderText) {
        this.placeholderText = placeholderText;
    }

    public final InteractiveSinglelineTextContent getTextContent() {
        return textContent;
    }
    
    private static void scrollHorizontally(Component component, double value){
        setHorizontalScroll(component, getHorizontalScroll(component) + value);
    }
    
    public static class TextContent extends InteractiveSinglelineTextContent {
    }
}
