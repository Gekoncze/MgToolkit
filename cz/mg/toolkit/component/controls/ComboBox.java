package cz.mg.toolkit.component.controls;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.controls.buttons.ContentButton;
import cz.mg.toolkit.component.window.ContextMenu;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;


public class ComboBox<T> extends Panel {
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
        setHorizontalSizePolicy(this, new FillParentSizePolicy());
    }

    private void initComponents() {
        text.setParent(this);
        button.setParent(this);
        setSizePolicy(text, new FillParentSizePolicy());
        menu.getContentPanel().setLayout(new VerticalLayout());
    }

    private void addEventListeners() {
        text.getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(wasLeftButton(e) && wasPressed(e)) button.sendEvent(new ActionEvent(text));
            }
        });
        
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

    public final Collection<T> getItems() {
        return items;
    }

    public final void setItems(Collection<T> items) {
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

    public final T getSelectedItem() {
        return selectedItem;
    }
    
    private void updateContextMenuItems(){
        menu.getContentPanel().getChildren().clear();
        if(items == null) return;
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
            setHorizontalSizePolicy(this, new WrapAndFillSizePolicy());
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

        public final T getItem() {
            return item;
        }
    }

    public static class OpenButton extends ContentButton {
        public OpenButton() {
            super(new Content());
        }

        public static class Content extends DrawableContent {}
    }
    
    public static class Text extends SinglelineTextContent {}
    
    public static class Menu extends ContextMenu {}
}
