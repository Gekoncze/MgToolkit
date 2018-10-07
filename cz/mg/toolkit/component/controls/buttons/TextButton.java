package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class TextButton extends ContentButton {
    private final SinglelineTextContent textContent = new SinglelineTextContent();

    public TextButton() {
        initComponents();
    }
    
    private void initComponents(){
        textContent.setParent(this);
        textContent.setUsePrefferedSize(false);
        setContentAlignment(textContent, 0.5);
    }

    public SinglelineTextContent getTextContent() {
        return textContent;
    }
    
    @Override
    public final Content getContent() {
        return textContent;
    }
}
