package cz.mg.toolkit.utilities.text;


public interface MultilineTextModel extends SinglelineTextModel {
    public String getText(int ixBegin, int iyBegin, int ixEnd, int iyEnd);
    public void insert(int x, int y, String s);
    public void remove(int ixBegin, int iyBegin, int ixEnd, int iyEnd);
    public String getLine(int y);
    public int lineCount();
}
