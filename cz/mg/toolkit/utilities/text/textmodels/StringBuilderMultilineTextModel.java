package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.utilities.text.MultilineTextModel;


public class StringBuilderMultilineTextModel implements MultilineTextModel {
    private StringBuilder text = new StringBuilder("");
    private ChainList<StringBuilder> linesCache = null;
    
    @Override
    public String getText() {
        updateFromLinesCache();
        return text.toString();
    }

    @Override
    public void setText(String text) {
        if(text == null) this.text = new StringBuilder("");
        else this.text = new StringBuilder(text);
        linesCache = null;
    }

    @Override
    public int characterCount() {
        updateFromLinesCache();
        return text.length();
    }
    
    @Override
    public String getText(int iBegin, int iEnd) {
        updateFromLinesCache();
        return text.substring(iBegin, iEnd);
    }

    @Override
    public void insert(int i, String text) {
        todo;
    }

    @Override
    public void remove(int iBegin, int iEnd) {
        todo;
    }
    
    @Override
    public String getText(int ixBegin, int iyBegin, int ixEnd, int iyEnd) {
        return getText(xy2i(ixBegin, iyBegin), xy2i(ixEnd, iyEnd)); todo;
    }

    @Override
    public void insert(int x, int y, String s) {
         todo;
    }

    @Override
    public void remove(int ixBegin, int iyBegin, int ixEnd, int iyEnd) {
         todo;
    }

    @Override
    public String getLine(int y) {
        updateLinesCache();
        return linesCache.get(y).toString();
    }

    @Override
    public int lineCount() {
        updateLinesCache();
        return linesCache.count();
    }
    
    private void updateLinesCache(){
        if(text == null) ???;
        if(linesCache != null) return; <--- todo - check;
        
        
        
        linesCache = new ChainList<>();
        int begin = 0;
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == '\n'){
                int end = i;
                linesCache.addLast(new StringBuilder(text.substring(begin, end)));
                begin = end + 1;
            }
        }
        if(begin < text.length()) linesCache.addLast(new StringBuilder(text.substring(begin)));
        else linesCache.addLast(new StringBuilder(""));
    }
    
    private void updateFromLinesCache(){
        todo;
    }
    
    private int xy2i(int ix, int iy){
        if(text == null) ???;
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
