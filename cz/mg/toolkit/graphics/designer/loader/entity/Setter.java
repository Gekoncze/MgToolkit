package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.tree.TreeLeaf;
import cz.mg.parser.utilities.Substring;


public class Setter extends TreeLeaf<DefineDesign> {
    private final Substring name;
    private final ChainList<Value> values;

    public Setter(Substring name, ChainList<Value> values) {
        this.name = name;
        this.values = values;
    }

    public Substring getName() {
        return name;
    }

    public ChainList<Value> getValues() {
        return values;
    }
}
