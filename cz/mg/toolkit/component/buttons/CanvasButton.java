package cz.mg.toolkit.component.buttons;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.Canvas;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class CanvasButton extends ContentButton {
    private final Canvas canvas = new Canvas();

    public CanvasButton() {
        initComponents();
    }

    private void initComponents() {
        canvas.setParent(this);
        setWrapAndFillWidth(canvas);
        setWrapAndFillHeight(canvas);
    }
    
    public final Canvas getCanvas() {
        return canvas;
    }

    @Override
    public final Content getContent() {
        return canvas;
    }
}
