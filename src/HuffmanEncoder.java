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
     * Recursively encodes a single character with the given tree
     * @param ch The character being encoded
     * @param tree The tree to search use to encode the character
     * @return The encoded character as a binary string
     * @throws NoSuchFieldException
     */
    public String encodeChar(char ch, HuffmanTree tree) throws NoSuchFieldException {
        //If at the given character, return nothing
        if(tree.leftSubtree().root() == null && tree.rightSubtree().root() == null && tree.root() == (int)ch){
            return "";
        }

        //If character is in left subtree, add a 0 and go down the left side
        else if(((HuffmanTree)tree.leftSubtree()).getLeafList().contains((int)ch)){
            return "0"+encodeChar(ch, (HuffmanTree) tree.leftSubtree());
        }

        //If character is in right subtree, add a 0 and go down the right side
        else if(((HuffmanTree)tree.leftSubtree()).getLeafList().contains((int)ch)){
            return "1"+encodeChar(ch, (HuffmanTree) tree.rightSubtree());
        }

        //Should not ever reach this part of the code
        else{
            throw new NoSuchFieldException("Char not in tree");
        }
    }
}
