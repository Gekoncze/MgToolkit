package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.contents.TextContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class TextButton extends ContentButton {
    public static final String DEFAULT_DESIGN_NAME = "text button";
    
    public TextButton() {
        super(new TextContent());
        initComponents();
    }
    
    public TextButton(String text) {
        super(new TextContent(text));
        initComponents();
    }
    
    private void initComponents(){
        setContentAlignment(getTextContent(), 0.5);
        getContent().setUsePrefferedSize(true);
        setSizePolicy(getContent(), new WrapContentSizePolicy());
    }

    public final TextContent getTextContent() {
        return (TextContent) getContent();
    }
}
