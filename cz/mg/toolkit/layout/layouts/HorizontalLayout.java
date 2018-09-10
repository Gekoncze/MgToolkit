package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.Layout;
import cz.mg.toolkit.layout.reshapes.HorizontalLinearReshape;
import cz.mg.toolkit.layout.reshapes.VerticalOverlayReshape;


public class HorizontalLayout extends Layout {
    @Override
    public void reshapeComponents(Container parent) {
        HorizontalLinearReshape.reshape(parent, parent.getChildren());
        VerticalOverlayReshape.reshape(parent, parent.getChildren());
    }

    @Override
    public double computeMinWidth(Container parent) {
        return HorizontalLinearReshape.computeWrapWidth(parent, parent.getChildren());
    }

    @Override
    public double computeMinHeight(Container parent) {
        return VerticalOverlayReshape.computeMinHeight(parent, parent.getChildren());
    }
}
