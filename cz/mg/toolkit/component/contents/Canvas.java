package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;


public class Canvas extends Content {
    public static final String DEFAULT_DESIGN_NAME = "canvas";
    
    public Canvas() {
        super(0, 0);
    }
    
    public Canvas(int contentWidth, int contentHeight) {
        super(contentWidth, contentHeight);
    }
}
