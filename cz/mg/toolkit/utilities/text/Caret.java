package cz.mg.toolkit.utilities.text;


public interface Caret {
    public int getCaret();
    public void setCaret(int i);
    public int getColumn();
    public int getRow();
    public double getX();
    public double getY();
    public void setCaret(double x, double y);
    public void moveHorizontally(int count);
    public void moveVertically(int count);
}
