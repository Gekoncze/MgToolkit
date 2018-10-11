package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class ExtendedContentButton extends ContentButton {
    private Content canvasContent;

    public ExtendedContentButton() {
        initComponents();
    }

    private void initComponents() {
        canvasContent = createContent();
        canvasContent.setParent(this);
        canvasContent.setUsePrefferedSize(false);
        setFillParent(canvasContent);
    }

    @Override
    public Content getContent() {
        return canvasContent;
    }
    
    protected abstract Content createContent();
}
