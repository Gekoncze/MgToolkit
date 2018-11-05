package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.controls.buttons.special.UpScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.DownScrollButton;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class VerticalScrollBar extends Container {
    public static final String DEFAULT_DESIGN_NAME = "vertical scroll bar";
    
    private Panel scrollablePanel;
    private final UpScrollButton upButton = new UpScrollButton();
    private final DownScrollButton downButton = new DownScrollButton();
    private final Content draggableBar = new Content();
    private double dragY;
    private double dragBeginScroll;
    
    public VerticalScrollBar() {
        initComponent();
        initComponents();
    }
    
    private void initComponent(){
        setLayout(new VerticalLayout());
        setHorizontalSizePolicy(this, new WrapContentSizePolicy());
        setVerticalSizePolicy(this, new WrapAndFillSizePolicy());
    }

    private void initComponents() {
        upButton.setParent(this);  
        draggableBar.setParent(this);
        downButton.setParent(this);
    }

    public UpScrollButton getUpButton() {
        return upButton;
    }

    public DownScrollButton getDownButton() {
        return downButton;
    }

    public Panel getScrollablePanel() {
        return scrollablePanel;
    }

    public void setScrollablePanel(Panel scrollablePanel) {
        this.scrollablePanel = scrollablePanel;
        upButton.setScrollPanel(scrollablePanel);
        downButton.setScrollPanel(scrollablePanel);
    }
    
    public final class Content extends cz.mg.toolkit.component.Content {
        public static final String DEFAULT_DESIGN_NAME = "vertical scroll bar content";
        
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
