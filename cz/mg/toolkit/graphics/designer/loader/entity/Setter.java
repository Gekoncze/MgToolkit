package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class Setter extends TreeLeaf<DefineDesign> {
    private final Substring name;
    private final Substring value;
    private final boolean literal;

    public Setter(Substring name, Substring value, boolean literal) {
        this.name = name;
        this.value = value;
        this.literal = literal;
    }

    public Substring getName() {
        return name;
    }

    public Substring getValue() {
        return value;
    }

    public boolean isLiteral() {
        return literal;
    }
}
