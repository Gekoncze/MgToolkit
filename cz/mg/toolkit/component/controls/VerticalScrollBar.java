package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.controls.buttons.special.VerticalScrollButtonUp;
import cz.mg.toolkit.component.controls.buttons.special.VerticalScrollButtonDown;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.DrawableContainer;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalScrollBar extends DrawableContainer {
    private Container scrollablePanel;
    private final VerticalScrollButtonUp upButton = new VerticalScrollButtonUp();
    private final VerticalScrollButtonDown downButton = new VerticalScrollButtonDown();
    private final DraggableBar draggableBar = new DraggableBar();
    private double dragY;
    private double dragBeginScroll;
    
    public VerticalScrollBar() {
        initComponent();
        initComponents();
    }
    
    private void initComponent(){
        setLayout(new VerticalLayout());
        setWrapContentWidth(this);
        setWrapAndFillHeight(this);
    }

    private void initComponents() {
        upButton.setParent(this);  
        draggableBar.setParent(this);
        downButton.setParent(this);
    }

    public VerticalScrollButtonUp getUpButton() {
        return upButton;
    }

    public VerticalScrollButtonDown getDownButton() {
        return downButton;
    }

    public Container getScrollablePanel() {
        return scrollablePanel;
    }

    public void setScrollablePanel(Container scrollablePanel) {
        this.scrollablePanel = scrollablePanel;
        upButton.setScrollPanel(scrollablePanel);
        downButton.setScrollPanel(scrollablePanel);
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
                    if(scrollablePanel.getContentHeight() <= 0) return;
                    
                    g.setColor(getCurrentForegroundColor());
                    
                    double hp = 3; // horizontal padding
                    double vp = 2; // vertical padding
                    double s = 2; // spacing (for |||)
                    double ah = getHeight() - 2*vp; // available height

                    
                    double scroll = getVerticalScroll(scrollablePanel);
                    double minScroll = scrollablePanel.getMinVerticalScroll();
                    double maxScroll = scrollablePanel.getMaxVerticalScroll();
                    double ds = maxScroll - minScroll;
                    
                    if(ds <= 0) return;
                    
                    double k = scrollablePanel.getHeight() / scrollablePanel.getContentHeight();
                    double p = (scroll - minScroll) / (ds);
                    
                    double w = getWidth() - 2*hp;
                    double h =  (ah * k);
                    
                    double fh = ah - h;
                    
                    double x = hp;
                    double y = vp + (fh*p);
                    
                    double ry = y + h/2;
                    
                    if(h > 4*s){
                        double hpp = hp+4;
                        g.drawLine(hpp, ry-s, getWidth()-hpp, ry-s);
                        g.drawLine(hpp, ry,    getWidth()-hpp, ry);
                        g.drawLine(hpp, ry+s, getWidth()-hpp, ry+s);
                    }
                    
                    g.drawRectangle(x, y, w-1, h-1);
                }
            });
            
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventLeave(MouseButtonEvent e) {
                    if(wasLeftButton(e) && wasPressed(e)){
                        requestMouseFocus();
                        dragY = getY(e);
                        dragBeginScroll = getVerticalScroll(scrollablePanel);
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
                        double vp = 2; // vertical padding
                        double ah = getHeight() - 2*vp; // available height
                        double minScroll = scrollablePanel.getMinVerticalScroll();
                        double maxScroll = scrollablePanel.getMaxVerticalScroll();
                        double ds = maxScroll - minScroll;
                        double k = scrollablePanel.getHeight() / scrollablePanel.getContentHeight();
                        double h =  (ah * k);
                        double fh = ah - h;
                        if(fh <= 0) return;
                        
                        double dpy = getY(e) - dragY;
                        double dcs =  (ds*(dpy / fh));
                        setVerticalScroll(scrollablePanel, dragBeginScroll + dcs);
                        relayout();
                    }
                }
            });
        }
    }
}
