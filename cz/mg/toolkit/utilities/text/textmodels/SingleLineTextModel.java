package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadonlyList;
import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.layout.reshapes.Reshape;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.Caret;
import cz.mg.toolkit.utilities.text.Options;
import cz.mg.toolkit.utilities.text.TextPart;


public class SingleLineTextModel implements TextModel {
    private StringBuilder text = new StringBuilder("");
    private final List<TextPart> textParts = new CachedChainList<>(new ImplTextPart());
    private final Options options = new Options();
    private final Caret beginCaret = new ImplCaret();
    private final Caret endCaret = new ImplCaret();

    public SingleLineTextModel() {
    }
    
    public SingleLineTextModel(String text) {
        setText(text);
    }
    
    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void setText(String text) {
        if(text == null) this.text = new StringBuilder("");
        else this.text = new StringBuilder(text);
    }

    @Override
    public ReadonlyList<TextPart> getTextParts() {
        return textParts;
    }

    @Override
    public Caret getBeginCaret() {
        return beginCaret;
    }

    @Override
    public Caret getEndCaret() {
        return endCaret;
    }

    @Override
    public String copy() {
        return StringUtilities.substring(text, beginCaret.getCaret(), endCaret.getCaret());
    }
    
    @Override
    public String cut(){
        String string = copy();
        delete();
        return string;
    }

    @Override
    public void delete() {
        StringUtilities.delete(this.text, beginCaret.getCaret(), endCaret.getCaret());
        endCaret.setCaret(beginCaret.getCaret());
    }

    @Override
    public void paste(String string) {
        delete();
        StringUtilities.insert(this.text, endCaret.getCaret(), string);
        endCaret.setCaret(endCaret.getCaret() + string.length());
        beginCaret.setCaret(endCaret.getCaret());
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public double getTextWidth() {
        return options.getFont().getWidth(text.toString());
    }

    @Override
    public double getTextHeight() {
        return options.getFont().getHeight();
    }
    
    @Override
    public double getLineHeight(){
        return options.getFont().getHeight();
    }
    
    private class ImplCaret implements Caret {
        private int caret = 0;
        
        @Override
        public int getCaret() {
            fix();
            return caret;
        }

        @Override
        public void setCaret(int i) {
            this.caret = i;
            fix();
        }

        @Override
        public int getColumn() {
            return caret;
        }

        @Override
        public int getRow() {
            return 0;
        }

        @Override
        public double getX() {
            double lineX = textParts.getFirst().getX();
            double positionX = options.getFont().getWidth(text.substring(0, getCaret()));
            return lineX + positionX;
        }

        @Override
        public double getY() {
            double lineY = textParts.getFirst().getY();
            return lineY;
        }

        @Override
        public void setCaret(double x, double y) {
            x -= textParts.getFirst().getX();
            setCaret(StringUtilities.getClosestCharacter(options.getFont(), text, x));
        }

        @Override
        public void moveHorizontally(int count) {
            caret += count;
            fix();
        }

        @Override
        public void moveVertically(int count) {
            fix();
        }
        
        private void fix(){
            if(caret < 0) caret = 0;
            if(caret > text.length()) caret = text.length();
        }
    }
    
    private class ImplTextPart implements TextPart {
        public ImplTextPart() {
        }
        
        @Override
        public String getText() {
            return text.toString();
        }

        @Override
        public double getX() {
            return Reshape.align(options.getWidth(), getTextWidth(), options.getHorizontalAlignment(), options.getLeftPadding(), options.getRightPadding());
        }

        @Override
        public double getY() {
            return Reshape.align(options.getHeight(), getTextHeight(), options.getVerticalAlignment(), options.getTopPadding(), options.getBottomPadding());
        }
    }
}
