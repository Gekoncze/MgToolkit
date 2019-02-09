package cz.mg.toolkit.utilities.text.textarrangements;

import cz.mg.collections.list.ReadonlyList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.layout.reshapes.Reshape;
import cz.mg.toolkit.utilities.text.*;


public abstract class AbstractTextArrangement implements TextArrangement {
    protected TextModel textModel;
    protected final Options options = new Options();
    protected ChainList<TextPart> parts = null;
    protected final Caret beginCaret;
    protected final Caret endCaret;

    public AbstractTextArrangement() {
        beginCaret = createBeginCaret();
        endCaret = createEndCaret();
    }

    @Override
    public final void setTextModel(TextModel textModel) {
        this.textModel = textModel;
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
    public final ReadonlyList<TextPart> getParts() {
        createParts();
        return parts;
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
        for(TextPart textPart : parts) maxWidth = Math.max(maxWidth, font.getWidth(textPart.getText()));
        return maxWidth;
    }

    @Override
    public final double getTextHeight() {
        createParts();
        double totalHeight = options.getFont().getHeight() * parts.count();
        double totalGap = (parts.count() - 1) * options.getVerticalSpacing();
        return totalHeight;
    }

    @Override
    public final void onChange(TextModel.Change change) {
        switch (change){
            case TEXT: clearParts();
            case CARET: ;
        }
    }

    protected final TextBuilder getTextBuilder(){
        return textModel.getTextBuilder();
    }

    private final Caret createBeginCaret() {
        return new AbstractCaret(){
            @Override
            public int getCaret() {
                return textModel.getBeginCaret();
            }

            @Override
            public void setCaret(int i) {
                textModel.setBeginCaret(i);
            }
        };
    }

    private final Caret createEndCaret() {
        return new AbstractCaret(){
            @Override
            public int getCaret() {
                return textModel.getEndCaret();
            }

            @Override
            public void setCaret(int i) {
                textModel.setEndCaret(i);
            }
        };
    }

    protected class AbstractTextPart implements TextPart {
        private final String text;
        private final int row;
        private final int beginCaret;
        private final int endCaret;

        public AbstractTextPart(String text, int row, int beginCaret, boolean newlined) {
            this.text = text;
            this.row = row;
            this.beginCaret = beginCaret;
            this.endCaret = beginCaret + (newlined ? text.length() : (text.length() - 1));
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
            return getTextY() + row*(getLineHeight() + options.getVerticalSpacing());
        }

        public int getBeginCaret() {
            return beginCaret;
        }

        public int getEndCaret() {
            return endCaret;
        }

        public int getRow() {
            return row;
        }
    }

    protected abstract class AbstractCaret implements Caret {
        @Override
        public abstract int getCaret();

        @Override
        public abstract void setCaret(int i);

        @Override
        public int getColumn() {
            return caretToCarets(getCaret())[0];
        }

        @Override
        public int getRow() {
            return caretToCarets(getCaret())[1];
        }

        @Override
        public double getX() {
            createParts();
            int[] cs = caretToCarets(getCaret());
            TextPart part = parts.get(cs[1]);
            return part.getX() + options.getFont().getWidth(part.getText().substring(0, cs[0]));
        }

        @Override
        public double getY() {
            createParts();
            int[] cs = caretToCarets(getCaret());
            TextPart part = parts.get(cs[1]);
            return part.getY();
        }

        @Override
        public void setCaret(double x, double y) {
            double lpy = y - getTextY();
            int line = getClosestLine(options.getFont(), parts.count(), lpy);

            AbstractTextPart part = (AbstractTextPart) parts.get(line);

            double lpx = x - part.getX();
            int character = getClosestCharacter(options.getFont(), part, lpx);

            setCaret(caretsToCaret(character, line));
        }

        @Override
        public void moveHorizontally(int count) {
            setCaret(getCaret() + count);
        }

        @Override
        public void moveVertically(int count) {
            double x = getX();
            double y = getY() + 0.5*getLineHeight();
            y += count*getLineHeight();
            setCaret(x, y);
        }

    }

    private final int caretsToCaret(int ix, int iy) {
        createParts();
        AbstractTextPart part = (AbstractTextPart) parts.get(iy);
        return part.getBeginCaret() + ix;
    }

    private final int[] caretToCarets(int i) {
        createParts();
        for(ChainListItem<TextPart> item = parts.getLastItem(); item != null; item = item.getPreviousItem()){
            AbstractTextPart part = (AbstractTextPart) item.getData();
            if(i >= part.getBeginCaret()){
                int column = i - part.getBeginCaret();
                int row = part.getRow();
                return new int[]{column, row};
            }
        }
        return new int[]{0, 0};
    }

    protected final void clearParts(){
        parts = null;
    }

    private int getClosestCharacter(Font font, AbstractTextPart part, double px){
        double minDistance = Math.abs(px - 0);
        int min = part.getBeginCaret();
        int max = part.getEndCaret();
        int id = min;
        TextBuilder text = textModel.getTextBuilder();
        for(int i = min + 1; i <= max; i++){
            double currentCaretPosition = font.getWidth(text.substring(min, i));
            double dx = Math.abs(px - currentCaretPosition);
            if(dx < minDistance){
                minDistance = dx;
                id = i;
            }
        }
        id -= min;
        return id;
    }

    private int getClosestLine(Font font, int lineCount, double py){
        int line = (int) (py / (font.getHeight() + options.getVerticalSpacing()));
        if(line >= lineCount) line = lineCount - 1;
        if(line < 0) line = 0;
        return line;
    }

    protected abstract void createParts();
}
