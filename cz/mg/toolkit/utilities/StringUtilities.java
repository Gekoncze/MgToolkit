package cz.mg.toolkit.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.graphics.Font;


public class StringUtilities {
    public static List<String> splitLines(String text, boolean newline){
        ChainList<String> lines = new ChainList<>();
        int begin = 0;
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == '\n'){
                int end = i;
                if(!newline) lines.addLast(text.substring(begin, end));
                end++;
                if(newline) lines.addLast(text.substring(begin, end));
                begin = end;
            }
        }
        if(begin < text.length()) lines.addLast(text.substring(begin));
        else lines.addLast("");
        return lines;
    }
    
    public static String substring(StringBuilder string, int beginIndex, int endIndex){
        if(beginIndex == endIndex) return "";
        if(beginIndex > endIndex){ int buffer = beginIndex; beginIndex = endIndex; endIndex = buffer; }
        if(string == null) return "";
        if(string.length() <= 0) return "";
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > string.length()) beginIndex = string.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex >= string.length()) endIndex = string.length();
        try {
            return string.substring(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
            return "";
        }
    }
    
    public static void delete(StringBuilder string, int beginIndex, int endIndex){
        if(beginIndex == endIndex) return;
        if(beginIndex > endIndex){ int buffer = beginIndex; beginIndex = endIndex; endIndex = buffer; }
        if(string == null) return;
        if(string.length() <= 0) return;
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > string.length()) beginIndex = string.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex > string.length()) endIndex = string.length();
        if(beginIndex >= endIndex) return;
        try {
            string.delete(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
        }
    }
    
    public static void insert(StringBuilder string, int i, String part){
        if(i < 0) i = 0;
        if(i > string.length()) i = string.length();
        string.insert(i, part);
    }
    
    public static int getClosestCharacter(Font font, String string, double px){
        int id = 0;
        double minDistance = Math.abs(px - 0);
        for(int i = 1; i <= string.length(); i++){
            double currentCaretPosition = font.getWidth(string.substring(0, i));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                id = i;
            }
        }
        return id;
    }
    
    public static int getClosestCharacter(Font font, StringBuilder string, double px){
        int id = 0;
        double minDistance = Math.abs(px - 0);
        for(int i = 1; i <= string.length(); i++){
            double currentCaretPosition = font.getWidth(string.substring(0, i));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                id = i;
            }
        }
        return id;
    }
    
    public static int getClosestLine(Font font, int lineCount, double py){
        int line = (int) (py / font.getHeight());
        if(line >= lineCount) line = lineCount - 1;
        if(line < 0) line = 0;
        return line;
    }
}
