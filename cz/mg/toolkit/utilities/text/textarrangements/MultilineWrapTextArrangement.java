package cz.mg.toolkit.utilities.text.textarrangements;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.utilities.text.TextBuilder;


public class MultilineWrapTextArrangement extends AbstractTextArrangement {
    private double lastFontSize = 0;

    public MultilineWrapTextArrangement() {
    }

    @Override
    protected void createParts() {
        if(parts != null) return;
        lastFontSize = options.getFont().getHeight();
        TextBuilder text = getTextBuilder();
        parts = new CachedChainList<>();
        int begin = 0;
        for(int i = 0; i < text.count(); i++){
            char ch = text.get(i);
            if(ch == '\n'){
                add(begin, i, true);
                begin = i + 1;
            } else {
                String s = text.substring(begin, i + 1);
                if(measure(s) > options.getWidth()){ // check for overflow
                    if(s.length() > 1){ // if can cut off 1 char from current line
                        add(begin, i, false);
                        begin = i;
                    } else {
                        add(begin, i + 1, false);
                        begin = i + 1;
                    }
                }
            }
        }
        add(begin, text.count(), true);
    }

    private void add(int begin, int end, boolean newlined){
        int row = parts.count();
        String s = getTextBuilder().substring(begin, end);
        parts.addLast(new AbstractTextPart(s, row, begin, newlined));
    }

    private double measure(String s){
        return options.getFont().getWidth(s) + options.getLeftPadding() + options.getRightPadding();
    }

    @Override
    public double getRequiredWidth() {
        return 0;
    }

    @Override
    public boolean update() {
        if(options.isChanged() || lastFontSize != options.getFont().getHeight()){
            options.setChanged(false);
            clearParts();
            createParts();
            return true;
        }
        return false;
    }
}
