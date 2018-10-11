package cz.mg.toolkit.utilities.text.textmodels;


public class FailsafeStringBuilder {
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
