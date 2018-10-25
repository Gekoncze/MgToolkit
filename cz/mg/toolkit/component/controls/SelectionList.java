package cz.mg.toolkit.component.controls;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.utilities.Selectable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class SelectionList<T> extends Panel {
    protected Collection<T> items = null;
    
    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
        rebuildComponents();
    }
    
    protected abstract void rebuildComponents();
    
    public class ListItem extends SinglelineTextContent implements Selectable {
        private final T item;
        private boolean selected = false;

        public ListItem(T item) {
            this.item = item;
            setText("" + item);
            setWrapAndFillWidth(this);
        }
        
        @Override
        public final boolean isSelected() {
            return selected;
        }

        @Override
        public final void setSelected(boolean selected) {
            this.selected = selected;
        }
        
        public final T getItem() {
            return item;
        }
    }
}
