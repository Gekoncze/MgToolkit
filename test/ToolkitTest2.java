package test;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.InteractiveTextContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.component.contents.VerticalSpacer;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.component.wrappers.HorizontalFlowArea;
import cz.mg.toolkit.component.wrappers.HorizontalScrollArea;
import cz.mg.toolkit.component.wrappers.HorizontalTabArea;
import cz.mg.toolkit.component.wrappers.SplitArea;
import cz.mg.toolkit.component.wrappers.VerticalFlowArea;
import cz.mg.toolkit.component.wrappers.VerticalScrollArea;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.utilities.Debug;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.adapters.TabCloseAdapter;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.event.events.TabCloseEvent;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import java.io.IOException;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class ToolkitTest2 {
    private static boolean DEBUG = false;
    
    public static void main(String[] args) throws IOException {
        Impl.setImplApi(new SwingImplApi());
        Impl.getImplApi().getPrimaryDisplay().setHorizontalZoom(0.25);
        Impl.getImplApi().getPrimaryDisplay().setVerticalZoom(0.25);
        
        Window window = new Window();
        window.setSize(200*4, 150*4);
        window.setTitle("Yay!");
        window.setIcon(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        window.center();
        
        window.getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!wasButtonPressed(e)) return;
                if(e.getLogicalButton() == Keyboard.Button.F1) window.setDecorated(!window.isDecorated());
                if(e.getLogicalButton() == Keyboard.Button.F2) window.setDecoration(new SystemDecoration());
                if(e.getLogicalButton() == Keyboard.Button.F3) window.setDecoration(new ToolkitDecoration());
                if(e.getLogicalButton() == Keyboard.Button.F4) ;
                if(e.getLogicalButton() == Keyboard.Button.F5) ;
                if(e.getLogicalButton() == Keyboard.Button.F6) ;
                if(e.getLogicalButton() == Keyboard.Button.F7) ;
                if(e.getLogicalButton() == Keyboard.Button.F8) window.setMinimized(true);
                if(e.getLogicalButton() == Keyboard.Button.F9) window.setMaximized(!window.isMaximized());
                if(e.getLogicalButton() == Keyboard.Button.F10) setDebug(window);
                window.relayout();
            }
        });
        
        Container windowPanel = window.getContentPanel();
//        windowPanel.setLayout(new VerticalLayout());
//        windowPanel.getHints().set(SPACING, Dimension.Y, 4);
//        setPadding(windowPanel, 4);
        
        HorizontalTabArea tabs = new HorizontalTabArea();
        tabs.setParent(windowPanel);
        tabs.openTab(new BitmapImage(ToolkitTest2.class.getResourceAsStream("rainbowdash.png")), "Rainbow dash");
        tabs.openTab(null, "Yay!");
        tabs.openTab(null, "Multiline text content test!");
        for(int i = 0; i < 5; i++) tabs.openTab(null, "tabbbbbbbbbbbbbbbbbb " + i);
        tabs.updateComponents();
        tabs.getTabs().getFirst().activate();
        
        tabs.getEventListeners().addLast(new TabCloseAdapter() {
            @Override
            public void onEventEnter(TabCloseEvent e) {
                if(!isHidden(e.getTab().getHeader().getIcon())){
                    System.out.println("Cannot close Rainbow Dash!");
                    e.consume();
                }
            }
        });
        
        SplitArea splitArea = new SplitArea();
        splitArea.setColumnCount(3);
        splitArea.setRowCount(3);
//        splitArea.setParent(windowPanel);
        splitArea.setParent(tabs.getTabs().getFirst().getContentPanel());
        
        HorizontalFlowArea hfa = new HorizontalFlowArea();
        VerticalFlowArea vfa = new VerticalFlowArea();
        
//        setSpacing(hfa.getContentPanel(), 8);
//        setPadding(hfa.getContentPanel(), 4);
//        setSpacing(vfa.getContentPanel(), 8);
//        setPadding(vfa.getContentPanel(), 4);
        
        for(int i = 0; i < 100; i++){
            hfa.getContentPanel().getChildren().addLast(new TextContent("Yay " + i));
        }
        
        for(int i = 0; i < 100; i++){
            vfa.getContentPanel().getChildren().addLast(new TextContent("Yay " + i));
        }
        
        splitArea.getContentPanels().get(0, 0).getChildren().addLast(hfa);
        splitArea.getContentPanels().get(1, 0).getChildren().addLast(vfa);
        
        HorizontalScrollArea hs = new HorizontalScrollArea();
        VerticalScrollArea vs = new VerticalScrollArea();
        
        hs.getContentPanel().setLayout(new HorizontalLayout());
//        setHorizontalSpacing(hs.getContentPanel(), 4);
//        setPadding(hs.getContentPanel(), 4);
        vs.getContentPanel().setLayout(new VerticalLayout());
//        setVerticalSpacing(vs.getContentPanel(), 4);
//        setPadding(vs.getContentPanel(), 4);
        
        for(int i = 0; i < 20; i++){
            hs.getContentPanel().getChildren().addLast(new TextContent("Yay " + i));
        }
        
        for(int i = 0; i < 20; i++){
            vs.getContentPanel().getChildren().addLast(new TextContent("Yay " + i));
        }
        
        splitArea.getContentPanels().get(0, 1).getChildren().addLast(hs);
        splitArea.getContentPanels().get(1, 1).getChildren().addLast(vs);
        
        Panel secondTab = tabs.getTabs().get(1).getContentPanel();
        secondTab.setLayout(new VerticalLayout());
        tabs.getTabs().get(1).activate();
        
        InteractiveTextContent eyyup = new InteractiveTextContent("Eyyyuuup! VA");
        setContentAlignment(eyyup, 0.5);
        setSizePolicy(eyyup, new FillParentSizePolicy());
        eyyup.setParent(secondTab);
        eyyup.setEditable(true);
        
        VerticalSpacer space = new VerticalSpacer();
        space.setParent(secondTab);
        
//        Panel mtct = tabs.getTabs().get(2).getContentPanel();
//        mtct.setLayout(new VerticalLayout());
//        mtct.getChildren().addLast(new MultilineTextContent(""));
        
        window.open();
        window.relayout();
    }
    
    public static void setDebug(Window window){
        if(!DEBUG) Debug.setIds(window);
        if(!DEBUG) Debug.setMouseButtonPrintInfo(window);
        Debug.sendRandomColors(window);
        Debug.printComponentInfo(window);
        window.redraw();
        DEBUG = true;
    }
}
