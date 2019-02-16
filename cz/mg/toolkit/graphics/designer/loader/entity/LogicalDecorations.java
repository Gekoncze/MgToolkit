package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class LogicalDecorations extends TreeLeaf<LogicalDesigner> {
    private final Substring classPath;

    public LogicalDecorations(Substring classPath) {
        this.classPath = classPath;
    }

    public Substring getClassPath() {
        return classPath;
    }
}
