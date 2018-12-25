package cz.mg.toolkit.utilities.text;


public interface MultilineTextModel extends SinglelineTextModel {
    public String getLine(int y);
    public int lineCount();
    public int caretsToCaret(int ix, int iy);
}
