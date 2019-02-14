package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeNode;
import cz.mg.parser.utilities.Substring;


public class DefineDesign extends TreeNode<DesignerRoot, Setter> {
    private final Substring name;
    private final Substring parentName;

    public DefineDesign(Substring name, Substring parentName) {
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
