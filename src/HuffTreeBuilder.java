import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * A class used to build Huffman Trees for a given string. All
 * characters in the string must have 0<= ascii value < 128
 * This class will also take a Huffman Tree and encode it in binary
 */
public class HuffTreeBuilder {
    //Priority Queue used to help build the huffman tree
    private PriorityQueue<HuffmanTree> pQueue = new PriorityQueue<>((Comparator)(new Comparator<HuffmanTree>(){
        /**
         * Compare method for a BinaryTree<Point>
         * Compares the y values of the two to determine order.
         * @param o1 First integer tree to be compared
         * @param o2 Second integer tree to be compared
         * @return An int indicating the order of the tree
         */
        @Override
        public int compare(HuffmanTree o1, HuffmanTree o2) {
            return o1.root() - o2.root();
        }
    }));

    /**
     * Constructor only used to make an instance of HuffTreeBuilder.
     * No data is stored in HuffTreeBuilder. It is only used to call
     * methods.
     */
    public HuffTreeBuilder(){
    }

    /**
     * Builds a Huffman Tree for the given message
     * @param message The string used to build the tree
     * @return The proper huffman tree for the message
     */
    public HuffmanTree buildTree(String message){
        int[] frequencies = setFrequencies(message);
        fillPriorityQueue(frequencies);

        HuffmanTree first;
        HuffmanTree second;
        while(pQueue.size() > 1){
            first = pQueue.poll();
            second = pQueue.poll();
            pQueue.add(combineTrees(first, second));
        }
        return pQueue.poll();
    }

    /**
     * Counts the number of each character in the given string and stores it
     * in the ArrayList frequencies. Each character has to have an ascii
     * value less than 128.
     */
    private int[] setFrequencies(String originalMessage) throws IllegalArgumentException{
        int[] frequencies = new int[128];
        int ascii;
        for(int i = 0; i < originalMessage.length(); i++){
            ascii = originalMessage.charAt(i);
            if(ascii < frequencies.length && ascii >= 0) {
                frequencies[ascii]++;
            }
            else{
                throw new IllegalArgumentException("Message to encode must only contain characters with ascii " +
                        "values between 0 and 128");
            }
        }
        return frequencies;
    }

    /**
     * Fills the priority queue with using an array of ascii frequencies
     * @param frequencies int[] array of character frequencies
     */
    private void fillPriorityQueue(int[] frequencies){
        pQueue.clear();
        for(int i = 0; i < frequencies.length; i++){
            if(frequencies[i] != 0){
                //Two nodes are made. The leaf holds the ascii value, the
                //internal node holds the frequency
                HuffmanTree leafNode = new HuffmanTree(i);
                HuffmanTree internalNode = new HuffmanTree(frequencies[i], leafNode, new HuffmanTree());
                pQueue.add(internalNode);
            }
        }
    }

    /**
     * Combines two trees into one tree. Used for making trees
     * to sort into the priorityQueue
     * @param tree1 Left subtree of final tree
     * @param tree2 Right subtree of final tree
     * @return BinaryTree of combined trees
     */
    private HuffmanTree combineTrees(HuffmanTree tree1, HuffmanTree tree2) {
        int freq1 = tree1.root();
        int freq2 = tree2.root();
        HuffmanTree oneToAdd;
        HuffmanTree twoToAdd;

        //If the tree has only one ascii value, the frequency node is not needed
        if(tree1.size() == 2){
            oneToAdd = new HuffmanTree((Integer)tree1.leftSubtree().root());
        }
        else{
            oneToAdd = tree1;
        }

        //same for second tree
        if(tree2.size() == 2){
            twoToAdd = new HuffmanTree((Integer)tree2.leftSubtree().root());
        }
        else{
            twoToAdd = tree2;
        }

        return new HuffmanTree(freq1 + freq2, oneToAdd, twoToAdd);
    }

    /**
     * Encodes the Huffman Tree in binary. The first 16 bits are the number of bits in the encoded
     * tree. The rest is the tree represented by a preorder traversal. Internal nodes are represented
     * by a "1" and leaf nodes are represented as "0" the binary form of the character's ascii
     * value.
     * @return Binary representation of the encoded tree.
     */
    public String encodeTree(HuffmanTree tree){
        Iterator it = tree.iteratorPreOrder();
        Stack<Integer> temp = new Stack<>();
        Stack<Integer> nodeStack = new Stack<>();
        String out = "";

        //fill a temporary stack upside down
        while(it.hasNext()){
            temp.push((Integer) it.next());
        }

        //move to nodeStack to reverse it
        while(!temp.isEmpty()){
            nodeStack.push(temp.pop());
        }

        int curr;

        //go through the stack of nodes
        while(!nodeStack.isEmpty()){
            curr = nodeStack.pop();
            //iterator has internal nodes as -1
            //Internal nodes are tagged with a 1 in binary
            if(curr == -1){
                out = out + "1";
            }
            //If the node is a leaf, a 0 is put at the
            //beginning and the 8 bit ascii value is put afterwards
            else{
                out = out + "0" + String.format("%8s", Integer.toBinaryString(curr)).replace(' ', '0');
            }
        }
        int length = out.length();
        //16 bits are at the front of the binary encoded tree to denote
        //how many bits are used for the tree, after the initial 16 bits
        String binaryLength = String.format("%16s", Integer.toBinaryString(length)).replace(' ', '0');
        return binaryLength+out;
    }
}
