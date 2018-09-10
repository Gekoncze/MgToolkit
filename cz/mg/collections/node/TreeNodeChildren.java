package cz.mg.collections.node;

import cz.mg.collections.list.chainlist.ChainList;


public class TreeNodeChildren<T extends TreeNode> extends ChainList<T> {
    private final TreeNode parent;

    public TreeNodeChildren(TreeNode parent) {
        this.parent = parent;
    }
    
    @Override
    protected void onItemAdded(T data) {
        if(data.parent != null) data.parent.getChildren().remove(data);
        data.parent = parent;
        super.onItemAdded(data);
    }

    @Override
    protected void onItemRemoved(T data) {
        data.parent = null;
        super.onItemRemoved(data);
    }
}
