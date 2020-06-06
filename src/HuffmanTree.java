import java.util.Iterator;

/**
 * Implementation of the Binary Tree ADT for use with Huffman Encoding
 * @author Julian Fisher
 * @Version 1.0
 */
public class HuffmanTree implements BinaryTreeADT {
    protected Integer root;
    protected int size;
    protected HuffmanTree left;
    protected HuffmanTree right;

    /**
     * Default constructor
     */
    public HuffmanTree(){
        root = null;
        left = null;
        right = null;
        size = 0;
    }

    /**
     * Leaf constructor
     * @param data The data held in the node
     */
    public HuffmanTree(int data){
        root = data;
        left = new HuffmanTree();
        right = new HuffmanTree();
        size = 1;
    }

    /**
     * Internal node constructor
     * @param data The data held in the node
     * @param leftChild Left subtree
     * @param rightChild Right subtree
     */
    public HuffmanTree(int data, HuffmanTree leftChild, HuffmanTree rightChild){
        root = data;
        this.left = leftChild;
        this.right = rightChild;
        size = leftChild.size + rightChild.size + 1;
    }

    /**
     * @return Returns the root of the tree
     */
    @Override
    public Integer root() {
        return this.root;
    }

    /**
     * @return True if the tree is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        if(this.size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return Returns the number of nodes in the tree
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Retrieves the left subtree of this binary tree
     *
     * @return Returns null if the subtree is empty; otherwise returns the subtree rooted
     * in the left child of this tree.
     */
    @Override
    public BinaryTreeADT leftSubtree() {
        return this.left;
    }

    /**
     * Retrieves the right subtree of this binary tree
     *
     * @return Returns null if the subtree is empty; otherwise returns the subtree rooted
     * in the right child of this tree.
     */
    @Override
    public BinaryTreeADT rightSubtree() {
        return this.right;
    }

    /**
     * Creates an in order iterator for the tree
     *
     * @return
     */
    @Override
    public Iterator iteratorPreOrder() {
        return new TreeIterator(this,size);
    }


}
