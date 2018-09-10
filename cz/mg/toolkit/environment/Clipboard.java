package cz.mg.toolkit.environment;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;


public class Clipboard {
    private static Clipboard instance = null;
    private final java.awt.datatransfer.Clipboard implClipboard;

    public Clipboard(java.awt.datatransfer.Clipboard implClipboard) {
        this.implClipboard = implClipboard;
    }
    
    public static Clipboard getInstance() {
        if(instance == null) instance = new Clipboard(java.awt.Toolkit.getDefaultToolkit().getSystemClipboard());
        return instance;
    }
    
    public final void setText(String text){
        implClipboard.setContents(new StringSelection(text), null);
    }
    
    public final String getText(){
        try {
            return (String) implClipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return null;
        }
    }
}
