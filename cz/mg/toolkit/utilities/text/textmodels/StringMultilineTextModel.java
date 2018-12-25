package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.MultilineTextModel;


public class StringMultilineTextModel implements MultilineTextModel {
    private String text = "";
    protected List<String> lines = null;
    
    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        if(text == null) this.text = "";
        else this.text = text;
        lines = null;
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
    public String getLine(int y) {
        updateLines();
        if(y < 0) y = 0;
        if(y >= lines.count()) y = lines.count() - 1;
        return lines.get(y);
    }

    @Override
    public int lineCount() {
        updateLines();
        return lines.count();
    }

    @Override
    public int caretsToCaret(int ix, int iy) {
        int caret = 0;
        for(int l = 0; l < iy; l++){
            caret += getLine(l).length() + 1;
        }
        return caret + ix;
    }
    
    protected void updateLines(){
        if(lines != null) return;
        lines = new CachedChainList<>(StringUtilities.splitLines(text));
    }
}
