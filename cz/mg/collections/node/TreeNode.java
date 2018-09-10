package cz.mg.collections.node;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import java.util.Iterator;

public abstract class TreeNode<A extends TreeNode, B extends TreeNode> implements Iterable<B> {
    A parent = null;
    private final TreeNodeChildren<B> children = new TreeNodeChildren<>(this);
    final ChainList<TreeNodeParentChangeListener> parentChangeListeners = new ChainList<>();

    public TreeNode() {
    }

    public TreeNode(A parent) {
        setParent(parent);
    }

    public final A getParent() {
        return parent;
    }

    public final void setParent(A parent) {
        if(parent == null && this.parent != null) this.parent.getChildren().remove(this);
        if(parent != null) parent.getChildren().addLast(this);
    }

    public final List<B> getChildren() {
        return children;
    }

    public final ChainList<TreeNodeParentChangeListener> getParentChangeListeners() {
        return parentChangeListeners;
    }

    @Override
    public final Iterator<B> iterator() {
        return children.iterator();
    }
    
    public interface TreeNodeParentChangeListener {
        public void parentChanged();
    }
}
