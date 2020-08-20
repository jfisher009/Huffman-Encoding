import Huffman.Tree.HuffmanTree;
import Huffman.Tree.Visual.TreePainter;
import UserInterfaces.GUIBasedHuffman;
import UserInterfaces.TextBasedHuffman;

import java.io.IOException;

public class Test {

    public static void main(String args[]) throws NoSuchFieldException, IOException {

        TextBasedHuffman tbh = new TextBasedHuffman();
        GUIBasedHuffman gbh = new GUIBasedHuffman();
        gbh.run();

        //leaves
        HuffmanTree three = new HuffmanTree(3);
        HuffmanTree five = new HuffmanTree(5);
        HuffmanTree six = new HuffmanTree(6);
        HuffmanTree two = new HuffmanTree(2, new HuffmanTree(), three);
        HuffmanTree four = new HuffmanTree(4, five, six);
        HuffmanTree one = new HuffmanTree(1, two, four);

        TreePainter tv = new TreePainter();
    }
}
