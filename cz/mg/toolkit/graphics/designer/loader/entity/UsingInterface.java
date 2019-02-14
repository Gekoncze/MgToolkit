package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class UsingInterface extends TreeLeaf<DesignerRoot> {
    private final Substring classPath;

    public UsingInterface(Substring classPath) {
        this.classPath = classPath;
    }

    public Substring getClassPath() {
        return classPath;
    }
}
