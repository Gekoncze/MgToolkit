package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.utilities.text.MultilineTextModel;


public class StringMultilineTextModel implements MultilineTextModel {
    private String text = "";
    private ChainList<String> lines = null;
    
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
        return FailsafeString.substring(text, iBegin, iEnd);
    }

    @Override
    public void insert(int i, String text) {
    }

    @Override
    public void remove(int iBegin, int iEnd) {
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
        lines = new ChainList<>();
        int begin = 0;
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == '\n'){
                int end = i;
                lines.addLast(text.substring(begin, end));
                begin = end + 1;
            }
        }
        if(begin < text.length()) lines.addLast(text.substring(begin));
        else lines.addLast("");
    }
    
    private int xy2i(int ix, int iy){
        int x = 0;
        int y = 0;
        for(int i = 0; i < text.length(); i++){
            if(y > iy) return i;
            if(y == iy && x >= ix) return i;
            
            char ch = text.charAt(i);
            if(ch == '\n'){
                y++;
                x = 0;
            } else {
                x++;
            }
        }
        return text.length();
    }
}
