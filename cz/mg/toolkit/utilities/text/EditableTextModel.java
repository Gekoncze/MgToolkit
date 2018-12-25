package cz.mg.toolkit.utilities.text;


public interface EditableTextModel extends TextModel {
    public void insert(int i, String text);
    public void remove(int iBegin, int iEnd);
}
