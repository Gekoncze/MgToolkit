package cz.mg.toolkit.utilities.text;

import cz.mg.toolkit.utilities.text.textarrangements.MultilineTextArrangement;
import cz.mg.toolkit.utilities.text.textbuilders.DefaultTextBuilder;


public class TextModel {
    private TextBuilder textBuilder;
    private TextArrangement textArrangement;
    private int beginCaret = 0;
    private int endCaret = 0;

    public TextModel() {
        this.textBuilder = new DefaultTextBuilder("");
        this.textArrangement = new MultilineTextArrangement();
        this.textArrangement.setTextModel(this);
    }

    public final TextBuilder getTextBuilder() {
        return textBuilder;
    }

    public final void setTextBuilder(TextBuilder textBuilder) {
        this.textBuilder = textBuilder;
        textArrangement.onChange(Change.TEXT);
    }

    public final TextArrangement getTextArrangement() {
        return textArrangement;
    }

    public final void setTextArrangement(TextArrangement textArrangement) {
        this.textArrangement.setTextModel(null);
        this.textArrangement = textArrangement;
        this.textArrangement.setTextModel(this);
        this.textArrangement.onChange(Change.TEXT);
    }

    public final String getText() {
        return textBuilder.toString();
    }

    public final void setText(String text) {
        textBuilder.set(text);
        textArrangement.onChange(Change.TEXT);
    }

    public final int getBeginCaret() {
        fixBeginCaret();
        return beginCaret;
    }

    public final void setBeginCaret(int beginCaret) {
        this.beginCaret = beginCaret;
        textArrangement.onChange(Change.CARET);
    }

    public final int getEndCaret() {
        fixEndCaret();
        return endCaret;
    }

    public void setEndCaret(int endCaret) {
        this.endCaret = endCaret;
        textArrangement.onChange(Change.CARET);
    }

    public final String copy() {
        return textBuilder.substring(beginCaret, endCaret);
    }

    public final String cut(){
        String string = copy();
        delete();
        textArrangement.onChange(Change.TEXT);
        return string;
    }

    public final void delete() {
        textBuilder.delete(beginCaret, endCaret);
        joinFirstCaret();
        textArrangement.onChange(Change.TEXT);
    }

    public final void paste(String string) {
        delete();
        textBuilder.insert(endCaret, string);
        endCaret = endCaret + string.length();
        joinEndCaret();
        textArrangement.onChange(Change.TEXT);
    }

    public final void joinBeginCaret(){
        endCaret = beginCaret;
    }

    public final void joinEndCaret(){
        beginCaret = endCaret;
    }

    public final void joinFirstCaret(){
        if(beginCaret < endCaret) joinBeginCaret();
        else joinEndCaret();
    }

    public final void joinSecondCaret(){
        if(beginCaret > endCaret) joinBeginCaret();
        else joinEndCaret();
    }

    private void fixBeginCaret(){
        if(beginCaret < 0) beginCaret = 0;
        if(beginCaret > getTextBuilder().count()) beginCaret = getTextBuilder().count();
    }

    private void fixEndCaret(){
        if(endCaret < 0) endCaret = 0;
        if(endCaret > getTextBuilder().count()) endCaret = getTextBuilder().count();
    }

    public static enum Change {
        TEXT,
        CARET
    };
}
