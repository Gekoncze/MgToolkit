package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class DefineConstant extends TreeLeaf<DesignerRoot> {
    private final Substring name;
    private final Substring value;

    public DefineConstant(Substring name, Substring value) {
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
