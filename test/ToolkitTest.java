package test;

import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.toolkit.component.controls.spinners.IntegerSpinner;
import cz.mg.toolkit.event.events.*;
import cz.mg.toolkit.graphics.designer.Designer;
import cz.mg.toolkit.graphics.designer.StructuredDesigner;
import cz.mg.toolkit.graphics.designer.designers.DesignersLocation;
import cz.mg.toolkit.graphics.designer.loader.task.DesignerLoader;
import cz.mg.toolkit.layout.layouts.*;
import cz.mg.toolkit.component.window.*;
import cz.mg.toolkit.component.contents.*;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.containers.*;
import cz.mg.toolkit.component.wrappers.*;
import cz.mg.toolkit.component.controls.*;
import cz.mg.toolkit.component.controls.buttons.*;
import cz.mg.toolkit.component.controls.menuitems.*;
import cz.mg.toolkit.component.controls.sliders.horizontal.*;
import cz.mg.toolkit.component.controls.sliders.vertical.*;
import cz.mg.toolkit.component.wrappers.decorations.SystemDecoration;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.utilities.Debug;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.*;
import cz.mg.toolkit.graphics.*;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.GridLayout.Column;
import cz.mg.toolkit.utilities.shapes.OvalShape;
import cz.mg.toolkit.utilities.SelectionGroup;
import cz.mg.toolkit.utilities.keyboardshortcuts.StandardKeyboardCharacterShortcut;
import java.io.IOException;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.*;
import cz.mg.toolkit.utilities.text.textarrangements.MultilineTextArrangement;


public class ToolkitTest {
    private static boolean DEBUG = false;

    public static void main(String[] args) throws IOException {
        Impl.setImplApi(new SwingImplApi());
        Impl.getImplApi().getPrimaryDisplay().setPhysicalWidth(312);
        Impl.getImplApi().getPrimaryDisplay().setPhysicalHeight(178);

        Window.defaultDesigner = loadDesigner();

        TextContent label;
        SelectionGroup selectionGroup = new SelectionGroup();

        MenuItem m1 = new StandardMenuItem(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")), "yay", new StandardKeyboardCharacterShortcut(true, false, false, 's'), null, null);
        MenuItem m2 = new StandardMenuItem(null, "nayyyyyyyyyyyyyyyyyyyyyy", null, true, null);
        MenuItem m3 = new SeparatorMenuItem();
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
        window.setSize(180, 140);
        window.setTitle("Yay!");
        window.setIcon(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        window.center();

        PonyDialog ponyDialog = new PonyDialog(window);

        window.getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(!wasButtonPressed(e)) return;
                if(e.getLogicalButton() == Keyboard.Button.F1) window.setDecorated(!window.isDecorated());
                if(e.getLogicalButton() == Keyboard.Button.F1) window.setDecoration(new SystemDecoration());
                if(e.getLogicalButton() == Keyboard.Button.F2) { window.setDecoration(new ToolkitDecoration()); }
                if(e.getLogicalButton() == Keyboard.Button.F3) ;
                if(e.getLogicalButton() == Keyboard.Button.F4) ;
                if(e.getLogicalButton() == Keyboard.Button.F5) ;
                if(e.getLogicalButton() == Keyboard.Button.F6) ;
                if(e.getLogicalButton() == Keyboard.Button.F7) window.setMinimized(true);
                if(e.getLogicalButton() == Keyboard.Button.F8) window.setMaximized(!window.isMaximized());
                if(e.getLogicalButton() == Keyboard.Button.F9) {setDebug(window); setDebug(contextMenu);}
                if(e.getLogicalButton() == Keyboard.Button.F10) {setDebug(window); setDebug(contextMenu);}
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

        CompactHorizontalScrollArea tabsArea = new CompactHorizontalScrollArea();
        tabsArea.setParent(windowPanel);

        Container bigTextContainer = tabsArea.getContentPanel();
        setDesignName(bigTextContainer, "big text container");
        bigTextContainer.setLayout(new HorizontalLayout());

        int n = 5;
        for(int i = 0; i <= n; i++){
            label = new TextContent(i + "" + i + "" + i + "" + i + "" + i);
            setDesignName(label, "big text content");
            label.setParent(bigTextContainer);
            if(i < n){
                bigTextContainer.getChildren().addLast(new HorizontalSeparator());
            }
        }

        ScrollArea scrollArea = new ScrollArea();
        scrollArea.setParent(windowPanel);
        setHorizontalContentAlignment(scrollArea.getContentPanel(), 0.5);

        Panel v1 = new Panel();
        setDesignName(v1, "page panel");
        v1.setLayout(new VerticalLayout());
        v1.setParent(scrollArea.getContentPanel());

        LayoutPanel h0 = new LayoutPanel();
        h0.setLayout(new HorizontalLayout());
        h0.setParent(v1);
        setHorizontalSizePolicy(h0, new WrapAndFillSizePolicy());

        TextButton tb = new TextButton();
        tb.getTextContent().setText("Pony");
        tb.setParent(h0);
        setSizePolicy(tb, new WrapContentSizePolicy());
        tb.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                ponyDialog.open();
            }
        });

        ImageButton ib = new ImageButton();
        ib.getImageContent().setImage(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        ib.setParent(h0);
        setSizePolicy(ib, new NoSizePolicy());
        setFixedSize(ib, 64*3/10, 24*3/10);
        ib.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                System.out.println("Image button!");
            }
        });

        TextInput ti = new TextInput();
        setSizePolicy(ti, new NoSizePolicy());
        setMinWidth(ti, 0*3/10);
        setMaxWidth(ti, 160*3/10);
        setMinHeight(ti, 64*3/10);
        setMaxHeight(ti, 64*3/10);
        ti.setParent(h0);
        ti.getTextContent().setText("Try typing here!");
        ti.getTextContent().getPlaceholderTextModel().setTextArrangement(new MultilineTextArrangement());

        TextContent alignmentTest = new TextContent("Yay!");
        alignmentTest.setParent(h0);
        setSizePolicy(alignmentTest, new FillParentSizePolicy());
        setContentAlignment(alignmentTest, 0.5);

        LayoutPanel h1 = new LayoutPanel();
        h1.setLayout(new HorizontalLayout());
        h1.setParent(v1);
        setHorizontalSizePolicy(h1, new WrapAndFillSizePolicy());

        label = new TextContent("Lorem ipsum 1");
        label.setParent(h1);

        label = new TextContent("Lorem ipsum 2");
        label.setParent(h1);

        label = new TextContent("Lorem ipsum 3");
        label.setParent(h1);

        LayoutPanel h2 = new LayoutPanel();
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

        LayoutPanel h4 = new LayoutPanel();
        h4.setLayout(new HorizontalLayout());
        h4.setParent(v1);

        label = new TextContent("Lorem ipsum 8");
        label.setParent(h4);

        label = new TextContent("Lorem ipsum 9999999999999999999999999999999999999999999999999999");
        label.setParent(h4);

        OvalButton ovalButton = new OvalButton();
        setShape(ovalButton, new OvalShape());
        setSizePolicy(ovalButton, new NoSizePolicy());
        setFixedSize(ovalButton, 64*2*3/10, 32*2*3/10);
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
//
//        LongSpinner longSpinner = new LongSpinner();
//        longSpinner.setParent(v1);
//        longSpinner.getEventListeners().addLast(new ActionAdapter() {
//            @Override
//            public void onEventEnter(ActionEvent e) {
//                System.out.println("Long: " + longSpinner.getValue());
//            }
//        });
//
//        FloatSpinner floatSpinner = new FloatSpinner();
//        floatSpinner.setParent(v1);
//        floatSpinner.getEventListeners().addLast(new ActionAdapter() {
//            @Override
//            public void onEventEnter(ActionEvent e) {
//                System.out.println("Float: " + floatSpinner.getValue());
//            }
//        });
//
//        DoubleSpinner doubleSpinner = new DoubleSpinner();
//        doubleSpinner.setParent(v1);
//        doubleSpinner.getEventListeners().addLast(new ActionAdapter() {
//            @Override
//            public void onEventEnter(ActionEvent e) {
//                System.out.println("Double: " + doubleSpinner.getValue());
//            }
//        });

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
        setVerticalSizePolicy(vvp, new NoSizePolicy());
        setFixedHeight(vvp, 256*3/10);
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

        TextInput mti = new TextInput();
        setSizePolicy(mti, new NoSizePolicy());
        setFixedSize(mti, 128*3/10, 64*3/10);
        mti.getTextContent().getPlaceholderTextModel().setText("Yay!\nYaay!\nYaaay!");
        mti.setParent(v1);
        mti.getTextContent().setText("Twilight Sparkle\nRarity\nFluttershy\nRainbow Dash\nApplejack\nPinkie Pie");
//
//        InteractiveTextContent mtc = new InteractiveTextContent("Twilight Sparkle\nRarity\nFluttershy\nRainbow Dash\nApplejack\nPinkie Pie");
//        mtc.setParent(v1);
//        mtc.setEditable(true);
//        setHorizontalContentAlignment(mtc, 0.5);

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
            label = new TextContent("Lorem ipsum i " + i);
            label.setParent(grid);
            setHorizontalAlignment(label, 0.5);
            setCell(label, x, y);
            if(i == 42) setHidden(label, true);
            if(i == 43) setHidden(label, true);
            if(i == 44) setHidden(label, true);
        }

        Debug.setIds(window);

        window.redesign();
        window.open();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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

    private static class PonyDialog extends DialogWindow {
        private ComboBox<Pony> boxOfPonies;

        public PonyDialog(Window parent) {
            super(parent);
            initComponent();
            initComponents();

            getEventListeners().addLast(new KeyboardButtonAdapter() {
                @Override
                public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                    if(e.wasPressed() && e.getLogicalButton() == Keyboard.Button.F9) {setDebug(PonyDialog.this);}
                }
            });
        }

        private void initComponent(){
            setSize(640, 320);
            center();
            setLayout(new OverlayLayout());
        }

        private void initComponents(){
            boxOfPonies = new ComboBox<>();
            boxOfPonies.setItems(PONIES);
            boxOfPonies.setParent(getContentPanel());
            boxOfPonies.getEventListeners().addLast(new ActionAdapter() {
                @Override
                public void onEventEnter(ActionEvent e) {
                    e.consume();
                    System.out.println("Your favourite pony in a box is " + boxOfPonies.getSelectedItem());
                }
            });
        }
    }

    public static void setDebug(Window window){
        if(!DEBUG) Debug.setIds(window);
        if(!DEBUG) Debug.setMouseButtonPrintInfo(window);
        Debug.sendRandomColors(window);
        window.redraw();
        DEBUG = true;
        Debug.printComponentInfo(window);
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

    private static Designer loadDesigner(){
        DesignerLoader loader = new DesignerLoader();
        StructuredDesigner designer = (StructuredDesigner) loader.load(
                DesignersLocation.class.getResourceAsStream("DefaultDesigner"),
                ToolkitTest.class.getResourceAsStream("ToolkitTestDesigner")
        );
        System.out.println("LOADED DESIGNS: " + designer.getDesigns().count());
        return designer;
    }
}
