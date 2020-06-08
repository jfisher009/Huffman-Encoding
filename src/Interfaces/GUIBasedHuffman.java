package Interfaces;

import Huffman.HuffmanDecoder;
import Huffman.HuffmanEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIBasedHuffman {
    private JFrame frame;
    private GridBagConstraints c;
    private ActionListener listener;
    private final int DEFAULT_Y_PADDING = 20;
    private final int DEFAULT_X_PADDING = 20;

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
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(clearInput)){
                    input.setText("");
                }

                else if(e.getSource().equals(clearOutput)){
                    output.setText("");
                }

                else if (e.getSource().equals(encode)){
                    //TODO Update stats along top bar
                    String in = input.getText();
                    String encodedMessage = "";
                    try {
                        encodedMessage = encoder.encodeMessage(in);
                        output.setText(encodedMessage);
                    }
                    catch(NoSuchFieldException except){
                        output.setText("An encoding error has occured." +
                                "\n Please try again.");
                    }
                    Integer asciiLength = in.length() * 8;
                    Integer huffmanLength = encodedMessage.length();
                    double percentChange;
                    String percentChangOut;
                    if(asciiLength > huffmanLength) {
                        percentChange = (((asciiLength - huffmanLength) / asciiLength) * 100);
                        percentChangOut = "-"+percentChange + "%";
                    }
                    if(asciiLength < huffmanLength) {
                        percentChange = (((asciiLength - huffmanLength) / asciiLength) * 100);
                        percentChangOut = "+"+percentChange + "%";
                    }
                    else{
                        percentChangOut = "+/-0.0%";
                    }


                    numAsciiBits.setText(asciiLength.toString());
                    numHuffBits.setText(huffmanLength.toString());
                    numPercentChange.setText(percentChangOut);
                }

                else if(e.getSource().equals(decode)){
                    String in = input.getText();
                    output.setText(decoder.decodeMessage(in));
                }

                else if(e.getSource().equals(copyToClipboard)){
                    StringSelection stringSelection = new StringSelection(output.getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

                else if(e.getSource().equals(encodeOrDecode)){
                    if(encodeOrDecode.getSelectedItem().equals("Encode")){
                        setUpEncode();
                    }
                    else if(encodeOrDecode.getSelectedItem().equals("Decode")){
                        setUpDecode();
                    }
                    //Fixes an issue with jcombobox perpetually covering the
                    //input textarea
                    encodeOrDecode.setVisible(false);
                    encodeOrDecode.setVisible(true);
                }
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        configWindow();

        //add items to encode specific list
        encodeSpecificComponents.add(encode);
        encodeSpecificComponents.add(asciiBits);
        encodeSpecificComponents.add(numAsciiBits);
        encodeSpecificComponents.add(huffmanBits);
        encodeSpecificComponents.add(numHuffBits);
        encodeSpecificComponents.add(percentChange);
        encodeSpecificComponents.add(numPercentChange);

        //add items to decode specific list
        decodeSpecificComponents.add(decode);
    }

    /**
     * Makes the frame visible so that the user can interact
     * with it
     */
    public void run(){
        frame.setVisible(true);
    }

    /**
     * Setup the window and add all necessary components.
     * Uses the GridBagLayout
     */
    private void configWindow(){
        frame.setSize(new Dimension(725,435));

        c.ipadx = DEFAULT_X_PADDING;
        c.ipady = DEFAULT_Y_PADDING;
        c.anchor = java.awt.GridBagConstraints.WEST;;

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

        //"Ascii bits:"
        configLabel(asciiBits, 4, 0);

        //Num bits in ascii. Update every time encode is pressed
        configLabel(numAsciiBits, 5, 0);

        //"Huffman Bits: "
        configLabel(huffmanBits, 6, 0);

        //Num bits in huffman encoding. Updated every time encode is pressed
        configLabel(numHuffBits, 7, 0);

        //"Percent Change:"
        configLabel(percentChange, 9, 0);

        //% change number Updated when encode is pressed
        configLabel(numPercentChange, 10, 0);

        //Input text area
        configTextArea(input,0,1,4);

        //Output text area
        output.setEditable(false);
        configTextArea(output,6,1,4);

        //Encode button
        configButton(encode, 0,3);

        //Decode button
        configButton(decode, 0, 3);

        //Clear input button
        configButton(clearInput, 1,3);

        //Copy to clipboard button
        configButton(copyToClipboard, 6,3);

        //Clear output button
        configButton(clearOutput, 7,3);
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
    private void configButton(Button button, int xpos, int ypos){
        button.addActionListener(listener);
        c.gridx = xpos;
        c.gridy = ypos;

        //small x padding to prevent expanding columns
        c.ipadx = 2;
        c.weighty = 0.0;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        frame.add(button, c);
    }

    public void setUpEncode(){
        for(Component comp:decodeSpecificComponents){
            comp.setVisible(false);
        }
        for(Component comp:encodeSpecificComponents){
            comp.setVisible(true);
        }
    }

    public void setUpDecode(){
        for(Component comp:encodeSpecificComponents){
            comp.setVisible(false);
        }
        for(Component comp:decodeSpecificComponents){
            comp.setVisible(true);
        }
    }
}
