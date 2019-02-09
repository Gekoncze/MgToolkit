package cz.mg.toolkit.utilities.text;

import cz.mg.collections.list.ReadonlyList;


public interface TextArrangement {
    public void setTextModel(TextModel textModel);
    public Options getOptions();
    public ReadonlyList<TextPart> getParts();
    public double getTextX();
    public double getTextY();
    public double getTextWidth();
    public double getTextHeight();
    public double getLineHeight();
    public boolean update();
    public void onChange(TextModel.Change change);
    public Caret getBeginCaret();
    public Caret getEndCaret();

    public default double getRequiredWidth() {
        return getTextWidth();
    }

    public default double getRequiredHeight() {
        return getTextHeight();
    }
}
