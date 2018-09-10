package cz.mg.toolkit.component.components;

import cz.mg.toolkit.component.Panel;
import cz.mg.toolkit.component.contents.Canvas;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.SelectionGroup;
import cz.mg.toolkit.utilities.Selectable;
import cz.mg.toolkit.utilities.Triggerable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class RadioButton extends Panel implements Selectable, Triggerable {
    private boolean selected = false;
    private SelectionGroup selectionGroup;
    private final Canvas canvas = new Canvas(16, 16);

    public RadioButton() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new OverlayLayout());
        setBorder(this, null);
    }
    
    private void initComponents(){
        canvas.setParent(this);
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    trigger();
                }
            }
        });
        canvas.getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.drawOval(0, 0, canvas.getWidth()-1, canvas.getHeight()-1);
                if(isSelected()) g.fillOval(4, 4, 8-1, 8-1);
            }
        });
    }

    public SelectionGroup getSelectionGroup() {
        return selectionGroup;
    }

    public void setSelectionGroup(SelectionGroup selectionGroup) {
        if(this.selectionGroup != null) this.selectionGroup.removeSelectable(this);
        this.selectionGroup = selectionGroup;
        selectionGroup.addSelectable(this);
    }

    @Override
    public final boolean isSelected() {
        return selected;
    }

    @Override
    public final void setSelected(boolean value) {
        this.selected = value;
    }
    
    @Override
    public void trigger() {
        if(selectionGroup != null) selectionGroup.clearSelection();
        setSelected(true);
        raiseEvent(new ActionEvent(this));
        relayout();
    }
}
