package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.layout.layouts.OverlayLayout;


public class NoDecoration extends Decoration {
    public NoDecoration() {
        setLayout(new OverlayLayout());
    }
    
    @Override
    public void setTitle(String title) {
    }

    @Override
    public void setIcon(Image icon) {
    }
}
