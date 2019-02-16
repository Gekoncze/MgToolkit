package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeNode;
import cz.mg.parser.utilities.Substring;


public class LogicalDesign extends TreeNode<LogicalDesigner, LogicalSetter> {
    private final Substring name;
    private final Substring parentName;

    public LogicalDesign(Substring name, Substring parentName) {
        this.name = name;
        this.parentName = parentName;
    }

    public Substring getName() {
        return name;
    }

    public Substring getParentName() {
        return parentName;
    }
}
