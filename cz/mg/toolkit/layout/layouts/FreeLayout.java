package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.Layout;


public class FreeLayout extends Layout {
    @Override
    public void reshapeComponents(Container parent) {
    }

    @Override
    public double computeMinWidth(Container parent) {
        return parent.getContentWidth();
    }

    @Override
    public double computeMinHeight(Container parent) {
        return parent.getContentHeight();
    }
}
