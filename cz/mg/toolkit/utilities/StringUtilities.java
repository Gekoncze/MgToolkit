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
    
    public static String substring(String string, int beginIndex, int endIndex){
        if(string == null) return "";
        if(string.length() <= 0) return "";
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > string.length()) beginIndex = string.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex > string.length()) endIndex = string.length();
        if(beginIndex >= endIndex) return "";
        try {
            return string.substring(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
            return "";
        }
    }
    
    public static String substring(StringBuilder string, int beginIndex, int endIndex){
        if(string == null) return "";
        if(string.length() <= 0) return "";
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > string.length()) beginIndex = string.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex >= string.length()) endIndex = string.length();
        if(beginIndex > endIndex) return "";
        try {
            return string.substring(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
            return "";
        }
    }
    
    public static void delete(StringBuilder string, int beginIndex, int endIndex){
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
}
