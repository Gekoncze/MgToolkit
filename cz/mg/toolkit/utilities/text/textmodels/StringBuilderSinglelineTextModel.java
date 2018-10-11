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
        if(text.length() <= 0) return "";
        if(iBegin < 0) iBegin = 0;
        if(iBegin >= text.length()) iBegin = text.length() - 1;
        if(iEnd < 0) iEnd = 0;
        if(iEnd >= text.length()) iEnd = text.length() - 1;
        if(iBegin >= iEnd) return "";
        return text.substring(iBegin, iEnd);
    }
    
    @Override
    public void insert(int i, String text) {
        if(i < 0) return;
        if(i > text.length()) return;
        this.text.insert(i, text);
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        if(text.length() <= 0) return;
        if(iBegin < 0) iBegin = 0;
        if(iBegin >= text.length()) iBegin = text.length() - 1;
        if(iEnd < 0) iEnd = 0;
        if(iEnd >= text.length()) iEnd = text.length() - 1;
        if(iBegin >= iEnd) return;
        this.text.delete(iBegin, iEnd);
    }
}
