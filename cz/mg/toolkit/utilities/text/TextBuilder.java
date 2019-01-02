package cz.mg.toolkit.utilities.text;


public interface TextBuilder {
    public char get(int i);
    public int count();
    public void set(String string);
    public String substring(int beginIndex, int endIndex);
    public void delete(int beginIndex, int endIndex);
    public void insert(int i, String part);
}
