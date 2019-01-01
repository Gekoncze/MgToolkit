package cz.mg.toolkit.utilities.text;

import cz.mg.collections.list.ReadonlyList;


public interface TextModel {
    public String getText();
    public void setText(String text);
    public ReadonlyList<TextPart> getTextParts();
    public Caret getBeginCaret();
    public Caret getEndCaret();
    public String copy();
    public String cut();
    public void delete();
    public void paste(String string);
    public boolean update();
    public Options getOptions();
    public double getTextX();
    public double getTextY();
    public double getTextWidth();
    public double getTextHeight();
    public double getLineHeight();
}
