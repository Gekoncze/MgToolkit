package test;

import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.component.wrappers.ScrollArea;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.containers.LayoutPanel;
import cz.mg.toolkit.component.controls.MenuItem;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.buttons.ImageButton;
import cz.mg.toolkit.component.controls.buttons.TextButton;
import cz.mg.toolkit.component.wrappers.CompactHorizontalScrollArea;
import cz.mg.toolkit.component.contents.HorizontalSeparator;
import cz.mg.toolkit.component.contents.InteractiveMultilineTextContent;
import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.controls.ComboBox;
import cz.mg.toolkit.component.controls.SinglelineTextInput;
import cz.mg.toolkit.component.controls.MultilineTextInput;
import cz.mg.toolkit.component.controls.MultipleSelectionList;
import cz.mg.toolkit.component.controls.SingleSelectionList;
import cz.mg.toolkit.component.controls.menuitems.SeparatorItem;
import cz.mg.toolkit.component.controls.menuitems.StandardMenuItem;
import cz.mg.toolkit.component.controls.sliders.horizontal.DoubleHorizontalSlider;
import cz.mg.toolkit.component.controls.sliders.horizontal.FloatHorizontalSlider;
import cz.mg.toolkit.component.controls.sliders.horizontal.IntegerHorizontalSlider;
import cz.mg.toolkit.component.controls.sliders.horizontal.LongHorizontalSlider;
import cz.mg.toolkit.component.controls.sliders.vertical.DoubleVerticalSlider;
import cz.mg.toolkit.component.controls.sliders.vertical.FloatVerticalSlider;
import cz.mg.toolkit.component.controls.sliders.vertical.IntegerVerticalSlider;
import cz.mg.toolkit.component.controls.sliders.vertical.LongVerticalSlider;
import cz.mg.toolkit.component.controls.spinners.DoubleSpinner;
import cz.mg.toolkit.component.controls.spinners.FloatSpinner;
import cz.mg.toolkit.component.controls.spinners.IntegerSpinner;
import cz.mg.toolkit.component.controls.spinners.LongSpinner;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.component.window.ContextMenu;
import cz.mg.toolkit.debug.Debug;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.designers.DefaultDesigner;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.GridLayout;
import cz.mg.toolkit.layout.layouts.GridLayout.Column;
import cz.mg.toolkit.utilities.shapes.OvalShape;
import cz.mg.toolkit.utilities.SelectionGroup;
import cz.mg.toolkit.utilities.keyboardshortcuts.StandardKeyboardCharacterShortcut;
import java.io.IOException;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FixedRangeSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FixedSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class ToolkitTest {
    private static boolean DEBUG = false;
    
    public static void main(String[] args) throws IOException {
        Impl.setImplApi(new SwingImplApi());
        Impl.getImplApi().getPrimaryDisplay().setHorizontalZoom(0.25);
        Impl.getImplApi().getPrimaryDisplay().setVerticalZoom(0.25);
        
        SinglelineTextContent label;
        SelectionGroup selectionGroup = new SelectionGroup();
        
        MenuItem m1 = new StandardMenuItem(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")), "yay", new StandardKeyboardCharacterShortcut(true, false, false, 's'), null, null);
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
        
        Window window = new Window();
        window.setSize(200*4, 150*4);
        window.setTitle("Yay!");
        window.setIcon(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        window.center();
        setDesigner(window, new TestDesigner());
        
        window.getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!wasButtonPressed(e)) return;
                if(e.getButton() == Keyboard.F1_BUTTON) window.setDecorated(!window.isDecorated());
                if(e.getButton() == Keyboard.F2_BUTTON) window.setDecoration(new SystemDecoration());
                if(e.getButton() == Keyboard.F3_BUTTON) { window.setDecoration(new ToolkitDecoration()); }
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
//        setVerticalSpacing(windowPanel, 4);
//        setPadding(windowPanel, 4);
        
        CompactHorizontalScrollArea tabsArea = new CompactHorizontalScrollArea();
        tabsArea.setParent(windowPanel);
        
        Container tabs = tabsArea.getContentPanel();
        tabs.setLayout(new HorizontalLayout());
//        setPadding(tabs, 8);
//        setHorizontalSpacing(tabs, 8);
        
        int n = 5;
        for(int i = 0; i <= n; i++){
            label = new BigTextContent(i + "" + i + "" + i + "" + i + "" + i);
            label.setParent(tabs);
            if(i < n){
                tabs.getChildren().addLast(new HorizontalSeparator());
            }
        }
        
        ScrollArea scrollArea = new ScrollArea();
        scrollArea.setParent(windowPanel);
        setHorizontalContentAlignment(scrollArea.getContentPanel(), 0.5);
        
        PagePanel v1 = new PagePanel();
//        setPadding(v1, 4);
        v1.setLayout(new VerticalLayout());
        v1.setParent(scrollArea.getContentPanel());
//        setVerticalSpacing(v1, 4);
        
        LayoutPanel h0 = new LayoutPanel();
//        setPadding(h0, 8);
        h0.setLayout(new HorizontalLayout());
        h0.setParent(v1);
//        setHorizontalSpacing(h0, 4);
        setHorizontalSizePolicy(h0, new WrapAndFillSizePolicy());
        
        TextButton tb = new TextButton();
        tb.getTextContent().setText("TB");
        tb.setParent(h0);
        setSizePolicy(tb, new FixedSizePolicy(64, 24));
//        setPadding(tb, 4);
        tb.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Text button!");
            }
        });
        
        ImageButton ib = new ImageButton();
        ib.getImageContent().setImage(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        ib.setParent(h0);
        setSizePolicy(ib, new FixedSizePolicy(64, 24));
//        setPadding(ib, 4);
        ib.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Image button!");
            }
        });
        
        SinglelineTextInput ti = new SinglelineTextInput();
        setHorizontalSizePolicy(ti, new FixedRangeSizePolicy(0, 160));
        ti.setParent(h0);
        ti.setPlaceholderText("Try typing here!");
        
        SinglelineTextContent alignmentTest = new SinglelineTextContent("Yay!");
        alignmentTest.setParent(h0);
        setSizePolicy(alignmentTest, new FillParentSizePolicy());
        setContentAlignment(alignmentTest, 0.5);
        
        LayoutPanel h1 = new LayoutPanel();
//        setPadding(h1, 8);
        h1.setLayout(new HorizontalLayout());
        h1.setParent(v1);
//        setHorizontalSpacing(h1, 8);
        setHorizontalSizePolicy(h1, new WrapAndFillSizePolicy());
        
        label = new SinglelineTextContent("Lorem ipsum 1");
        label.setParent(h1);
        
        label = new SinglelineTextContent("Lorem ipsum 2");
        label.setParent(h1);
        
        label = new SinglelineTextContent("Lorem ipsum 3");
        label.setParent(h1);
        
        LayoutPanel h2 = new LayoutPanel();
//        setHorizontalSpacing(h2, 8);
//        setPadding(h2, 8);
        h2.setLayout(new HorizontalLayout());
        h2.setParent(v1);
        setHorizontalAlignment(h2, 1.0);
        
        label = new SinglelineTextContent("Lorem ipsum 4");
        label.setParent(h2);
        
        label = new SinglelineTextContent("Lorem ipsum 5");
        label.setParent(h2);
        
        label = new SinglelineTextContent("Lorem ipsum 6");
        label.setParent(h2);
        
        label = new SinglelineTextContent("Lorem ipsum 7");
        label.getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(!e.wasPressed() || !wasLeftButton(e)) return;
                e.consume();
                System.out.println("Lucky!");
            }
        });
        label.setParent(h2);
        
        LayoutPanel h4 = new LayoutPanel();
//        setPadding(h1, 8);
//        setHorizontalSpacing(h4, 8);
        h4.setLayout(new HorizontalLayout());
        h4.setParent(v1);
        
        label = new SinglelineTextContent("Lorem ipsum 8");
        label.setParent(h4);
        
        label = new SinglelineTextContent("Lorem ipsum 9999999999999999999999999999999999999999999999999999");
        label.setParent(h4);
        
        ComboBox<Pony> boxOfPonies = new ComboBox<>();
        boxOfPonies.setItems(PONIES);
        boxOfPonies.setParent(v1);
        boxOfPonies.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Your favourite pony in a box is " + boxOfPonies.getSelectedItem());
            }
        });
        
        OvalButton ovalButton = new OvalButton();
        setShape(ovalButton, new OvalShape());
        setSizePolicy(ovalButton, new FixedSizePolicy(64*2, 32*2));
        ovalButton.setParent(v1);
        setForeground(ovalButton, null);
        
        SingleSelectionList<Pony> listOfPonies = new SingleSelectionList<>();
        listOfPonies.setItems(PONIES);
        listOfPonies.setParent(v1);
        listOfPonies.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Your favourite pony in a list is " + listOfPonies.getSelectedItem());
            }
        });
        
        MultipleSelectionList<Pony> anotherListOfPonies = new MultipleSelectionList<>();
        anotherListOfPonies.setItems(PONIES);
        anotherListOfPonies.setParent(v1);
        anotherListOfPonies.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Your favurite ponies in a list are " + anotherListOfPonies.getSelectedItems().toString(", "));
            }
        });
        
        IntegerSpinner integerSpinner = new IntegerSpinner();
        integerSpinner.setParent(v1);
        integerSpinner.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Integer: " + integerSpinner.getValue() + " ;; " + e.getSource().getClass().getSimpleName());
            }
        });
        
        LongSpinner longSpinner = new LongSpinner();
        longSpinner.setParent(v1);
        longSpinner.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Long: " + longSpinner.getValue());
            }
        });
        
        FloatSpinner floatSpinner = new FloatSpinner();
        floatSpinner.setParent(v1);
        floatSpinner.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Float: " + floatSpinner.getValue());
            }
        });
        
        DoubleSpinner doubleSpinner = new DoubleSpinner();
        doubleSpinner.setParent(v1);
        doubleSpinner.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Double: " + doubleSpinner.getValue());
            }
        });
        
        IntegerHorizontalSlider ihs = new IntegerHorizontalSlider(0, 10, -10);
        ihs.setParent(v1);
        ihs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Sl-Integer: " + ihs.getValue());
            }
        });
        
        LongHorizontalSlider lhs = new LongHorizontalSlider(0L, 10L, -10L);
        lhs.setParent(v1);
        lhs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Sl-Long: " + lhs.getValue());
            }
        });
        
        FloatHorizontalSlider fhs = new FloatHorizontalSlider(0.0f, -10.0f, 10.0f);
        fhs.setParent(v1);
        fhs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Sl-Float: " + fhs.getValue());
            }
        });
        
        DoubleHorizontalSlider dhs = new DoubleHorizontalSlider(0.0, -10.0, 10.0);
        dhs.setParent(v1);
        dhs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Sl-Double: " + dhs.getValue());
            }
        });
        
        LayoutPanel vvp = new LayoutPanel();
        setHorizontalSizePolicy(vvp, new WrapContentSizePolicy());
        setVerticalSizePolicy(vvp, new FixedSizePolicy(256));
        vvp.setLayout(new HorizontalLayout());
        vvp.setParent(v1);
        
        IntegerVerticalSlider ivs = new IntegerVerticalSlider(0, -10, 10);
        ivs.setParent(vvp);
        ivs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Slv-Integer: " + ivs.getValue());
            }
        });
        
        LongVerticalSlider lvs = new LongVerticalSlider(0L, -10L, 10L);
        lvs.setParent(vvp);
        lvs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Slv-Long: " + lvs.getValue());
            }
        });
        
        FloatVerticalSlider fvs = new FloatVerticalSlider(0.0f, 10.0f, -10.0f);
        fvs.setParent(vvp);
        fvs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Slv-Float: " + fvs.getValue());
            }
        });
        
        DoubleVerticalSlider dvs = new DoubleVerticalSlider(0.0, 10.0, -10.0);
        dvs.setParent(vvp);
        dvs.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                System.out.println("Slv-Double: " + dvs.getValue());
            }
        });
        
        MultilineTextInput mti = new MultilineTextInput();
        setSizePolicy(mti, new FixedSizePolicy(128, 64));
        mti.setPlaceholderText("Yay!\nYaay!\nYaaay!");
        mti.setParent(v1);
        mti.getTextContent().setText("Twilight Sparkle\nRarity\nFluttershy\nRainbow Dash\nApplejack\nPinkie Pie");
        
        InteractiveMultilineTextContent mtc = new InteractiveMultilineTextContent("Twilight Sparkle\nRarity\nFluttershy\nRainbow Dash\nApplejack\nPinkie Pie");
        mtc.setParent(v1);
        mtc.setEditable(true);
        setHorizontalContentAlignment(mtc, 0.5);
        
        LayoutPanel grid = new LayoutPanel();
//        setPadding(grid, 8);
        GridLayout gridLayout = new GridLayout(3, 35);
        grid.setLayout(gridLayout);
        grid.setParent(v1);
//        setVerticalSpacing(grid, 8);
        setHorizontalSizePolicy(grid, new WrapAndFillSizePolicy());
        
        for(Column column : gridLayout.getColumns()){
            setHorizontalSizePolicy(column, new FillParentSizePolicy());
        }
        
        for(int i = 0; i < 80; i++){
            int x = i % 3;
            int y = i / 3;
            label = new SinglelineTextContent("Lorem ipsum i " + i);
            label.setParent(grid);
            setHorizontalAlignment(label, 0.5);
            setCell(label, x, y);
            if(i == 42) setHidden(label, true);
            if(i == 43) setHidden(label, true);
            if(i == 44) setHidden(label, true);
        }
        
        window.open();
    }
    
    private static class BigTextContent extends SinglelineTextContent {
        public BigTextContent(String text) {
            super(text);
        }
    }
    
    private static class OvalButton extends Button {
        public OvalButton() {
            getEventListeners().addLast(new GraphicsDrawAdapter() {
                @Override
                public void onDrawEventLeave(Graphics g) {
                    g.setColor(Color.WHITE);
                    g.drawOval(0, 0, getWidth(), getHeight());
                }
            });
            
            getEventListeners().addLast(new ActionAdapter() {
                @Override
                public void onEventEnter(ActionEvent e) {
                    e.consume();
                    System.out.println("Clicked oval!!!");
                }
            });
        }
    }
    
    private static class PagePanel extends Panel {}
    
    public static void setDebug(Window window){
        if(!DEBUG) Debug.setIds(window);
        if(!DEBUG) Debug.setMouseButtonPrintInfo(window);
        Debug.sendRandomColors(window);
        window.redraw();
        DEBUG = true;
        Debug.printComponentInfo(window);
    }
    
    private static class TestDesigner extends DefaultDesigner {
        private static final Font TOP_LABEL_FONT = new Font("default", 48, Font.Style.REGULAR);
        
        @Override
        public void onDesign(Component component) {
            super.onDesign(component);
            if(component instanceof BigTextContent) setFont(component, TOP_LABEL_FONT);
            if(component instanceof PagePanel) setPadding(component, 4);
            if(component instanceof PagePanel) setSpacing(component, 4);
        }
    }
    
    private static class Pony {
        private final String name;

        public Pony(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    private static Collection<Pony> PONIES = new Array<>(new Pony[]{
        new Pony("Twilight Sparkle"),
        new Pony("Rainbow Dash"),
        new Pony("Pinkie Pie"),
        new Pony("Rarity"),
        new Pony("Applejack"),
        new Pony("Fluttershy")
    });
}
