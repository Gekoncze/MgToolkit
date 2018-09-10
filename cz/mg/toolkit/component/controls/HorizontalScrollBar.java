package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.controls.buttons.special.HorizontalScrollButtonLeft;
import cz.mg.toolkit.component.controls.buttons.special.HorizontalScrollButtonRight;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.DrawableContainer;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalScrollBar extends DrawableContainer {
    private Container scrollablePanel;
    private final HorizontalScrollButtonLeft leftButton = new HorizontalScrollButtonLeft();
    private final HorizontalScrollButtonRight rightButton = new HorizontalScrollButtonRight();
    private final DraggableBar draggableBar = new DraggableBar();
    private double dragX;
    private double dragBeginScroll;
    
    public HorizontalScrollBar() {
        initComponent();
        initComponents();
    }
    
    private void initComponent() {
        setLayout(new HorizontalLayout());
        setWrapAndFillWidth(this);
        setWrapContentHeight(this);
    }
    
    private void initComponents() {
        leftButton.setParent(this);
        draggableBar.setParent(this);
        rightButton.setParent(this);
    }

    public HorizontalScrollButtonLeft getLeftButton() {
        return leftButton;
    }

    public HorizontalScrollButtonRight getRightButton() {
        return rightButton;
    }
    
    public Container getScrollablePanel() {
        return scrollablePanel;
    }

    public void setScrollablePanel(Container scrollablePanel) {
        this.scrollablePanel = scrollablePanel;
        leftButton.setScrollPanel(scrollablePanel);
        rightButton.setScrollPanel(scrollablePanel);
    }
    
    private final class DraggableBar extends DrawableContent {
        public DraggableBar() {
            initComponent();
            addEventListeners();
        }
        
        private void initComponent(){
            setFillParentWidth(this);
            setFillParentHeight(this);
            setBorder(this, null);
        }
        
        private void addEventListeners(){
            getEventListeners().addLast(new GraphicsDrawAdapter() {
                @Override
                public void onDrawEventLeave(Graphics g) {
                    if(scrollablePanel.getContentWidth() <= 0) return;
                    
                    g.setColor(getCurrentForegroundColor());
                    
                    double hp = 3; // horizontal padding
                    double vp = 2; // vertical padding
                    double s = 2; // spacing (for |||)
                    double aw = getWidth() - 2*hp; // available width

                    
                    double scroll = getHorizontalScroll(scrollablePanel);
                    double minScroll = scrollablePanel.getMinHorizontalScroll();
                    double maxScroll = scrollablePanel.getMaxHorizontalScroll();
                    double ds = maxScroll - minScroll;
                    
                    if(ds <= 0) return;
                    
                    double k = scrollablePanel.getWidth() / scrollablePanel.getContentWidth();
                    double p = (scroll - minScroll) / (ds);
                    
                    double w =  (aw * k);
                    double h = getHeight() - 2*vp;
                    
                    double fw = aw - w;
                    
                    double x = hp + (fw*p);
                    double y = vp;
                    
                    double rx = x + w/2;
                    
                    if(w > 4*s){
                        double vpp = vp+4;
                        g.drawLine(rx-s, vpp, rx-s, getHeight()-vpp);
                        g.drawLine(rx  , vpp, rx  , getHeight()-vpp);
                        g.drawLine(rx+s, vpp, rx+s, getHeight()-vpp);
                    }
                    
                    g.drawRectangle(x, y, w-1, h-1);
                }
            });
            
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
