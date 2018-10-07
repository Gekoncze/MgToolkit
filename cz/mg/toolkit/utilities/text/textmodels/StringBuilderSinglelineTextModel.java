package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.toolkit.utilities.text.SinglelineTextModel;


public class StringBuilderSinglelineTextModel implements SinglelineTextModel {
    private StringBuilder text = new StringBuilder("");
    
    @Override
    public String getText() {
        return text.toString();
    }
    
    @Override
    public void setText(String text) {
        if(text == null) this.text = new StringBuilder("");
        else this.text = new StringBuilder(text);
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
        this.text.insert(i, text);
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        this.text.delete(iBegin, iEnd);
    }
}
