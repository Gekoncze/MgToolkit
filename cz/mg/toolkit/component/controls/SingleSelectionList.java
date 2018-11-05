package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.utilities.SelectionGroup;


public class SingleSelectionList<T> extends SelectionList<T> {
    public static final String DEFAULT_DESIGN_NAME = "single selection list";
    
    private final SelectionGroup selectionGroup = new SelectionGroup();
    
    public SingleSelectionList() {
        initComponent();
    }
    
    private void initComponent() {
        setLayout(new VerticalLayout());
    }

    public final T getSelectedItem() {
        for(Component component : getChildren()){
            ListItem listItem = (ListItem) component;
            if(listItem.isSelected()) return (T) listItem.getItem();
        }
        return null;
    }
    
    @Override
    protected void rebuildComponents(){
        getChildren().clear();
        selectionGroup.removeAll();
        if(items == null) return;
        for(T item : items){
            ListItem listItem = new ListItem(item);
            listItem.setParent(this);
            selectionGroup.addSelectable(listItem);
        }
    }
    
    private void select(ListItem listItem){
        boolean value = !listItem.isSelected();
        selectionGroup.clearSelection();
        listItem.setSelected(value);
        raiseOrSendActionEvent();
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
    
    public class ListItem extends SelectionList.ListItem {
        public static final String DEFAULT_DESIGN_NAME = "single selection list item";
        
        public ListItem(T item) {
            super(item);
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventEnter(MouseButtonEvent e) {
                    if(!wasLeftButton(e) || !wasPressed(e)) return;
                    select(ListItem.this);
                    redraw();
                }
            });
        }
    }
}
