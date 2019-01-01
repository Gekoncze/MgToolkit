package test;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.TextInput;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FixedRangeSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FixedSizePolicy;
import cz.mg.toolkit.utilities.text.textmodels.MultiLineTextModel;
import cz.mg.toolkit.utilities.text.textmodels.SingleLineTextModel;


public class ToolkitTest3 {
    public static void main(String[] args) {
        Impl.setImplApi(new SwingImplApi());
        Impl.getImplApi().getPrimaryDisplay().setHorizontalZoom(0.25);
        Impl.getImplApi().getPrimaryDisplay().setVerticalZoom(0.25);
        
        Window window = new Window();
        window.setSize(800, 600);
        window.open();
        
        Panel p = window.getContentPanel();
        p.setLayout(new VerticalLayout());
        
        TextInput ti = new TextInput();
        setHorizontalSizePolicy(ti, new FixedRangeSizePolicy(0, 160));
        setVerticalSizePolicy(ti, new FixedSizePolicy(64));
        ti.setParent(p);
        ti.getTextContent().setPlaceholderTextModel(new SingleLineTextModel("Try typing here!"));
        ti.getTextContent().setTextModel(new SingleLineTextModel());
        
//        TextContent text = new TextContent("Yay!");
//        text.setParent(p);
//        
//        text = new TextContent("Yay!");
//        text.setParent(p);
//        
//        text = new TextContent("Yay!");
//        text.setParent(p);
//        
//        ComboBox box = new ComboBox();
//        box.setParent(p);
//        
//        box.getEventListeners().addLast(new AfterLayoutAdapter() {
//            @Override
//            public void onEventLeave(AfterLayoutEvent e) {
//                System.out.println("box v. size policy: " + getVerticalSizePolicy(box) + ";; minh: " + getMinHeight(box) + ";; maxh: " + getMaxHeight(box));
//            }
//        });
    }
}
