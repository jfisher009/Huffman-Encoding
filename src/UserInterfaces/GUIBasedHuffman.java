package UserInterfaces;

import Huffman.HuffmanDecoder;
import Huffman.HuffmanEncoder;
import Huffman.Tree.HuffmanTree;
import Huffman.Tree.Visual.TreePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A GUI based interface for Huffman Encoding
 * @author Julian Fisher
 * @version 08-21-2020
 */
public class GUIBasedHuffman {
    //currTree used to hold the current encoding tree. It is update every time the encode or decode button is pressed.
    private HuffmanTree currTree;

    private JFrame frame;
    private GridBagConstraints c;
    private ActionListener listener;
    private final int DEFAULT_Y_PADDING = 20;
    private final int DEFAULT_X_PADDING = 20;

    //used to visualize the tree
    private JFrame visualizerFrame;
    private TreePainter tp;

    //used for the tutorial
    private JFrame tutorialFrame;

    //Huffman encoder and decoder
    HuffmanEncoder encoder = new HuffmanEncoder();
    HuffmanDecoder decoder = new HuffmanDecoder();

    //Components need global scope for action handling
    //Buttons
    private Button encode = new Button("Encode");
    private Button decode = new Button("Decode");
    private Button clearInput = new Button("Clear");
    private Button copyToClipboard = new Button("Copy to clipboard");
    private Button clearOutput = new Button("Clear");
    private Button viewTree = new Button("View Tree");

    //Labels
    private JLabel asciiBits = new JLabel("Ascii Bits:");
    private JLabel numAsciiBits = new JLabel("0");
    private JLabel huffmanBits = new JLabel("  Huffman Bits: ");
    private JLabel numHuffBits = new JLabel("0");
    private JLabel percentChange = new JLabel("Percent Change: ");
    private JLabel numPercentChange = new JLabel("0");

    //Text areas
    private TextArea input = new TextArea(20, 40);
    private TextArea output = new TextArea(20,40);

    //Select between encode and decode
    private JComboBox<String> encodeOrDecode = new JComboBox<>();

    //List of components specific to encoding/decoding mode
    private ArrayList<Component> encodeSpecificComponents = new ArrayList<>();
    private ArrayList<Component> decodeSpecificComponents = new ArrayList<>();

    /**
     * Constructor for the GUI based Huffman encoding
     */
    public GUIBasedHuffman(){
        frame = new JFrame();
        frame.setTitle("Huffman Encoder/Decoder");
        frame.setIconImage(new ImageIcon(getClass().getResource(("/Pictures/HuffmanLogo.png"))).getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //setup visualization frame
        visualizerFrame = new JFrame();
        visualizerFrame.setIconImage(new ImageIcon(getClass().getResource("/Pictures/HuffmanLogo.png")).getImage());
        visualizerFrame.setTitle("Tree Viewer");
        visualizerFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        tp = new TreePainter();
        visualizerFrame.add(tp);

        //setup tutorial frame
        tutorialFrame = new JFrame();
        tutorialFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ImageIcon tutorialIcon = new ImageIcon(getClass().getResource("/Pictures/HuffmanTutorial.png"));
        JLabel tutorialLabel = new JLabel(tutorialIcon);
        tutorialFrame.add(tutorialLabel);
        tutorialFrame.pack();
        tutorialFrame.setIconImage(new ImageIcon(getClass().getResource("/Pictures/HuffmanLogo.png")).getImage());
        tutorialFrame.setTitle("Huffman Tutorial");

        //action listener for all buttons and menus
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear input button
                if(e.getSource().equals(clearInput)){
                    input.setText("");
                }

                //clear output button
                else if(e.getSource().equals(clearOutput)){
                    output.setText("");
                }

                //encode button
                else if (e.getSource().equals(encode)){
                    //get input
                    String in = input.getText();
                    String encodedMessage = "";
                    currTree = encoder.getEncodingTree(in);
                    try {
                        encodedMessage = encoder.encodeMessage(in);
                        output.setText(encodedMessage);
                    }
                    catch(NoSuchFieldException except){
                        output.setText("An encoding error has occured." +
                                "\n Please try again.");
                    }
                    catch(IllegalArgumentException except){
                        output.setText("Characters encoded must have ascii values between 0 and 128." +
                                "\nPlease try again.");
                    }
                    catch(java.lang.NullPointerException except){
                        output.setText("Enter text to encode." +
                                "\nPlease try again.");
                    }
                    //figuring out stats
                    Integer asciiLength = in.length() * 8;
                    Integer huffmanLength = encodedMessage.length();
                    double percentChange;
                    String percentChangOut;

                    //used to cut off after tow decimal points
                    DecimalFormat df = new DecimalFormat("#.##");

                    //format percent change with positive or negative
                    if(asciiLength > huffmanLength) {
                        percentChange = (((double)(asciiLength - huffmanLength) / asciiLength) * 100);
                        percentChangOut = "-" + df.format(percentChange) + "%";
                    }
                    else if(asciiLength < huffmanLength) {
                        percentChange = (((double)(huffmanLength - asciiLength) / asciiLength) * 100);
                        percentChangOut = "+" + df.format(percentChange) + "%";
                    }
                    else{
                        percentChangOut = "+/-0.0%";
                    }

                    //update statistic labels
                    numAsciiBits.setText(asciiLength.toString());
                    numHuffBits.setText(huffmanLength.toString());
                    numPercentChange.setText(percentChangOut);
                }

                //decode button
                else if(e.getSource().equals(decode)){
                    String in = input.getText();
                    String decoded = "";
                    try {
                        currTree = decoder.getDecodingTree(in);
                        decoded = decoder.decodeMessage(in);
                        output.setText(decoded);
                    }
                    catch(IllegalStateException except){
                        output.setText("The given string is not in binary." +
                                "\nPleasse try again with a binary string.");
                    }
                    catch(java.lang.StringIndexOutOfBoundsException except){
                        output.setText("Enter text to decode." +
                                "\nPlease try again.");
                    }

                    //get bit lengths for both models
                    Integer asciiLength = decoded.length() * 8;
                    Integer huffmanLength = in.length();
                    double percentChange;
                    String percentChangOut;

                    //used to cut off after two decimal points
                    DecimalFormat df = new DecimalFormat("#.##");

                    //format percent change with positive or negative
                    if(asciiLength > huffmanLength) {
                        percentChange = (((double)(asciiLength - huffmanLength) / asciiLength) * 100);
                        percentChangOut = "-" + df.format(percentChange) + "%";
                    }
                    else if(asciiLength < huffmanLength) {
                        percentChange = (((double)(huffmanLength - asciiLength) / asciiLength) * 100);
                        percentChangOut = "+" + df.format(percentChange) + "%";
                    }
                    else{
                        percentChangOut = "+/-0.0%";
                    }

                    //update statistic labels
                    numAsciiBits.setText(asciiLength.toString());
                    numHuffBits.setText(huffmanLength.toString());
                    numPercentChange.setText(percentChangOut);

                }

                //Copy to clipboard button
                else if(e.getSource().equals(copyToClipboard)){
                    StringSelection stringSelection = new StringSelection(output.getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

                //viewTree button
                else if(e.getSource().equals(viewTree)){
                    try {
                        tp.drawTree(currTree);
                        visualizerFrame.setSize(new Dimension((int) (tp.horizontalSpacing * 2 * tp.STARTX_FACTOR), tp.verticalSpacing * (currTree.getDepth() + 2)));
                        visualizerFrame.setVisible(true);
                    }
                    catch(NullPointerException except){
                        output.setText("No tree to display");
                    }
                }

                //using drop down menu
                else if(e.getSource().equals(encodeOrDecode)){
                    Component[] add;
                    Component[] remove;

                    //setup the lists to view or hide mode specific components
                    if(encodeOrDecode.getSelectedItem().equals("Encode")){
                        add = encodeSpecificComponents.toArray(Component[]::new);
                        remove = decodeSpecificComponents.toArray(Component[]::new);
                    }
                    else if(encodeOrDecode.getSelectedItem().equals("Decode")){
                        add = decodeSpecificComponents.toArray(Component[]::new);
                        remove = encodeSpecificComponents.toArray(Component[]::new);
                    }
                    //default case. This shouldn't run
                    else{
                        add = new Component[0];
                        remove = new Component[0];
                    }
                    switchEncodeDecodeMode(add, remove);
                    //Fixes an issue with jComboBox perpetually covering the
                    //input textarea
                    encodeOrDecode.setVisible(false);
                    encodeOrDecode.setVisible(true);
                }
            }
        };
        configWindow();

        //add items to encode specific list
        encodeSpecificComponents.add(encode);

        //add items to decode specific list
        decodeSpecificComponents.add(decode);
    }

    /**
     * Setup the window and add all necessary components.
     * Uses the GridBagLayout
     */
    private void configWindow(){
        //setup basics of the frame
        frame.setSize(new Dimension(725,435));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.ipadx = DEFAULT_X_PADDING;
        c.ipady = DEFAULT_Y_PADDING;
        c.anchor = java.awt.GridBagConstraints.WEST;

        //"Mode:" label
        JLabel mode = new JLabel("Mode:");
        configLabel(mode, 0, 0);

        //Encode/decode drop box
        encodeOrDecode.addActionListener(listener);
        encodeOrDecode.addItem("Encode");
        encodeOrDecode.addItem("Decode");
        c.ipady = DEFAULT_Y_PADDING / 2;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        frame.add(encodeOrDecode, c);
        c.ipady = DEFAULT_Y_PADDING;

        //Buffer jframe to use extra space
        JLabel buffer = new JLabel();
        c.weighty = 0.0;
        c.weightx = 1.0;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        frame.add(buffer, c);

        //"Ascii bits:" Label
        configLabel(asciiBits, 4, 0);

        //Num bits in ascii. Update every time encode is pressed
        configLabel(numAsciiBits, 5, 0);

        //"Huffman Bits: " Label
        configLabel(huffmanBits, 6, 0);

        //Num bits in huffman encoding. Updated every time encode is pressed
        configLabel(numHuffBits, 7, 0);

        //"Percent Change:" Label
        configLabel(percentChange, 9, 0);

        //% change number Updated when encode is pressed
        configLabel(numPercentChange, 10, 0);

        //Input text area
        configTextArea(input,0,1,4);

        //Output text area
        output.setEditable(false);
        configTextArea(output,6,1,4);

        //Encode button
        configButton(encode, 0,3,1);

        //Decode button
        configButton(decode, 0, 3,1);

        //Clear input button
        configButton(clearInput, 1,3,1);

        //Copy to clipboard button
        configButton(copyToClipboard, 6,3,1);

        //Clear output button
        configButton(clearOutput, 7,3,1);

        //viewTree Button
        configButton(viewTree, 10,3, 2);
    }

    /**
     * Config label and put it in the frame at the given grid position
     * @param label Label to configure
     * @param xpos Grid x position
     * @param ypos Grid y position
     */
    private void configLabel(JLabel label, int xpos, int ypos){
        label.setBackground(new Color(192,192,192));
        label.setOpaque(true);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        c.gridy = ypos;
        c.gridx = xpos;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        frame.add(label, c);
    }

    /**
     * Configures a TextArea and puts it at the specified location
     * with the given width
     * @param txtArea The text area to configure
     * @param xpos Grid x position
     * @param ypos Frid y position
     * @param width Number of cells wide
     */
    private void configTextArea(TextArea txtArea, int xpos, int ypos, int width){
        c.weighty = 0.0;

        //Split extra width between the text areas
        c.weightx = 0.5;
        c.gridx = xpos;
        c.gridy = ypos;

        //Both text areas are 6 columns wide
        c.gridwidth = 6;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        frame.add(txtArea, c);
    }

    /**
     * Configure a button and add it to the from at the given
     * grid x and y coordinate
     * @param button Button to config
     * @param xpos Grid x position
     * @param ypos Grid y position
     */
    private void configButton(Button button, int xpos, int ypos, int width){
        button.addActionListener(listener);
        c.gridx = xpos;
        c.gridy = ypos;

        //small x padding to prevent expanding columns
        c.ipadx = 2;
        c.weighty = 0.0;
        c.weightx = 0.0;
        c.gridwidth = width;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        frame.add(button, c);
    }

    /**
     * Makes all components in addList visible and hides all components in removeList
     * @param addList List of items to set visible true
     * @param removeList List of items to set visible false
     */
    public void switchEncodeDecodeMode(Component[] addList, Component[] removeList){
        //make components visible
        for(Component comp:addList){
            comp.setVisible(true);
        }
        //hide components
        for(Component comp:removeList){
            comp.setVisible(false);
        }
        //reset the statistic labels
        numAsciiBits.setText("0");
        numHuffBits.setText("0");
        numPercentChange.setText("0");
    }

    /**
     * Makes the main frame and tutorial frame visible so that the
     * user can interact with the window
     */
    public void run(){
        frame.setVisible(true);
        tutorialFrame.setVisible(true);
    }
}