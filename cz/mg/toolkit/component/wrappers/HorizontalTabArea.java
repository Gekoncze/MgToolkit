package cz.mg.toolkit.component.wrappers;

import cz.mg.collections.list.List;
import cz.mg.collections.list.quicklist.QuickList;
import cz.mg.toolkit.component.Button;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.ContentPanel;
import cz.mg.toolkit.component.Panel;
import cz.mg.toolkit.component.buttons.CloseButton;
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


public class HorizontalTabArea extends Panel {
    private final CompactHorizontalScrollArea tabsArea = new CompactHorizontalScrollArea();
    private final Panel contentPanel = new ContentPanel();
    private final List<Tab> tabs = new QuickList<>();

    public HorizontalTabArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }

    private void initComponent() {
        setLayout(new VerticalLayout());
        setFillParent(this);
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
        else tab.getHeader().getIcon().setHidden(true);
        
        if(text != null) tab.getHeader().getText().setText(text);
        else tab.getHeader().getText().setHidden(true);
        
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
            t.getHeader().setHighlighted(false);
            t.getContentPanel().setHidden(true);
            for(Component c : t.getHeader().getChildren()) c.setHighlighted(false);
        }
        tab.getHeader().setHighlighted(true);
        tab.getContentPanel().setHidden(false);
        for(Component c : tab.getHeader().getChildren()) c.setHighlighted(true);
    }
    
    public class Tab {
        private final HorizontalTabHeader header = new HorizontalTabHeader();
        private final Panel contentPanel = new ContentPanel();
        private long lastActivationTime = Timer.getCurrentTimeInMilliseconds();

        public final HorizontalTabHeader getHeader() {
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
        
        public class HorizontalTabHeader extends Button {
            private final ImageContent icon = new ImageContent();
            private final TextContent text = new TextContent();
            private final CloseButton closeButton = new CloseButton();

            public HorizontalTabHeader() {
                initComponent();
                initComponents();
                addEventListeners();
            }

            private void initComponent(){
                setLayout(new HorizontalLayout());
                setPadding(this, 4);
                setActionInvocation(Button.ActionInvocation.EVENT_LEAVE);
                setVerticalAlignment(this, 1.0);
                setHorizontalSpacing(this, 4);
            }

            private void initComponents(){
                icon.setParent(this);
                text.setParent(this);
                closeButton.setParent(this);
                closeButton.setWrapSize(16, 16, 4);
                icon.setContentSize(16, 16);
                icon.setUsePrefferedSize(false);
            }

            private void addEventListeners() {
                getEventListeners().addLast(new ActionAdapter() {
                    @Override
                    public void onEventEnter(ActionEvent e) {
                        if(e.getSource() == HorizontalTabHeader.this){
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
}
