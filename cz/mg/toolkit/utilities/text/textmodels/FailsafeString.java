package cz.mg.toolkit.utilities.text.textmodels;


public class FailsafeString {
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
}
