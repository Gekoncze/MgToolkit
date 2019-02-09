package cz.mg.toolkit.utilities.text.textarrangements;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.toolkit.utilities.text.TextBuilder;


public class MultilineTextArrangement extends AbstractTextArrangement {
    public MultilineTextArrangement() {
    }

    @Override
    protected void createParts() {
        if(parts != null) return;
        TextBuilder text = getTextBuilder();
        parts = new CachedChainList<>();
        int begin = 0;
        for(int i = 0; i < text.count(); i++){
            char ch = text.get(i);
            if(ch == '\n'){
                add(begin, i);
                begin = i + 1;
            }
        }
        add(begin, text.count());
    }

    private void add(int begin, int end){
        int row = parts.count();
        String s = getTextBuilder().substring(begin, end);
        parts.addLast(new AbstractTextPart(s, row, begin, true));
    }

    @Override
    public boolean update() {
        return false;
    }
}
