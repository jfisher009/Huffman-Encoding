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
    public int horizontalSpacing = 100;
    public int verticalSpacing = 50;
    public final  int SQUARE_SIDE_LENGTH = 50;
    public final double  STARTX_FACTOR = 1.5;

    //used to roughly center the text in the displayed box
    private static final int TEXT_CENTERING_X = 3;
    private static final int TEXT_CENTERING_Y = 5;

    //lists used to tell the frame what to draw
    private List<Rectangle2D> leavesToDraw;
    private List<Line2D> linesToDraw;

    //textPoints holds the x,y values of the chars to draw in the boxes, charsToDraw holds the value of what is to be drawn
    private List<Point> textPoints;
    private List<Integer> charsToDraw;

    /**
     * Constructor
     */
    public TreePainter(){
        linesToDraw = new ArrayList<>();
        leavesToDraw = new ArrayList<>();
        textPoints = new ArrayList<>();
        charsToDraw = new ArrayList<>();
    }

    /**
     * Draws the given full binary tree to the jFrame
     * @param huffTree
     */
    public void drawTree(HuffmanTree huffTree){
        //clear lists used to draw
        leavesToDraw.clear();
        linesToDraw.clear();
        textPoints.clear();
        charsToDraw.clear();

        //Set the starting position of the tree
        horizontalSpacing = huffTree.getDepth() * SQUARE_SIDE_LENGTH;
        int startX = (int)(horizontalSpacing * STARTX_FACTOR);
        int startY = verticalSpacing;
        makeDrawLists(huffTree, startX, startY, horizontalSpacing);
    }

    /**
     * Sets the list variables to help draw the binary tree
     * @param tree
     * @param currX
     * @param currY
     * @param currOffset
     */
    private void makeDrawLists(HuffmanTree tree, int currX, int currY, int currOffset){
        //if the node is a leaf, add a box to the appropriate list to draw in the window,
        if(tree.rightSubtree().root() == null && tree.leftSubtree().root() == null){
            leavesToDraw.add(new Rectangle((int) currX - SQUARE_SIDE_LENGTH / 2, currY, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH));
            charsToDraw.add(tree.root());
            textPoints.add(new Point(currX - TEXT_CENTERING_X, currY + TEXT_CENTERING_Y + (SQUARE_SIDE_LENGTH / 2)));
        }
        //if the node is an internal node, draw lines to the left and right, then draw the left and right subtrees
        else{
            linesToDraw.add(new Line2D.Float(currX, currY, currX - currOffset / 2, currY + verticalSpacing));
            makeDrawLists((HuffmanTree) tree.leftSubtree(), currX - currOffset / 2, currY + verticalSpacing, currOffset / 2);
            linesToDraw.add(new Line2D.Float(currX, currY, currX + currOffset / 2, currY + verticalSpacing));
            makeDrawLists((HuffmanTree) tree.rightSubtree(), currX + currOffset / 2, currY + verticalSpacing, currOffset / 2);
        }
    }

    /**
     * Draws everything held in linesToDraw and leavesToDraw.
     * Uses the points stored in textpoints to draw the info held in charsToDraw
     * @param g
     */
    @Override
    public void paint(Graphics g){
        //instantiate local variables
        Line2D currLine;
        Rectangle2D currLeaf;
        Integer currChar;
        g.setColor(Color.BLACK);

        //draw all of the lines
        for (Line2D line2D : linesToDraw) {
            currLine = line2D;
            g.drawLine((int) currLine.getX1(), (int) currLine.getY1(), (int) currLine.getX2(), (int) currLine.getY2());
        }

        //draw all of the boxes for the leaves
        for (Rectangle2D rectangle2D : leavesToDraw) {
            currLeaf = rectangle2D;
            g.drawRect((int) currLeaf.getX(), (int) currLeaf.getY(), SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        }

        //draw the text
        for(int i = 0; i < charsToDraw.size(); i ++){
            currChar = charsToDraw.get(i);
            g.drawString(String.valueOf(currChar), (int)textPoints.get(i).getX(), (int)textPoints.get(i).getY());
        }
    }


}
