package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.toolkit.utilities.text.SinglelineTextModel;


public class StringSinglelineTextModel implements SinglelineTextModel {
    private String text = "";
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public void setText(String text) {
        if(text == null) this.text = "";
        else this.text = text;
    }

    @Override
    public int characterCount() {
        return text.length();
    }
    
    @Override
    public String getText(int iBegin, int iEnd) {
        return text.substring(iBegin, iEnd);
    }
    
    @Override
    public void insert(int i, String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        throw new UnsupportedOperationException();
    }
}
