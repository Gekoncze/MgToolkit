package cz.mg.toolkit.utilities.text.textarrangements;

import cz.mg.collections.list.chainlist.CachedChainList;


public class SinglelineTextArrangement extends AbstractTextArrangement {
    public SinglelineTextArrangement() {
    }

    @Override
    protected void createParts() {
        if(parts != null) return;
        parts = new CachedChainList<>(new AbstractTextPart(getTextBuilder().toString(), 0, 0, true));
    }

    @Override
    public boolean update() {
        return false;
    }
}
