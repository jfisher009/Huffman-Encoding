package Huffman.Tree.Visual;


import Huffman.Tree.HuffmanTree;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * Used to visualize a tree in a JFrame
 */
public class TreePainter extends JPanel{
    private final int HORIZONTAL_SPACING = 600;
    private final int VERTICAL_SPACING = 200;
    private final  int SQUARE_SIDE_LENGTH = 20;
    private Queue<Object> nodePreOrder;
    private List<Rectangle2D> leavesToDraw;
    private List<Line2D> linesToDraw;
    private int currDepth;

    /**
     * Constructor
     */
    public TreePainter(){

    }

    /**
     * Draws the given tree to the jFrame
     * @param huffTree
     */
    public void drawTree(HuffmanTree huffTree){

    }

    public void makeDrawLists(HuffmanTree tree, int currX, int currY, int currOffset){
        if(tree.rightSubtree().root() == null && tree.leftSubtree().root() == null){
            leavesToDraw.add(new Rectangle(currX, currY, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH));
        }
        else{
            linesToDraw.add(new Line2D.Float(currX, currY, currX - currOffset / 2, currY + VERTICAL_SPACING));
            makeDrawLists((HuffmanTree) tree.leftSubtree(), currX - currOffset / 2, currY + VERTICAL_SPACING, currOffset / 2);
            linesToDraw.add(new Line2D.Float(currX, currY, currX + currOffset / 2, currY + VERTICAL_SPACING));
            makeDrawLists((HuffmanTree) tree.rightSubtree(), currX + currOffset / 2, currY + VERTICAL_SPACING, currOffset / 2);
        }
    }


    @Override
    public void paint(Graphics g){
        Line2D currLine;
        Rectangle2D currLeaf;
        for(int i = 0; i < linesToDraw.size(); i++){
            currLine = linesToDraw.get(i);
            g.drawLine((int)currLine.getX1(), (int)currLine.getY1(), (int)currLine.getX2(), (int)currLine.getY2());
        }
        for(int i = 0; i < leavesToDraw.size(); i++){
            currLeaf = leavesToDraw.get(i);
            g.drawRect((int)currLeaf.getX(), (int)currLeaf.getY(), SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        }
    }


}
