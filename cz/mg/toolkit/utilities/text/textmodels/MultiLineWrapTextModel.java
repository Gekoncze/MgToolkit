package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.toolkit.utilities.text.TextModel;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadonlyList;
import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.layout.reshapes.Reshape;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.Caret;
import cz.mg.toolkit.utilities.text.Options;
import cz.mg.toolkit.utilities.text.TextPart;


public class MultiLineWrapTextModel implements TextModel {
    private StringBuilder text = new StringBuilder("");
    private List<TextPart> textParts = null;
    private final Options options = new Options();
    private final Caret beginCaret = new ImplCaret();
    private final Caret endCaret = new ImplCaret();
    private Options lastOptions = options.copy();
    private double lastFontSize = 0;

    public MultiLineWrapTextModel() {
    }
    
    public MultiLineWrapTextModel(String text) {
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
        updateParts();
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
        clearParts();
        String string = copy();
        delete();
        return string;
    }

    @Override
    public void delete() {
        clearParts();
        StringUtilities.delete(this.text, beginCaret.getCaret(), endCaret.getCaret());
        joinFirstCaret();
    }

    @Override
    public void paste(String string) {
        clearParts();
        delete();
        StringUtilities.insert(this.text, endCaret.getCaret(), string);
        endCaret.setCaret(endCaret.getCaret() + string.length());
        joinEndCaret();
    }

    @Override
    public boolean update() {
        if(!Options.equals(options, lastOptions) || lastFontSize != options.getFont().getHeight()){
            clearParts();
            updateParts();
            return true;
        }
        return false;
    }

    @Override
    public Options getOptions() {
        return options;
    }
    
    private void clearParts(){
        textParts = null;
    }
    
    private void updateParts(){
        lastOptions = options.copy();
        lastFontSize = options.getFont().getHeight();
        
        if(textParts != null) return;
        textParts = new CachedChainList<>();
        int row = 0;
        String currentLine = "";
        for(int i = 0; i < text.length(); i++){
            char ch = text.charAt(i);
            if(ch == '\n'){
                textParts.addLast(new ImplTextPart(currentLine, row, true));
                currentLine = "";
                row++;
            } else {
                String extendedLine = currentLine + ch;
                if(options.getFont().getWidth(extendedLine) > options.getWidth()){
                    textParts.addLast(new ImplTextPart(currentLine, row, false));
                    currentLine = "" + ch;
                    row++;
                } else {
                    currentLine = extendedLine;
                }
            }
        }
        textParts.addLast(new ImplTextPart(currentLine, row, false));
    }
    
    @Override
    public double getTextX() {
        updateParts();
        double minX = Double.MAX_VALUE;
        for(TextPart part : textParts) minX = Math.min(minX, part.getX());
        return minX;
    }

    @Override
    public double getTextY() {
        return textParts.getFirst().getY();
    }

    @Override
    public double getTextWidth() {
        return 0;
    }

    @Override
    public double getTextHeight() {
        updateParts();
        double totalHeight = options.getFont().getHeight() * textParts.count();
        return totalHeight;
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
            return caretToCarets(caret)[0];
        }

        @Override
        public int getRow() {
            return caretToCarets(caret)[1];
        }

        @Override
        public double getX() {
            updateParts();
            int[] cs = caretToCarets(caret);
            TextPart part = textParts.get(cs[1]);
            return part.getX() + options.getFont().getWidth(part.getText().substring(0, cs[0]));
        }

        @Override
        public double getY() {
            updateParts();
            int[] cs = caretToCarets(caret);
            TextPart part = textParts.get(cs[1]);
            return part.getY();
        }

        @Override
        public void setCaret(double x, double y) {
            double lpy = y - getVerticalTextPosition();
            int line = StringUtilities.getClosestLine(options.getFont(), textParts.count(), lpy);

            TextPart part = textParts.get(line);
            
            double lpx = x - part.getX();
            int character = StringUtilities.getClosestCharacter(options.getFont(), part.getText(), lpx);

            caret = caretsToCaret(character, line);
            fix();
        }

        @Override
        public void moveHorizontally(int count) {
            caret += count;
            fix();
        }

        @Override
        public void moveVertically(int count) {
            double x = getX();
            double y = getY() + 0.5*getLineHeight();
            y += count*getLineHeight();
            setCaret(x, y);
            fix();
        }
        
        private void fix(){
            if(caret < 0) caret = 0;
            if(caret > text.length()) caret = text.length();
        }
    }
    
    public int caretsToCaret(int ix, int iy) {
        updateParts();
        int caret = 0;
        for(int i = 0; i < iy; i++){
            caret += textParts.get(i).getText().length() + 1;
        }
        return caret + ix;
    }

    public int[] caretToCarets(int i) {
        updateParts();
        if(i <= 0) return new int[]{0, 0};
        if(i > text.length()) i = text.length();
        
        int iy = 0;
        int ix = 0;
        for(TextPart part : textParts){
            if((i - part.getText().length()) > 0){
                i -= part.getText().length() + 1;
                iy++;
            } else {
                ix = i;
                break;
            }
        }
        return new int[]{ix, iy};
    }
    
    private class ImplTextPart implements TextPart {
        private final String text;
        private final int row;
        private boolean newlined;

        public ImplTextPart(String text, int row, boolean newlined) {
            this.text = text;
            this.row = row;
            this.newlined = newlined;
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
            return getVerticalTextPosition() + row*getLineHeight();
        }

        public boolean isNewlined() {
            return newlined;
        }
    }
    
    private double getVerticalTextPosition(){
        return Reshape.align(options.getHeight(), getTextHeight(), options.getVerticalAlignment(), options.getTopPadding(), options.getBottomPadding());
    }
    
    private void joinBeginCaret(){
        endCaret.setCaret(beginCaret.getCaret());
    }
    
    private void joinEndCaret(){
        beginCaret.setCaret(endCaret.getCaret());
    }
    
    private void joinFirstCaret(){
        if(beginCaret.getCaret() < endCaret.getCaret()) joinBeginCaret();
        else joinEndCaret();
    }
    
    private void joinSecondCaret(){
        if(beginCaret.getCaret() > endCaret.getCaret()) joinBeginCaret();
        else joinEndCaret();
    }
}
