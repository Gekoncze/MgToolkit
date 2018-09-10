package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.Layout;
import cz.mg.toolkit.layout.reshapes.HorizontalOverlayReshape;
import cz.mg.toolkit.layout.reshapes.VerticalOverlayReshape;


public class OverlayLayout extends Layout {
    @Override
    public void reshapeComponents(Container parent) {
        HorizontalOverlayReshape.reshape(parent, parent.getChildren());
        VerticalOverlayReshape.reshape(parent, parent.getChildren());
    }

    @Override
    public double computeMinWidth(Container parent) {
        return HorizontalOverlayReshape.computeMinWidth(parent, parent.getChildren());
    }

    @Override
    public double computeMinHeight(Container parent) {
        return VerticalOverlayReshape.computeMinHeight(parent, parent.getChildren());
    }
}
