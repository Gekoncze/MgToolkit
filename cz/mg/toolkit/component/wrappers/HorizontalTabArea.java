package cz.mg.toolkit.component.wrappers;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.ContentPanel;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.TabCloseAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.TabCloseEvent;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.utilities.Timer;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.SameAsHeightSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class HorizontalTabArea extends Panel {
    public static final String DEFAULT_DESIGN_NAME = "horizontal tab area";
    
    private final CompactHorizontalScrollArea tabsArea = new CompactHorizontalScrollArea();
    private final Panel contentPanel = new ContentPanel();
    private final List<Tab> tabs = new CachedChainList<>();

    public HorizontalTabArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }

    private void initComponent() {
        setLayout(new VerticalLayout());
        setSizePolicy(this, new FillParentSizePolicy());
    }

    private void initComponents() {
        tabsArea.setParent(this);
        contentPanel.setParent(this);
        tabsArea.getContentPanel().setLayout(new HorizontalLayout());
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new TabCloseAdapter() {
            @Override
            public void onEventLeave(TabCloseEvent e) {
                closeTabImmediately(e.getTab());
            }
        });
    }
    
    public void updateComponents(){
        tabsArea.getContentPanel().getChildren().clear();
        contentPanel.getChildren().clear();
        for(Tab tab : tabs){
            tabsArea.getContentPanel().getChildren().addLast(tab.getHeader());
            contentPanel.getChildren().addLast(tab.getContentPanel());
        }
    }
    
    public final List<Tab> getTabs(){
        return tabs;
    }
    
    public final Tab openTab(Image icon, String text){
        Tab tab = new Tab();
        
        if(icon != null) tab.getHeader().getIcon().setImage(icon);
        else setHidden(tab.getHeader().getIcon(), true);
        
        if(text != null) tab.getHeader().getText().setText(text);
        else setHidden(tab.getHeader().getText(), true);
        
        tabs.addLast(tab);
        updateComponents();
        activateTab(tab);
        return tab;
    }
    
    public final void closeTab(Tab tab){
        sendEvent(new TabCloseEvent(tab));
    }
    
    public final void closeTabImmediately(Tab tab){
        if(tabs.remove(tab) != null) updateComponents();
        activateLastActiveTab();
    }
    
    private void activateLastActiveTab(){
        Tab lastActiveTab = null;
        for(Tab tab : tabs){
            if(lastActiveTab == null) lastActiveTab = tab;
            else if(tab.lastActivationTime > lastActiveTab.lastActivationTime) lastActiveTab = tab;
        }
        if(lastActiveTab != null) activateTab(lastActiveTab);
    }
    
    public final void activateTab(Tab tab){
        tab.lastActivationTime = Timer.getCurrentTimeInMilliseconds();
        for(Tab t : tabs){
            setHighlighted(t.getHeader(), false);
            setHidden(t.getContentPanel(), true);
            for(Component c : t.getHeader().getChildren()) setHighlighted(c, false);
        }
        setHighlighted(tab.getHeader(), true);
        setHidden(tab.getContentPanel(), false);
        for(Component c : tab.getHeader().getChildren()) setHighlighted(c, true);
    }
    
    public class Tab {
        private final TabHeader header = new TabHeader();
        private final Panel contentPanel = new ContentPanel();
        private long lastActivationTime = Timer.getCurrentTimeInMilliseconds();

        public final TabHeader getHeader() {
            return header;
        }
        
        public final Panel getContentPanel(){
            return contentPanel;
        }

        public long getLastActivationTime() {
            return lastActivationTime;
        }
        
        public final void close(){
            closeTab(this);
        }
        
        public final void closeImmediately(){
            closeTabImmediately(this);
        }
        
        public final void activate(){
            activateTab(this);
        }
        
        public class TabHeader extends Button {
            public static final String DEFAULT_DESIGN_NAME = "horizontal tab area header";
            
            private final Icon icon = new Icon();
            private final Text text = new Text();
            private final CloseButton closeButton = new CloseButton();

            public TabHeader() {
                initComponent();
                initComponents();
                addEventListeners();
            }

            private void initComponent(){
                setLayout(new HorizontalLayout());
                setActionInvocation(Button.ActionInvocation.EVENT_LEAVE);
                setVerticalAlignment(this, 1.0);
                setHorizontalSizePolicy(this, new WrapContentSizePolicy());
                setVerticalSizePolicy(this, new FixedSizePolicy());
            }

            private void initComponents(){
                icon.setParent(this);
                text.setParent(this);
                closeButton.setParent(this);
                
                setHorizontalSizePolicy(icon, new SameAsHeightSizePolicy());
                setHorizontalSizePolicy(text, new WrapContentSizePolicy());
                setHorizontalSizePolicy(closeButton, new SameAsHeightSizePolicy());
                
                setVerticalSizePolicy(icon, new FillParentSizePolicy());
                setVerticalSizePolicy(text, new FillParentSizePolicy());
                setVerticalSizePolicy(closeButton, new FillParentSizePolicy());
            }

            private void addEventListeners() {
                getEventListeners().addLast(new ActionAdapter() {
                    @Override
                    public void onEventEnter(ActionEvent e) {
                        if(e.getSource() == TabHeader.this){
                            activate();
                            HorizontalTabArea.this.relayout();
                        }
                    }
                });
                
                closeButton.getEventListeners().addLast(new ActionAdapter() {
                    @Override
                    public void onEventEnter(ActionEvent e) {
                        if(e.getSource() == closeButton){
                            close();
                            HorizontalTabArea.this.relayout();
                        }
                    }
                });
            }

            public final ImageContent getIcon() {
                return icon;
            }

            public final TextContent getText() {
                return text;
            }
        }
    }
    
    public static class Icon extends ImageContent {
        public static final String DEFAULT_DESIGN_NAME = "horizontal tab area icon";
    }
    
    public static class Text extends TextContent {
        public static final String DEFAULT_DESIGN_NAME = "horizontal tab area text";
    }
    
    public static class CloseButton extends cz.mg.toolkit.component.controls.buttons.special.CloseButton {
        public static final String DEFAULT_DESIGN_NAME = "horizontal tab area close button";
    }
}
