package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.layout.layouts.OverlayLayout;


public class NoDecoration extends Decoration {
    public static final String DEFAULT_DESIGN_NAME = "no decoration";
    
    public NoDecoration() {
        setLayout(new OverlayLayout());
    }
}
