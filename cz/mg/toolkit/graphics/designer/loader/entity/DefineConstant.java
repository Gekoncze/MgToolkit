package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.collections.tree.TreeLeaf;


public class DefineConstant extends TreeLeaf<DefineDesign> {
    private final String name;
    private final String value;

    public DefineConstant(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
