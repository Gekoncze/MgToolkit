package cz.mg.toolkit.environment;

import cz.mg.toolkit.impl.ImplClipboard;
import cz.mg.toolkit.impl.swing.SwingImplClipboard;


public class Clipboard {
    private static Clipboard instance = null;
    private final ImplClipboard implClipboard;

    private Clipboard() {
        this.implClipboard = new SwingImplClipboard();
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
