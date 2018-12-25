package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.EditableTextModel;
import cz.mg.toolkit.utilities.text.SinglelineTextModel;


public class StringBuilderSinglelineTextModel implements SinglelineTextModel, EditableTextModel {
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
        return StringUtilities.substring(text, iBegin, iEnd);
    }
    
    @Override
    public void insert(int i, String text) {
        StringUtilities.insert(this.text, i, text);
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        StringUtilities.delete(this.text, iBegin, iEnd);
    }
}
