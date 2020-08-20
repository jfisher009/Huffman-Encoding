import Huffman.Tree.HuffmanTree;
import Huffman.Tree.Visual.TreePainter;
import UserInterfaces.GUIBasedHuffman;
import UserInterfaces.TextBasedHuffman;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Test {

    public static void main(String args[]) throws NoSuchFieldException, IOException {
        /**
        TextBasedHuffman tbh = new TextBasedHuffman();
        GUIBasedHuffman gbh = new GUIBasedHuffman();
        gbh.run();
         */

        //leaves
        HuffmanTree eight = new HuffmanTree(8);
        HuffmanTree nine = new HuffmanTree(9);
        HuffmanTree seven = new HuffmanTree(7);
        HuffmanTree three = new HuffmanTree(3);
        HuffmanTree five = new HuffmanTree(5, eight, nine);
        HuffmanTree six = new HuffmanTree(6);
        HuffmanTree two = new HuffmanTree(2, seven, three);
        HuffmanTree four = new HuffmanTree(4, five, six);
        HuffmanTree one = new HuffmanTree(1, two, four);

        TreePainter tv = new TreePainter();
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(tv);
        tv.drawTree(one);
        frame.setSize(new Dimension(1200,1200));
    }
}
