package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.contents.SinglelineTextContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class TextButton extends ContentButton {
    public TextButton() {
        super(new SinglelineTextContent());
        initComponents();
    }
    
    public TextButton(String text) {
        super(new SinglelineTextContent(text));
        initComponents();
    }
    
    private void initComponents(){
        setContentAlignment(getTextContent(), 0.5);
        getContent().setUsePrefferedSize(true);
        setSizePolicy(getContent(), new WrapContentSizePolicy());
    }

    public final SinglelineTextContent getTextContent() {
        return (SinglelineTextContent) getContent();
    }
}
