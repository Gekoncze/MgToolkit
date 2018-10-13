package cz.mg.toolkit.component.controls;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.controls.buttons.ExtendedContentButton;
import cz.mg.toolkit.component.window.ContextMenu;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ComboBox<T> extends Panel {
    private static final int DEFAULT_WIDTH = 128;
    private static final int DEFAULT_HEIGHT = 24;
    
    private Collection<T> items = null;
    private T selectedItem = null;
    private final Text text = new Text();
    private final OpenButton button = new OpenButton();
    private final Menu menu = new Menu();

    public ComboBox() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new HorizontalLayout());
        setFixedSize(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void initComponents() {
        text.setParent(this);
        button.setParent(this);
        setFillParent(text);
        setFixedSize(button, DEFAULT_HEIGHT, DEFAULT_HEIGHT);
        menu.getContentPanel().setLayout(new VerticalLayout());
    }

    private void addEventListeners() {
        button.getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                if(menu.isActivated()){
                    menu.close();
                } else {
                    menu.open();
                    menu.setPosition(getScreenX(), getScreenY() + getHeight());
                }
            }
        });
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
        updateContextMenuItems();
        if(items.count() > 0){
            for(T item : items){
                updateSelectedItem(item);
                break;
            }   
        } else {
            updateSelectedItem(null);
        }
    }

    public T getSelectedItem() {
        return selectedItem;
    }
    
    private void updateContextMenuItems(){
        menu.getContentPanel().getChildren().clear();
        for(T item : items){
            menu.getContentPanel().getChildren().addLast(new ContextMenuItem(item));
        }
    }
    
    private void updateSelectedItem(T item){
        this.selectedItem = item;
        text.setText("" + this.selectedItem);
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
    
    private class ContextMenuItem extends SinglelineTextContent {
        private final T item;

        public ContextMenuItem(T item) {
            this.item = item;
            setText("" + this.item);
            setWrapAndFillWidth(this);
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventEnter(MouseButtonEvent e) {
                    if(!wasLeftButton(e) || !wasPressed(e)) return;
                    updateSelectedItem(item);
                    menu.close();
                    ComboBox.this.redraw();
                    raiseOrSendActionEvent();
                }
            });
        }

        public T getItem() {
            return item;
        }
    }

    public static class OpenButton extends ExtendedContentButton {
        @Override
        protected Content createContent() {
            return new Content();
        }
        
        public static class Content extends DrawableContent {}
    }
    
    public static class Text extends SinglelineTextContent {}
    
    public static class Menu extends ContextMenu {}
}
