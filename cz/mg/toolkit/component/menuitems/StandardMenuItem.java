package cz.mg.toolkit.component.menuitems;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.components.CheckBox;
import cz.mg.toolkit.component.components.RadioButton;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.utilities.KeyboardShortcut;
import cz.mg.toolkit.utilities.SelectionGroup;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class StandardMenuItem extends ActionMenuItem {
    private static final int DEFAULT_ICON_SIZE = 16;
    
    private final ImageContent imageContent = new ImageContent();
    private final RadioButton radioButton = new RadioButton();
    private final CheckBox checkBox = new CheckBox();
    private final TextContent textContent = new TextContent();
    private final TextContent keyTextContent = new TextContent();
    
    private final Component[] components = new Component[]{
        imageContent,
        radioButton,
        checkBox,
        textContent,
        keyTextContent
    };
    
    private final KeyboardShortcut keyboardShortcut;
    
    public StandardMenuItem(Image icon, String text, KeyboardShortcut keyboardShortcut, Boolean checked, SelectionGroup selectionGroup) {
        this.keyboardShortcut = keyboardShortcut;
        initComponents(icon, text, keyboardShortcut, checked, selectionGroup);
        addEventListeners();
    }
    
    private void initComponents(Image icon, String text, KeyboardShortcut keyboardShortcut, Boolean checked, SelectionGroup selectionGroup) {
        if(icon == null) setHidden(imageContent, true);
        setColumn(imageContent, 0);
        imageContent.setContentWidth(DEFAULT_ICON_SIZE);
        imageContent.setContentHeight(DEFAULT_ICON_SIZE);
        imageContent.setUsePrefferedSize(false);
        imageContent.setImage(icon);
        
        if(checked == null) setHidden(radioButton, true);
        if(checked == null) setHidden(checkBox, true);
        if(selectionGroup == null) setHidden(radioButton, true);
        if(selectionGroup != null) setHidden(checkBox, true);
        setColumn(radioButton, 1);
        setColumn(checkBox, 1);
        if(checked != null) radioButton.setSelected(checked);
        if(checked != null) checkBox.setSelected(checked);
        if(selectionGroup != null) radioButton.setSelectionGroup(selectionGroup);
        
        if(text == null) setHidden(textContent, true);
        setColumn(textContent, 2);
        textContent.setText(text);
        setContext(textContent, "menu item");
        
        if(keyboardShortcut == null) setHidden(keyTextContent, true);
        setColumn(keyTextContent, 3);
        if(keyboardShortcut != null) keyTextContent.setText(keyboardShortcut.toString());
        setContext(keyTextContent, "menu item shortcut");
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new KeyboardButtonAdapter() {
            @Override
            public void onKeyboardButtonEventEnter(KeyboardButtonEvent e) {
                if(keyboardShortcut != null && keyboardShortcut.matches(e)){
                    e.consume();
                    trigger();
                }
            }
        });
    }
    
    @Override
    public Component[] getComponents() {
        return components;
    }

    @Override
    public void trigger() {
        if(!isHidden(radioButton)) radioButton.trigger();
        else if(!isHidden(checkBox)) checkBox.trigger();
        else super.trigger();
    }
}
