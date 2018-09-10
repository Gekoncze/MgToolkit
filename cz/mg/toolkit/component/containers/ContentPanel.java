package cz.mg.toolkit.component.containers;

import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class ContentPanel extends Panel {
    public ContentPanel() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new OverlayLayout());
        setFillParentWidth(this);
        setFillParentHeight(this);
    }
}
