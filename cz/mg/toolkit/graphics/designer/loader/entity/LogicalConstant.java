package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class LogicalConstant extends TreeLeaf<LogicalDesigner> {
    private final Substring name;
    private final Substring value;

    public LogicalConstant(Substring name, Substring value) {
        this.name = name;
        this.value = value;
    }

    public Substring getName() {
        return name;
    }

    public Substring getValue() {
        return value;
    }
}
