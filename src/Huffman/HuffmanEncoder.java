package Huffman;

import Huffman.Tree.HuffmanTree;

/**
 * Huffman Encoder class. Implements encoding messages
 * using the Huffman Encoding text compression strategy
 * @author Julian Fisher
 * @version 06-07-2020
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
    public String encodeMessage(String message, boolean prependTree) throws NoSuchFieldException {
        HuffmanTree encodingTree = treeBuilder.buildTree(message);
        String out = "";
        String encodedTree = "";

        //Set tree to prepend to the front of binary message
        if(prependTree){
            encodedTree = treeBuilder.encodeTree(encodingTree);
        }

        //encode the message one character at a time
        for(int i = 0; i < message.length(); i++){
            out += encodeChar(message.charAt(i), encodingTree);
        }

        return encodedTree+out;
    }

    /**
     * Default encode message will include tree
     * @param message Message to be encoded
     * @return The encoded message with the tree
     * @throws NoSuchFieldException
     */
    public String encodeMessage(String message) throws NoSuchFieldException {
        return encodeMessage(message, true);
    }

    /**
     * Recursively encodes a single character with the given tree
     * @param ch The character being encoded
     * @param tree The tree to search use to encode the character
     * @return The encoded character as a binary string
     * @throws NoSuchFieldException
     */
    private String encodeChar(char ch, HuffmanTree tree) throws NoSuchFieldException {
        //If at the given character, return nothing

        if(tree.leftSubtree().root() == null && tree.rightSubtree().root() == null && tree.root() == (int)ch){
            return "";
        }

        //If character is in left subtree, add a 0 and go down the left side
        else if(((HuffmanTree)tree.leftSubtree()).getLeafList().contains((int)ch)){
            return "0"+encodeChar(ch, (HuffmanTree) tree.leftSubtree());
        }

        //If character is in right subtree, add a 0 and go down the right side
        else if(((HuffmanTree)tree.rightSubtree()).getLeafList().contains((int)ch)){
            return "1"+encodeChar(ch, (HuffmanTree) tree.rightSubtree());
        }

        //Should not ever reach this part of the code
        else{
            throw new NoSuchFieldException("Char not in tree");
        }
    }

    /**
     * Returns the encoding tree for the given message
     * @param message String to get encoding tree for
     * @return The encoding tree
     */
    public HuffmanTree getEncodingTree(String message){
        return treeBuilder.buildTree(message);
    }
}
