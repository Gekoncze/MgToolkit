package cz.mg.toolkit.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;


public class StringUtilities {
    public static List<String> splitLines(String text){
        ChainList<String> lines = new ChainList<>();
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
        return lines;
    }
}
