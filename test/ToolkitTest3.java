package test;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.controls.ComboBox;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.component.wrappers.decorations.ToolkitDecoration;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.utilities.properties.PropertiesInterface;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


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
        
        SinglelineTextContent text = new SinglelineTextContent("Yay!");
        text.setParent(p);
        
        text = new SinglelineTextContent("Yay!");
        text.setParent(p);
        
        text = new SinglelineTextContent("Yay!");
        text.setParent(p);
        
        ComboBox box = new ComboBox();
        box.setParent(p);
        
        box.getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventLeave(AfterLayoutEvent e) {
                System.out.println("box v. size policy: " + getVerticalSizePolicy(box) + ";; minh: " + getMinHeight(box) + ";; maxh: " + getMaxHeight(box));
            }
        });
    }
}
