package test;

import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.component.wrappers.ScrollArea;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.controls.MenuItem;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.buttons.ImageButton;
import cz.mg.toolkit.component.controls.buttons.TextButton;
import cz.mg.toolkit.component.wrappers.CompactHorizontalScrollArea;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.controls.TextInput;
import cz.mg.toolkit.component.contents.MultilineTextContent;
import cz.mg.toolkit.component.controls.menuitems.SeparatorItem;
import cz.mg.toolkit.component.controls.menuitems.StandardMenuItem;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.component.window.ContextMenu;
import cz.mg.toolkit.debug.Debug;
import cz.mg.toolkit.environment.device.devices.Display;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.graphics.designers.DefaultDesigner;
import cz.mg.toolkit.layout.layouts.GridLayout;
import cz.mg.toolkit.layout.layouts.GridLayout.Column;
import cz.mg.toolkit.utilities.SelectionGroup;
import cz.mg.toolkit.utilities.keyboardshortcuts.StandardKeyboardCharacterShortcut;
import java.io.IOException;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ToolkitTest {
    private static boolean DEBUG = false;
    
    public static void main(String[] args) throws IOException {
        TextContent label;
        SelectionGroup selectionGroup = new SelectionGroup();
        
        Display.getInstance().setHorizontalZoom(0.25);
        Display.getInstance().setVerticalZoom(0.25);
        
        MenuItem m1 = new StandardMenuItem(new Image(ToolkitTest2.class.getResourceAsStream("mg.png")), "yay", new StandardKeyboardCharacterShortcut(true, false, false, 's'), null, null);
        MenuItem m2 = new StandardMenuItem(null, "nayyyyyyyyyyyyyyyyyyyyyy", null, true, null);
        MenuItem m3 = new SeparatorItem();
        MenuItem m4 = new StandardMenuItem(null, "option 1", null, false, selectionGroup);
        MenuItem m5 = new StandardMenuItem(null, "option 2", null, false, selectionGroup);
        MenuItem m6 = new StandardMenuItem(null, "option 3", null, false, selectionGroup);
        
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getMenu().getItems().addLast(m1);
        contextMenu.getMenu().getItems().addLast(m2);
        contextMenu.getMenu().getItems().addLast(m3);
        contextMenu.getMenu().getItems().addLast(m4);
        contextMenu.getMenu().getItems().addLast(m5);
        contextMenu.getMenu().getItems().addLast(m6);
        contextMenu.getMenu().updateComponents();
        
        contextMenu.wrap();
        
        Window window = new Window();
        window.setContentSize(800, 600);
        window.setTitle("Yay!");
        window.setIcon(new Image(ToolkitTest2.class.getResourceAsStream("mg.png")));
        window.center();
        setDesigner(window, new TestDesigner());
        
        window.getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!wasButtonPressed(e)) return;
                if(e.getButton() == Keyboard.F1_BUTTON) {window.setDecorated(!window.isDecorated());}
                if(e.getButton() == Keyboard.F2_BUTTON) window.setDecoration(new SystemDecoration());
                if(e.getButton() == Keyboard.F3_BUTTON) {window.setDecoration(new ToolkitDecoration());}
                if(e.getButton() == Keyboard.F4_BUTTON) ;
                if(e.getButton() == Keyboard.F5_BUTTON) ;
                if(e.getButton() == Keyboard.F6_BUTTON) ;
                if(e.getButton() == Keyboard.F7_BUTTON) window.setActivated(false);
                if(e.getButton() == Keyboard.F8_BUTTON) window.setMinimized(true);
                if(e.getButton() == Keyboard.F9_BUTTON) window.setMaximized(!window.isMaximized());
                if(e.getButton() == Keyboard.F10_BUTTON) {setDebug(window); setDebug(contextMenu);}
                window.relayout();
            }
        });
        
        window.getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(wasPressed(e) && wasRightButton(e)){
                    contextMenu.open();
                    contextMenu.setX(e.getMouse().getScreenX());
                    contextMenu.setY(e.getMouse().getScreenY());
                }
            }
        });
        
        Container windowPanel = window.getContentPanel();
        windowPanel.setLayout(new VerticalLayout());
        setVerticalSpacing(windowPanel, 4);
        setPadding(windowPanel, 4);
        
        CompactHorizontalScrollArea tabsArea = new CompactHorizontalScrollArea();
        tabsArea.setParent(windowPanel);
        
        Container tabs = tabsArea.getContentPanel();
        tabs.setLayout(new HorizontalLayout());
        setPadding(tabs, 8);
        setHorizontalSpacing(tabs, 8);
        
        int n = 5;
        for(int i = 0; i <= n; i++){
            label = new TextContent(i + "" + i + "" + i + "" + i + "" + i);
            setContext(label, "my top label");
            label.setParent(tabs);
            if(i < n){
                tabs.getChildren().addLast(new HorizontalSeparator());
            }
        }
        
        ScrollArea scrollArea = new ScrollArea();
        scrollArea.setParent(windowPanel);
        setHorizontalContentAlignment(scrollArea.getContentPanel(), 0.5);
        
        Panel v1 = new Panel();
        setPadding(v1, 4);
        v1.setLayout(new VerticalLayout());
        v1.setParent(scrollArea.getContentPanel());
        setVerticalSpacing(v1, 4);
        
        Panel h0 = new Panel();
        setPadding(h0, 8);
        h0.setLayout(new HorizontalLayout());
        h0.setParent(v1);
        setHorizontalSpacing(h0, 4);
        setWrapAndFillWidth(h0);
        
        TextButton tb = new TextButton();
        tb.getTextContent().setText("TB");
        tb.setParent(h0);
        tb.setWrapSize(64, 24, 4);
        tb.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Text button!");
            }
        });
        
        ImageButton ib = new ImageButton();
        ib.getImageContent().setImage(new Image(ToolkitTest2.class.getResourceAsStream("mg.png")));
        ib.setParent(h0);
        ib.setWrapSize(64, 24, 4);
        ib.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Image button!");
            }
        });
        
        TextInput ti = new TextInput();
        setLimitedWidth(ti, 0, 160);
        ti.setParent(h0);
        ti.setPlaceholderText("Try typing here!");
        
        TextContent alignmentTest = new TextContent("Yay!", new Font("default", 18, Font.Style.ITALIC));
        alignmentTest.setParent(h0);
        setFillParentWidth(alignmentTest);
        setFillParentHeight(alignmentTest);
        setContentAlignment(alignmentTest, 0.5);
        
        Panel h1 = new Panel();
        setPadding(h1, 8);
        h1.setLayout(new HorizontalLayout());
        h1.setParent(v1);
        setHorizontalSpacing(h1, 8);
        setWrapAndFillWidth(h1);
        
        label = new TextContent("Lorem ipsum 1");
        label.setParent(h1);
        
        label = new TextContent("Lorem ipsum 2");
        label.setParent(h1);
        
        label = new TextContent("Lorem ipsum 3");
        label.setParent(h1);
        
        Panel h2 = new Panel();
        setHorizontalSpacing(h2, 8);
        setPadding(h2, 8);
        h2.setLayout(new HorizontalLayout());
        h2.setParent(v1);
        setHorizontalAlignment(h2, 1.0);
        
        label = new TextContent("Lorem ipsum 4");
        label.setParent(h2);
        
        label = new TextContent("Lorem ipsum 5");
        label.setParent(h2);
        
        label = new TextContent("Lorem ipsum 6");
        label.setParent(h2);
        
        label = new TextContent("Lorem ipsum 7");
        label.getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(!e.wasPressed() || !wasLeftButton(e)) return;
                e.consume();
                System.out.println("Lucky!");
            }
        });
        label.setParent(h2);
        
        Panel h4 = new Panel();
        setPadding(h1, 8);
        setHorizontalSpacing(h4, 8);
        h4.setLayout(new HorizontalLayout());
        h4.setParent(v1);
        
        label = new TextContent("Lorem ipsum 8");
        label.setParent(h4);
        
        label = new TextContent("Lorem ipsum 9999999999999999999999999999999999999999999999999999");
        label.setParent(h4);
        
        MultilineTextContent mtc = new MultilineTextContent("Twilight Sparkle\nRarity\nFluttershy\nRainbow Dash\nApplejack\nPinkie Pie");
        mtc.setParent(v1);
        setHorizontalContentAlignment(mtc, 0.5);
        
        Panel grid = new Panel();
        setPadding(grid, 8);
        GridLayout gridLayout = new GridLayout(3, 35);
        grid.setLayout(gridLayout);
        grid.setParent(v1);
        setVerticalSpacing(grid, 8);
        setWrapAndFillWidth(grid);
        
        for(Column column : gridLayout.getColumns()){
            setFillParentWidth(column);
        }
        
        for(int i = 0; i < 80; i++){
            int x = i % 3;
            int y = i / 3;
            label = new TextContent("Lorem ipsum i " + i);
            label.setParent(grid);
            setHorizontalAlignment(label, 0.5);
            setCell(label, x, y);
            if(i == 42) setHidden(label, true);
            if(i == 43) setHidden(label, true);
            if(i == 44) setHidden(label, true);
        }
        
        window.open();
        window.relayout();
    }
    
//    private static void printAllActiveThreads(){
//        System.out.println("\n");
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        for(Thread t : threadSet) System.out.println("T: " + t);
//        System.out.println("\n");
//    }
//    
//    private static void printAllWindowState(){
//        System.out.println("\n");
//        for(java.awt.Window window : java.awt.Window.getWindows()){
//            System.out.println("" + window.isDisplayable());
//        }
//        System.out.println("\n");
//    }
    
    public static void setDebug(Window window){
        if(!DEBUG) Debug.setIds(window);
        if(!DEBUG) Debug.setMouseButtonPrintInfo(window);
        Debug.sendRandomColors(window);
        window.redraw();
        DEBUG = true;
    }
    
    private static class TestDesigner extends DefaultDesigner {
        private static final Font TOP_LABEL_FONT = new Font("default", 48, Font.Style.REGULAR);
        
        @Override
        public void design(Component component) {
            super.design(component);
            if("my top label".equals(getContext(component))) setFont(component, TOP_LABEL_FONT);
        }
    }
}
