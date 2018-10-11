package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.List;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.MultilineTextModel;


public class StringBuilderMultilineTextModel implements MultilineTextModel {
    private StringBuilder text = new StringBuilder("");
    private List<String> lines = null;
    
    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void setText(String text) {
        lines = null;
        if(text == null) this.text = new StringBuilder("");
        else this.text = new StringBuilder(text);
    }

    @Override
    public int characterCount() {
        return text.length();
    }
    
    @Override
    public String getText(int iBegin, int iEnd) {
        return FailsafeStringBuilder.substring(text, iBegin, iEnd);
    }

    @Override
    public void insert(int i, String part) {
        lines = null;
        FailsafeStringBuilder.insert(text, i, part);
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        lines = null;
        FailsafeStringBuilder.delete(text, iBegin, iEnd);
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
    
    private void updateLines(){
        if(lines != null) return;
        lines = StringUtilities.splitLines(text.toString());
    }
}
