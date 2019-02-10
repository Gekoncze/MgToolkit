package cz.mg.toolkit.component.controls.menuitems;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.controls.CheckBox;
import cz.mg.toolkit.component.controls.RadioButton;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.event.adapters.KeyboardButtonAdapter;
import cz.mg.toolkit.event.events.KeyboardButtonEvent;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.utilities.KeyboardShortcut;
import cz.mg.toolkit.utilities.SelectionGroup;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class StandardMenuItem extends ActionMenuItem {
    public static final String DEFAULT_DESIGN_NAME = "standard menu item";
    
    private final Icon imageContent = new Icon();
    private final RadioButton radioButton = new RadioButton();
    private final CheckBox checkBox = new CheckBox();
    private final Description description = new Description();
    private final Shortcut shortcut = new Shortcut();
    
    private final Component[] components = new Component[]{
        imageContent,
        radioButton,
        checkBox,
        description,
        shortcut
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
        setSizePolicy(imageContent, new FixedSizePolicy());
        imageContent.setAutosize(false);
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
        
        if(text == null) setHidden(description, true);
        setColumn(description, 2);
        description.setText(text);
        
        if(keyboardShortcut == null) setHidden(shortcut, true);
        setColumn(shortcut, 3);
        if(keyboardShortcut != null) shortcut.setText(keyboardShortcut.toString());
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
    
    public static class Icon extends ImageContent {
        public static final String DEFAULT_DESIGN_NAME = "standard menu item icon";
    }
    
    public static class Description extends TextContent {
        public static final String DEFAULT_DESIGN_NAME = "standard menu item description";
    }
    
    public static class Shortcut extends TextContent {
        public static final String DEFAULT_DESIGN_NAME = "standard menu item shortcut";
    }
}
