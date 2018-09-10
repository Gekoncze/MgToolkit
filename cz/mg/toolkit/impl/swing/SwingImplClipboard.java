package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.impl.ImplClipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;


public class SwingImplClipboard implements ImplClipboard {
    final java.awt.datatransfer.Clipboard swingClipboard;

    public SwingImplClipboard() {
        this.swingClipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    @Override
    public final void setText(String text){
        swingClipboard.setContents(new StringSelection(text), null);
    }
    
    @Override
    public final String getText(){
        try {
            return (String) swingClipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return null;
        }
    }
}
