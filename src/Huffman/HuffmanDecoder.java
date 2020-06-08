package Huffman;

import Huffman.Tree.HuffmanTree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Huffman Encoder class. Implements encoding messages
 * using the Huffman Encoding text compression strategy
 * @author Julian Fisher
 * @version 06-07-2020
 */

public class HuffmanDecoder {

    /**
     * No special constructor needed. Only needed to call specific methods.
     */
    public HuffmanDecoder(){
    }

    /**
     * Decode the given binary string. The string must have the tree encoded
     * at the beginning of the binary string.
     * @param binary Binary string for the message
     * @return The decoded message
     */
    public String decodeMessage(String binary){
        //Make sure string is in binary
        for(int i = 0; i < binary.length(); i++){
            if(!(binary.charAt(i) == '0' || binary.charAt(i) == '1')){
                throw new IllegalStateException("Given string is not in binary");
            }
        }
        //first 16 bits are the number of bits used to encode the tree
        int treeEncodeLength = Integer.parseInt(binary.substring(0,16), 2);
        String binaryEncodedTree = binary.substring(0,16+treeEncodeLength);
        binary = binary.substring(16+treeEncodeLength, binary.length());

        HuffmanTree encodingTree = decodeTree(binaryEncodedTree);
        return decodeMessage(binary, encodingTree);
    }

    /**
     * Decode a message given as a binary string using the provided tree
     * @param encodedMessage The encoded message as a binary string
     * @param decodingTree The tree used to decode the message
     * @return The decoded message
     */
    public String decodeMessage(String encodedMessage, HuffmanTree decodingTree){
        String decodedMessage = "";
        char currChar;
        HuffmanTree currTree;

        //loop through every character
        while(encodedMessage.length() > 0){

            //make sure characters are a 0 or a 1
            if(encodedMessage.charAt(0) == '0' || encodedMessage.charAt(0) == '1'){
                currTree = decodingTree;

                //while you are at an internal node
                while(currTree.leftSubtree().root() != null && currTree.rightSubtree().root() != null){
                    //get next char and remove it from string
                    currChar = encodedMessage.charAt(0);
                    encodedMessage = encodedMessage.substring(1);

                    //if 0 go left
                    if(currChar == '0'){
                        currTree = (HuffmanTree) currTree.leftSubtree();
                    }
                    //if 1 go right
                    else if(currChar == '1'){
                        currTree = (HuffmanTree) currTree.rightSubtree();
                    }
                }
                decodedMessage += (char)(int)currTree.root();
            }
            //If not in binary
            else{
                throw new IllegalStateException("Given string is not in binary");
            }
        }
        return decodedMessage;
    }

    /**
     * Decode the Huffman.Tree.HuffmanTree given as a binary String.
     * @param treeAsBinary The binary representation of the tree
     * @return Decoded Huffman.Tree.HuffmanTree
     */
    private HuffmanTree decodeTree(String treeAsBinary){
        String treeLength = treeAsBinary.substring(0,16);
        int length = Integer.parseInt(treeLength, 2);
        treeAsBinary = treeAsBinary.substring(16,16+length);
        Stack<Integer> binaryString = new Stack();
        ArrayList<HuffmanTree> nodeList = new ArrayList<>();

        //fill binaryString with the binary representation of the tree
        while (treeAsBinary.length() > 0){
            binaryString.push(Integer.parseInt(((Character)treeAsBinary.charAt(treeAsBinary.length()-1)).toString()));
            treeAsBinary = treeAsBinary.substring(0,treeAsBinary.length()-1);
        }

        //Fill the ArrayList nodeList with the nodes of the tree
        int curr;
        String binaryCharacter;
        while(!binaryString.isEmpty()){
            curr = binaryString.pop();
            if(curr == 1){
                nodeList.add(new HuffmanTree(-1));
            }
            else{
                binaryCharacter = "";
                for(int i = 0; i <  8; i++){
                    binaryCharacter = binaryCharacter + binaryString.pop().toString();
                }
                nodeList.add(new HuffmanTree(Integer.parseInt(binaryCharacter, 2)));
            }
        }

        HuffmanTree currNode;
        HuffmanTree findingNode;
        int currPos = 1;
        int findingPos;
        boolean found = false;

        //Go through every node and place it in the tree
        while(currPos < nodeList.size()){
            found = false;
            findingPos = currPos - 1;
            currNode = nodeList.get(currPos);

            //find the place to put the node
            while(!found){
                findingNode = nodeList.get(findingPos);
                if(findingNode.root() == -1 && findingNode.leftSubtree().root() == null){
                    findingNode.setLeftSubtree(currNode);
                    found = true;
                }
                else if(findingNode.root() == -1 && findingNode.rightSubtree().root() == null){
                    findingNode.setRightSubtree(currNode);
                    found = true;
                }
                findingPos--;
            }
            currPos++;
        }
        return nodeList.get(0);
    }
}
