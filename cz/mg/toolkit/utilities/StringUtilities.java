package cz.mg.toolkit.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.utilities.text.TextBuilder;


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
    
    public static int getClosestCharacter(Font font, TextBuilder string, double px){
        int id = 0;
        double minDistance = Math.abs(px - 0);
        for(int i = 1; i <= string.count(); i++){
            double currentCaretPosition = font.getWidth(string.substring(0, i));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                id = i;
            }
        }
        return id;
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
    
    public static int getClosestLine(Font font, int lineCount, double py){
        int line = (int) (py / font.getHeight());
        if(line >= lineCount) line = lineCount - 1;
        if(line < 0) line = 0;
        return line;
    }
}
