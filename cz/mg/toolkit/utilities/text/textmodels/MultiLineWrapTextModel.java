package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.Caret;
import cz.mg.toolkit.utilities.text.Options;
import cz.mg.toolkit.utilities.text.TextPart;


public class MultiLineWrapTextModel extends AbstractTextModel {
    private Options lastOptions = options.copy();
    private double lastFontSize = 0;

    public MultiLineWrapTextModel() {
    }
    
    public MultiLineWrapTextModel(String text) {
        super(text);
    }
    
    @Override
    protected void clearParts(){
        textParts = null;
    }

    @Override
    protected void createParts() {
        lastOptions = options.copy();
        lastFontSize = options.getFont().getHeight();
        
        if(textParts != null) return;
        textParts = new CachedChainList<>();
        int row = 0;
        String currentLine = "";
        for(int i = 0; i < textBuilder.count(); i++){
            char ch = textBuilder.get(i);
            if(ch == '\n'){
                textParts.addLast(new ImplTextPart2(currentLine, row, true));
                currentLine = "";
                row++;
            } else {
                String extendedLine = currentLine + ch;
                if(options.getFont().getWidth(extendedLine) > options.getWidth()){
                    textParts.addLast(new ImplTextPart2(currentLine, row, false));
                    currentLine = "" + ch;
                    row++;
                } else {
                    currentLine = extendedLine;
                }
            }
        }
        textParts.addLast(new ImplTextPart2(currentLine, row, false));
    }

    @Override
    public double getPrefferedWidth() {
        return 0;
    }
    
    @Override
    public boolean update() {
        if(!Options.equals(options, lastOptions) || lastFontSize != options.getFont().getHeight()){
            clearParts();
            createParts();
            return true;
        }
        return false;
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
            createParts();
            int[] cs = caretToCarets(caret);
            TextPart part = textParts.get(cs[1]);
            return part.getX() + options.getFont().getWidth(part.getText().substring(0, cs[0]));
        }

        @Override
        public double getY() {
            createParts();
            int[] cs = caretToCarets(caret);
            TextPart part = textParts.get(cs[1]);
            return part.getY();
        }

        @Override
        public void setCaret(double x, double y) {
            double lpy = y - getTextY();
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
            if(caret > textBuilder.count()) caret = textBuilder.count();
        }
    }
    
    public int caretsToCaret(int ix, int iy) {
        createParts();
        int caret = 0;
        for(int i = 0; i < iy; i++){
            caret += textParts.get(i).getText().length() + 1;
        }
        return caret + ix;
    }

    public int[] caretToCarets(int i) {
        createParts();
        if(i <= 0) return new int[]{0, 0};
        if(i > textBuilder.count()) i = textBuilder.count();
        
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
    
    private class ImplTextPart2 extends ImplTextPart {
        private final boolean newlined;

        public ImplTextPart2(String text, int row, boolean newlined) {
            super(text, row);
            this.newlined = newlined;
        }

        public boolean isNewlined() {
            return newlined;
        }
    }

    @Override
    protected Caret createCaret() {
        return new ImplCaret();
    }
}
