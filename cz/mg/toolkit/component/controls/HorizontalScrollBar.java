package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.controls.buttons.special.LeftScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.RightScrollButton;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class HorizontalScrollBar extends Container {
    public static final String DEFAULT_DESIGN_NAME = "horizontal scroll bar";
    
    private Panel scrollablePanel;
    private final LeftScrollButton leftButton = new LeftScrollButton();
    private final RightScrollButton rightButton = new RightScrollButton();
    private final Content draggableBar = new Content();
    private double dragX;
    private double dragBeginScroll;
    
    public HorizontalScrollBar() {
        initComponent();
        initComponents();
    }
    
    private void initComponent() {
        setLayout(new HorizontalLayout());
        setHorizontalSizePolicy(this, new WrapAndFillSizePolicy());
        setVerticalSizePolicy(this, new WrapContentSizePolicy());
    }
    
    private void initComponents() {
        leftButton.setParent(this);
        draggableBar.setParent(this);
        rightButton.setParent(this);
    }

    public LeftScrollButton getLeftButton() {
        return leftButton;
    }

    public RightScrollButton getRightButton() {
        return rightButton;
    }
    
    public Panel getScrollablePanel() {
        return scrollablePanel;
    }

    public void setScrollablePanel(Panel scrollablePanel) {
        this.scrollablePanel = scrollablePanel;
        leftButton.setScrollPanel(scrollablePanel);
        rightButton.setScrollPanel(scrollablePanel);
    }
    
    public final class Content extends cz.mg.toolkit.component.Content {
        public static final String DEFAULT_DESIGN_NAME = "horizontal scroll bar content";
        
        public Content() {
            initComponent();
            addEventListeners();
        }
        
        public Panel getScrollablePanel() {
            return scrollablePanel;
        }
        
        private void initComponent(){
            setSizePolicy(this, new FillParentSizePolicy());
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventLeave(MouseButtonEvent e) {
                    if(wasLeftButton(e) && wasPressed(e)){
                        requestMouseFocus();
                        dragX = getX(e);
                        dragBeginScroll = getHorizontalScroll(scrollablePanel);
                        e.consume();
                    }
                }
            });
            
            getEventListeners().addLast(new MouseButtonAdapter() {
                @Override
                public void onMouseButtonEventEnter(MouseButtonEvent e) {
                    if(wasLeftButton(e) && wasReleased(e) && hasMouseFocus()){
                        releaseMouseFocus();
                    }
                }
            });
            
            getEventListeners().addLast(new MouseMotionAdapter() {
                @Override
                public void onMouseMotionEventEnter(MouseMotionEvent e) {
                    if(hasMouseFocus()){
                        double hp = 3; // horizontal padding
                        double aw = getWidth()- 2*hp; // available width
                        double minScroll = scrollablePanel.getMinHorizontalScroll();
                        double maxScroll = scrollablePanel.getMaxHorizontalScroll();
                        double ds = maxScroll - minScroll;
                        double k = scrollablePanel.getWidth()/ scrollablePanel.getContentWidth();
                        double w =  (aw * k);
                        double fw = aw - w;
                        if(fw <= 0) return;
                        
                        double dpx = getX(e) - dragX;
                        double dcs =  (ds*(dpx / fw));
                        setHorizontalScroll(scrollablePanel, dragBeginScroll + dcs);
                        relayout();
                    }
                }
            });
        }
    }
}
