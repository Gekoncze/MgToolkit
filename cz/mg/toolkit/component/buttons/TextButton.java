package cz.mg.toolkit.component.buttons;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.TextContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class TextButton extends ContentButton {
    private final TextContent textContent = new TextContent();

    public TextButton() {
        initComponents();
    }
    
    private void initComponents(){
        textContent.setParent(this);
        textContent.setUsePrefferedSize(false);
        setContentAlignment(textContent, 0.5);
    }

    public TextContent getTextContent() {
        return textContent;
    }
    
    @Override
    public final Content getContent() {
        return textContent;
    }
}
