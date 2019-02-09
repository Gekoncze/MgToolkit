package cz.mg.toolkit.component.controls;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.utilities.Selectable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.text.textarrangements.SinglelineTextArrangement;


public abstract class SelectionList<T> extends Panel {
    public static final String DEFAULT_DESIGN_NAME = "selection list";
    
    protected Collection<T> items = null;

    public SelectionList() {
    }
    
    public final Collection<T> getItems() {
        return items;
    }

    public final void setItems(Collection<T> items) {
        this.items = items;
        rebuildComponents();
    }
    
    protected abstract void rebuildComponents();
    
    public class ListItem extends TextContent implements Selectable {
        public static final String DEFAULT_DESIGN_NAME = "selection list item";
        
        private final T item;
        private boolean selected = false;

        public ListItem(T item) {
            this.item = item;
            getTextModel().setTextArrangement(new SinglelineTextArrangement());
            getTextModel().setText("" + item);
            setSizePolicy(this, new WrapAndFillSizePolicy());
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
