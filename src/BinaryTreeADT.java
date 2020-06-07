import java.util.Iterator;

/**
 * Programming interface for binary tree data structure
 * @author Scott Sigman, Chris Branton, Julian Fisher
 * @Revision by Julian Fisher on 2020-06-06 Changed iteratorInOrder to iteratorPreOrder
 * @version 2020-04-24
 */
public interface BinaryTreeADT<T> {
    /**
     *
     * @return Returns the root of the tree
     */
    public T root();

    /**
     *
     * @return True if the tree is empty; false otherwise
     */
    public boolean isEmpty();

    /**
     *
     * @return Returns the number of nodes in the tree
     */
    public int size();

    /**
     * Retrieves the left subtree of this binary tree
     * @return Returns null if the subtree is empty; otherwise returns the subtree rooted
     * in the left child of this tree.
     */
    BinaryTreeADT leftSubtree();

    /**
     * Retrieves the right subtree of this binary tree
     * @return Returns null if the subtree is empty; otherwise returns the subtree rooted
     * in the right child of this tree.
     */
    BinaryTreeADT rightSubtree();

    /**
     * Creates a pre order iterator for the tree
     * @return
     */
    public Iterator iteratorPreOrder();
}
