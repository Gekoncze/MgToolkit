package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.controls.Button;
import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.setSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public abstract class ContentButton extends Button {
    public static final String DEFAULT_DESIGN_NAME = "content button";
    
    private Content content;

    public ContentButton(Content content) {
        initComponents(content);
    }

    private void initComponents(Content content) {
        this.content = content;
        content.setParent(this);
        content.setAutosize(false);
        setSizePolicy(content, new FillParentSizePolicy());
    }

    public final Content getContent() {
        return content;
    }
}
