package cz.mg.toolkit.component.window;

import cz.mg.toolkit.component.controls.Menu;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ContextMenu extends PopupWindow {
    private final Menu menu = new Menu();
    
    public ContextMenu() {
        initComponent();
        initComponents();
        addEventListeners();
    }

    private void initComponent() {
        setWrapContent(this);
        setWrapContent(getContentPanel());
        setWrapContent(getDecoration());
        setWrapContent(getDecoration().getContentPanel());
        setTitle("Context menu");
    }

    private void initComponents() {
        getContentPanel().getChildren().addLast(menu);
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                closeImmediately();
            }
        });
    }

    public Menu getMenu() {
        return menu;
    }
}
