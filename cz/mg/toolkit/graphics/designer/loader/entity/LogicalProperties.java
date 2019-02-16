package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class LogicalProperties extends TreeLeaf<LogicalDesigner> {
    private final Substring classPath;

    public LogicalProperties(Substring classPath) {
        this.classPath = classPath;
    }

    public Substring getClassPath() {
        return classPath;
    }
}
