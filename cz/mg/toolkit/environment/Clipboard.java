package cz.mg.toolkit.environment;

import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.ImplClipboard;


public class Clipboard {
    private static Clipboard instance = null;
    private final ImplClipboard implClipboard;

    private Clipboard() {
        this.implClipboard = Impl.getImplApi().createClipboard();
    }
    
    public static Clipboard getInstance() {
        if(instance == null) instance = new Clipboard();
        return instance;
    }
    
    public final void setText(String text){
        implClipboard.setText(text);
    }
    
    public final String getText(){
        return implClipboard.getText();
    }
}
