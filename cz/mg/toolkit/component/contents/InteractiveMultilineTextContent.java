package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.event.adapters.BeforeDrawAdapter;
import cz.mg.toolkit.event.events.BeforeDrawEvent;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.setHighlighted;
import cz.mg.toolkit.utilities.text.textmodels.StringBuilderMultilineTextModel;


public class InteractiveMultilineTextContent extends MultilineTextContent {
    private int caretX = 0;
    private int caretY = 0;
    private int selectionCaretX = 0;
    private int selectionCaretY = 0;
    private boolean editable = false;
    private boolean partialFocus = false; // for read only selection and copy support
    
    public InteractiveMultilineTextContent() {
        setTextModel(new StringBuilderMultilineTextModel());
        addEventListeners();
    }

    public InteractiveMultilineTextContent(String text) {
        setTextModel(new StringBuilderMultilineTextModel());
        setText(text);
        addEventListeners();
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new BeforeDrawAdapter() {
            @Override
            public void onEventEnter(BeforeDrawEvent e) {
                setHighlighted(InteractiveMultilineTextContent.this, hasKeyboardFocus());
            }
        });
        
        todo;
    }
}
