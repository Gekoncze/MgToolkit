package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.Canvas;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.Selectable;
import cz.mg.toolkit.utilities.Triggerable;


public class CheckBox extends Panel implements Selectable, Triggerable {
    private boolean selected = false;
    private final Canvas canvas = new Canvas(16, 16);
    
    public CheckBox() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new OverlayLayout());
    }
    
    private void initComponents() {
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
                if(isSelected()){
                    g.drawLine(4, 4, 12, 12);
                    g.drawLine(4, 12, 12, 4);
                }
            }
        });
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
        setSelected(!selected);
        raiseEvent(new ActionEvent(this));
        relayout();
    }    
}
