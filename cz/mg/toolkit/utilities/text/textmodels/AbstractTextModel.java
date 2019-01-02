package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadonlyList;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.layout.reshapes.Reshape;
import cz.mg.toolkit.utilities.text.Caret;
import cz.mg.toolkit.utilities.text.Options;
import cz.mg.toolkit.utilities.text.TextBuilder;
import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.toolkit.utilities.text.TextPart;
import cz.mg.toolkit.utilities.text.textbuilders.DefaultTextBuilder;


public abstract class AbstractTextModel implements TextModel {
    protected final TextBuilder textBuilder = new DefaultTextBuilder("");
    protected final Caret beginCaret;
    protected final Caret endCaret;
    protected final Options options = new Options();
    protected List<TextPart> textParts = null;
    
    public AbstractTextModel() {
        beginCaret = createCaret();
        endCaret = createCaret();
    }
    
    public AbstractTextModel(String text) {
        this();
        textBuilder.set(text);
    }
    
    @Override
    public final String getText() {
        return textBuilder.toString();
    }

    @Override
    public final void setText(String text) {
        clearParts();
        textBuilder.set(text);
    }
    
    @Override
    public final Caret getBeginCaret() {
        return beginCaret;
    }

    @Override
    public final Caret getEndCaret() {
        return endCaret;
    }
    
    @Override
    public final Options getOptions() {
        return options;
    }
    
    @Override
    public final double getLineHeight(){
        return options.getFont().getHeight();
    }
    
    @Override
    public final ReadonlyList<TextPart> getTextParts() {
        createParts();
        return textParts;
    }
    
    @Override
    public final double getTextX() {
        return Reshape.align(options.getWidth(), getTextWidth(), options.getHorizontalAlignment(), options.getLeftPadding(), options.getRightPadding());
    }

    @Override
    public final double getTextY() {
        return Reshape.align(options.getHeight(), getTextHeight(), options.getVerticalAlignment(), options.getTopPadding(), options.getBottomPadding());
    }

    @Override
    public final double getTextWidth() {
        createParts();
        double maxWidth = 0;
        Font font = options.getFont();
        for(TextPart textPart : textParts) maxWidth = Math.max(maxWidth, font.getWidth(textPart.getText()));
        return maxWidth;
    }

    @Override
    public final double getTextHeight() {
        createParts();
        double totalHeight = options.getFont().getHeight() * textParts.count();
        return totalHeight;
    }

    @Override
    public double getPrefferedWidth() {
        return getTextWidth();
    }

    @Override
    public double getPrefferedHeight() {
        return getTextHeight();
    }
    
    @Override
    public final String copy() {
        return textBuilder.substring(beginCaret.getCaret(), endCaret.getCaret());
    }
    
    @Override
    public final String cut(){
        clearParts();
        String string = copy();
        delete();
        return string;
    }

    @Override
    public final void delete() {
        clearParts();
        textBuilder.delete(beginCaret.getCaret(), endCaret.getCaret());
        joinFirstCaret();
    }

    @Override
    public final void paste(String string) {
        clearParts();
        delete();
        textBuilder.insert(endCaret.getCaret(), string);
        endCaret.setCaret(endCaret.getCaret() + string.length());
        joinEndCaret();
    }
    
    protected abstract Caret createCaret();
    protected abstract void clearParts();
    protected abstract void createParts();
    
    protected void joinBeginCaret(){
        endCaret.setCaret(beginCaret.getCaret());
    }
    
    protected void joinEndCaret(){
        beginCaret.setCaret(endCaret.getCaret());
    }
    
    protected void joinFirstCaret(){
        if(beginCaret.getCaret() < endCaret.getCaret()) joinBeginCaret();
        else joinEndCaret();
    }
    
    protected void joinSecondCaret(){
        if(beginCaret.getCaret() > endCaret.getCaret()) joinBeginCaret();
        else joinEndCaret();
    }
    
    protected class ImplTextPart implements TextPart {
        private final String text;
        private final int row;

        public ImplTextPart(String text, int row) {
            this.text = text;
            this.row = row;
        }
        
        @Override
        public String getText() {
            return text;
        }

        @Override
        public double getX() {
            return Reshape.align(options.getWidth(), options.getFont().getWidth(text), options.getHorizontalAlignment(), options.getLeftPadding(), options.getRightPadding());
        }

        @Override
        public double getY() {
            return getTextY() + row*getLineHeight();
        }
    }
}
