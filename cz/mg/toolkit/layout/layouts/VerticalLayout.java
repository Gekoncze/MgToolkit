package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.Layout;
import cz.mg.toolkit.layout.reshapes.HorizontalOverlayReshape;
import cz.mg.toolkit.layout.reshapes.VerticalLinearReshape;


public class VerticalLayout extends Layout {
    @Override
    public void reshapeComponents(Container parent) {
        HorizontalOverlayReshape.reshape(parent, parent.getChildren());
        VerticalLinearReshape.reshape(parent, parent.getChildren());
    }

    @Override
    public double computeMinWidth(Container parent) {
        return HorizontalOverlayReshape.computeMinWidth(parent, parent.getChildren());
    }

    @Override
    public double computeMinHeight(Container parent) {
        return VerticalLinearReshape.computeWrapHeight(parent, parent.getChildren());
    }
}
