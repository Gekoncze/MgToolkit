package cz.mg.toolkit.component.controls;

import cz.mg.collections.list.List;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.InteractiveMultilineTextContent;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.StringUtilities;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;


public class MultilineTextInput extends Panel {
    private final TextContent textContent = new TextContent();
    private String placeholderText;
    private List<String> placeholderLines = null;

    public MultilineTextInput() {
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
                setHighlighted(MultilineTextInput.this, textContent.hasKeyboardFocus());
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
                    if(placeholderLines == null) return;
                    g.setTransparency(0.5);
                    g.setColor(getCurrentForegroundColor());
                    g.setFont(getFont(textContent));
                    int i = 0;
                    for(String placeholderLine : placeholderLines){
                        g.drawText(
                                placeholderLine,
                                textContent.getHorizontalLinePosition(i, placeholderLine),
                                textContent.getVerticalLinePosition(i, placeholderLine)
                        );
                        i++;
                    }
                    g.setTransparency(1.0);
                }
            }
        });
    }
    
    private void fixScroll(){
        double[] cp = textContent.getCaretPosition();
        cp[0] += textContent.getX();
        cp[1] += textContent.getY();
        
        double leftPadding = getLeftPadding(this);
        double rightPadding = getRightPadding(this);
        double topPadding = getTopPadding(this);
        double bottomPadding = getBottomPadding(this);
        
        double leftBoundary = leftPadding;
        double rightBoundary = getWidth() - rightPadding;
        double topBoundary = topPadding;
        double bottomBoundary = getHeight() - bottomPadding;
        
        double leftPoint = cp[0];
        double rightPoint = cp[0];
        double topPoint = cp[1];
        double bottomPoint = cp[1] + textContent.getLineHeight();
        
        if((rightBoundary - leftBoundary) <= textContent.getLineHeight()) return; // not enough space
        if((bottomBoundary - topBoundary) <= textContent.getLineHeight()) return; // not enough space
        
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

    public final String getPlaceholderText() {
        return placeholderText;
    }

    public final void setPlaceholderText(String placeholderText) {
        this.placeholderText = placeholderText;
        this.placeholderLines = StringUtilities.splitLines(placeholderText);
    }

    public final InteractiveMultilineTextContent getTextContent() {
        return textContent;
    }
    
    private static void scrollHorizontally(Component component, double value){
        setHorizontalScroll(component, getHorizontalScroll(component) + value);
    }
    
    private static void scrollVertically(Component component, double value){
        setVerticalScroll(component, getVerticalScroll(component) + value);
    }
    
    public static class TextContent extends InteractiveMultilineTextContent {
    }
}
