/**
 * Huffman Encoder class. Implements encoding messages
 * using the Huffman Encoding text compression strategy
 */

public class HuffmanEncoder {
    private HuffTreeBuilder treeBuilder;

    public HuffmanEncoder(){
        treeBuilder = new HuffTreeBuilder();
    }

    /**
     * Encodes a message in binary using Huffman text compression
     * @param message Message to be encoded
     * @param prependTree True if encoded tree should be added to the
     *                    front of the binary encoding
     * @return The encoded message
     */
    public String encodeMessage(String message, boolean prependTree){

        return null;
    }

    /**
     * @param ch The character being encoded
     * @param tree The tree to search use to encode the character
     * @return The encoded character as a binary string
     * @throws NoSuchFieldException
     */
    public String encodeChar(char ch, HuffmanTree tree) throws NoSuchFieldException {
        if(tree.leftSubtree().root() == null && tree.rightSubtree().root() == null && tree.root() == (int)ch){
            return "";
        }
        else if(((BinaryTree<Integer>)tree.leftSubtree()).getLeafList().contains((int)ch)){
            return "0"+encodeChar(ch, (BinaryTree<Integer>) tree.leftSubtree());
        }
        else if(((BinaryTree<Integer>)tree.rightSubtree()).getLeafList().contains((int)ch)){
            return "1"+encodeChar(ch, (BinaryTree<Integer>) tree.rightSubtree());
        }
        else{
            throw new NoSuchFieldException("Char not in tree");
        }
    }
}
