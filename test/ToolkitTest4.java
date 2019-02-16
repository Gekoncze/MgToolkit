package test;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.controls.buttons.TextButton;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.environment.device.devices.Keyboard;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.graphics.images.BitmapImage;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.swing.SwingImplApi;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.utilities.Debug;
import cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;

import java.io.IOException;


public class ToolkitTest4 {
    public static void main(String[] args) throws IOException {
        Impl.setImplApi(new SwingImplApi());
        Impl.getImplApi().getPrimaryDisplay().setHorizontalZoom(0.35);
        Impl.getImplApi().getPrimaryDisplay().setVerticalZoom(0.35);

        Window window = new Window();
        window.setSize(200*4, 150*4);
        window.setTitle("Yay!");
        window.setIcon(new BitmapImage(ToolkitTest2.class.getResourceAsStream("mg.png")));
        window.center();

        window.getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(e.wasPressed()) if(e.getLogicalButton() == Keyboard.Button.ENTER) Debug.printComponentInfo(window);
            }
        });

        Panel root = window.getContentPanel();
        root.setLayout(new VerticalLayout());

        Button butt = new TextButton("Yay!");
        butt.setParent(root);
        SimplifiedPropertiesInterface.setSizePolicy(butt, new WrapContentSizePolicy());

        window.redesign();
        window.open();
    }
}
