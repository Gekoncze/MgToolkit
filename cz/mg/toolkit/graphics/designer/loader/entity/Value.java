package cz.mg.toolkit.graphics.designer.loader.entity;

import cz.mg.parser.utilities.Substring;


public class Value {
    private Substring value;
    private boolean literal;

    public Value(Substring value, boolean literal) {
        this.value = value;
        this.literal = literal;
    }

    public Substring getValue() {
        return value;
    }

    public boolean isLiteral() {
        return literal;
    }
}
