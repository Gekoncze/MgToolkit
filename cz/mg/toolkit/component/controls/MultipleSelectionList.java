package cz.mg.toolkit.component.controls;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.layout.layouts.VerticalLayout;


public class MultipleSelectionList<T> extends SelectionList<T> {
    public static final String DEFAULT_DESIGN_NAME = "multiple selection list";
    
    public MultipleSelectionList() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new VerticalLayout());
    }

    public List<T> getSelectedItems(){
        ChainList<T> selectedItems = new ChainList<>();
        for(Component component : getChildren()){
            ListItem listItem = (ListItem) component;
            if(listItem.isSelected()) selectedItems.addLast((T) listItem.getItem());
        }
        return selectedItems;
    }
    
    @Override
    protected void rebuildComponents(){
        getChildren().clear();
        if(items == null) return;
        for(T item : items){
            ListItem listItem = new ListItem(item);
            listItem.setParent(this);
        }
    }
    
    private void switchSelection(ListItem listItem){
        listItem.setSelected(!listItem.isSelected());
        raiseOrSendActionEvent();
    }
    
    private void raiseOrSendActionEvent(){
        raiseEvent(new ActionEvent(this));
    }
    
    public class ListItem extends SelectionList.ListItem {
        public static final String DEFAULT_DESIGN_NAME = "multiple selection list item";
        
        public ListItem(T item) {
            super(item);
            getEventListeners().addLast(new LocalMouseButtonAdapter() {
                @Override
                public void onMouseButtonEventEnter(MouseButtonEvent e) {
                    if(!wasLeftButton(e) || !wasPressed(e)) return;
                    switchSelection(ListItem.this);
                    redraw();
                }
            });
        }
    }
}
