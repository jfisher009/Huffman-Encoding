import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

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
     * @return preOrder iterator for the tree
     */
    @Override
    public Iterator iteratorPreOrder() {
        return new TreeIterator(this,size);
    }

    /**
     * Returns all the leaves of the tree. Used to encode text
     * @return ArrayList of leaves of the tree
     */
    public ArrayList<Integer> getLeafList(){
        //collection list for leaves
        ArrayList<Integer> leaves = new ArrayList<>();


        HuffmanTree curr;
        HuffmanTree left;
        HuffmanTree right;

        Stack<HuffmanTree> stack = new Stack<>();
        stack.push(this);
        while(!stack.isEmpty()){
            curr = stack.pop();
            left = (HuffmanTree) curr.leftSubtree();
            right = (HuffmanTree) curr.rightSubtree();

            //if the root of both subtrees is null, it is a leaf
            if(left.root() == null && right.root() == null){
                leaves.add(curr.root());
            }

            //If the left's root is not null, it is an internal node
            //and it is added to the stack
            if(left.root() != null){
                stack.push(left);
            }

            //If the left's root is not null, it is an internal node
            //and it is added to the stack
            if(right.root() != null){
                stack.push(right);
            }
        }
        return leaves;
    }
}
