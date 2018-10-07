package cz.mg.toolkit.utilities.text;


public interface SinglelineTextModel extends TextModel {
    public String getText(int iBegin, int iEnd);
    public void insert(int i, String text);
    public void remove(int iBegin, int iEnd);
}
