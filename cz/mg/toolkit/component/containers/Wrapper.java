package cz.mg.toolkit.component.containers;


public abstract class Wrapper extends Panel {
    private final ContentPanel contentPanel = new ContentPanel();

    public Wrapper() {
        contentPanel.setParent(this);
    }
    
    public final Panel getContentPanel() {
        return contentPanel;
    }
}
