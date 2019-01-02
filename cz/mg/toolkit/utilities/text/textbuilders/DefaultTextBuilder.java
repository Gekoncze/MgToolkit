package cz.mg.toolkit.utilities.text.textbuilders;

import cz.mg.toolkit.utilities.text.TextBuilder;


public class DefaultTextBuilder implements TextBuilder {
    private StringBuilder text;

    public DefaultTextBuilder() {
        text = new StringBuilder();
    }
    
    public DefaultTextBuilder(String string) {
        this.text = new StringBuilder(string);
    }

    @Override
    public char get(int i) {
        return text.charAt(i);
    }
    
    @Override
    public int count() {
        return text.length();
    }

    @Override
    public void set(String string) {
        if(string == null) string = "";
        text = new StringBuilder(string);
    }

    @Override
    public String substring(int beginIndex, int endIndex){
        if(beginIndex == endIndex) return "";
        if(beginIndex > endIndex){ int buffer = beginIndex; beginIndex = endIndex; endIndex = buffer; }
        if(text == null) return "";
        if(text.length() <= 0) return "";
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > text.length()) beginIndex = text.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex >= text.length()) endIndex = text.length();
        try {
            return text.substring(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
            return "";
        }
    }
    
    @Override
    public void delete(int beginIndex, int endIndex){
        if(beginIndex == endIndex) return;
        if(beginIndex > endIndex){ int buffer = beginIndex; beginIndex = endIndex; endIndex = buffer; }
        if(text == null) return;
        if(text.length() <= 0) return;
        if(beginIndex < 0) beginIndex = 0;
        if(beginIndex > text.length()) beginIndex = text.length();
        if(endIndex < 0) endIndex = 0;
        if(endIndex > text.length()) endIndex = text.length();
        if(beginIndex >= endIndex) return;
        try {
            text.delete(beginIndex, endIndex);
        } catch(IndexOutOfBoundsException e){
        }
    }
    
    @Override
    public void insert(int i, String part){
        if(i < 0) i = 0;
        if(i > text.length()) i = text.length();
        text.insert(i, part);
    }
    
    @Override
    public String toString() {
        return text.toString();
    }
}
