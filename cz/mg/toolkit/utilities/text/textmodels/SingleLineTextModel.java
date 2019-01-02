package cz.mg.toolkit.utilities.text.textmodels;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.utilities.StringUtilities;
import cz.mg.toolkit.utilities.text.Caret;


public class SingleLineTextModel extends AbstractTextModel {
    public SingleLineTextModel() {
    }
    
    public SingleLineTextModel(String text) {
        super(text);
    }

    @Override
    protected void clearParts() {
        textParts = null;
    }

    @Override
    protected void createParts() {
        if(textParts != null) return;
        textParts = new CachedChainList<>(new ImplTextPart(textBuilder.toString(), 0));
    }
    
    @Override
    public boolean update() {
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
            return caret;
        }

        @Override
        public int getRow() {
            return 0;
        }

        @Override
        public double getX() {
            double lineX = textParts.getFirst().getX();
            double positionX = options.getFont().getWidth(textBuilder.substring(0, getCaret()));
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
            setCaret(StringUtilities.getClosestCharacter(options.getFont(), textBuilder, x));
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
            if(caret > textBuilder.count()) caret = textBuilder.count();
        }
    }

    @Override
    protected Caret createCaret() {
        return new ImplCaret();
    }
}
