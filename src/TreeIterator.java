import java.util.Iterator;

/**
 * Provides an iterator for HuffmanTree
 */
public class TreeIterator implements Iterator {
    // current iterator
    private int current;

    // collection size
    private int count;

    // Linear collection of nodes
    private int[] collectionArray;

    // The binary tree collection
    private HuffmanTree collection;

    /**
     * Constructs a TreeIterator that iterates over the tree
     */
    public TreeIterator(HuffmanTree collection, int size)
    {
        // set the initial iterator state
        current = 0;
        count = size;
        this.collection = collection;

        // make the collection
        collectionArray =new int[count];

        // load the collectionArray
        preOrderTrav(this.collection);

        // reset current
        current = 0;
    } //end constructor

    /**
     * Method to check if more elements remain in the iteration
     * @return True if the iterator is not done
     */
    public boolean hasNext()
    {
        return current < count;
    } // end hasNext

    /**
     * Method to return the next element in the iteration.
     */
    public Integer next()
    {
        int retVal = collectionArray[current];
        current++;
        return retVal;
    } // end next

    /**
     * Not implemented but must be included.
     */
    public void remove() {   } // end remove

    /**
     * PreOrder Traversal for a huffman tree. Internal nodes are marked as -1
     * and leaf nodes are marked with their root value
     * @param tree Tree to give pre order of
     */
    private void preOrderTrav(HuffmanTree tree){
        if(tree == null || tree.size() == 0){
            return;
        }
        else{
            //add root
            if(tree.leftSubtree().root() == null){
                collectionArray[current]=tree.root();

            }
            else{
                collectionArray[current] = -1;
            }
            current++;

            //traverse left
            preOrderTrav((HuffmanTree) tree.leftSubtree());
            //traverse right
            preOrderTrav((HuffmanTree) tree.rightSubtree());
        }
    }
}
