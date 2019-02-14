package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class UsingDecorations extends TreeLeaf<DesignerRoot> {
    private final Substring classPath;

    public UsingDecorations(Substring classPath) {
        this.classPath = classPath;
    }

    public Substring getClassPath() {
        return classPath;
    }
}
