package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBasedHuffman {
    private JFrame frame;
    private GridBagConstraints c;
    private ActionListener listener;
    private final int DEFAULT_Y_PADDING = 20;
    private final int DEFAULT_X_PADDING = 20;

    /**
     * Constructor for the GUI based Huffman encoding
     */
    public GUIBasedHuffman(){
        frame = new JFrame();
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Clear")){
                    System.out.println("Clear");
                }
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        configWindow();
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
        JComboBox<String> encodeOrDecode = new JComboBox<>();
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
        JLabel asciiBits = new JLabel("Ascii Bits:");
        configLabel(asciiBits, 4, 0);

        //Num bits in ascii. Update every time encode is pressed
        JLabel numAsciiBits = new JLabel("TEST NUM");
        configLabel(numAsciiBits, 5, 0);

        //"Huffman Bits: "
        JLabel huffmanBits = new JLabel("  Huffman Bits: ");
        configLabel(huffmanBits, 6, 0);

        //Num bits in huffman encoding. Updated every time encode is pressed
        JLabel numHuffBits = new JLabel("TEST NUM");
        configLabel(numHuffBits, 7, 0);

        //"Percent Change:"
        JLabel percentChange = new JLabel("Percent Change: ");
        configLabel(percentChange, 9, 0);

        //% change number Updated when encode is pressed
        JLabel numPercentChange = new JLabel("TEST %");
        configLabel(numPercentChange, 10, 0);

        //Input text area
        TextArea input = new TextArea(20, 40);
        configTextArea(input,0,1,4);

        //Output text area
        TextArea output = new TextArea(20,40);
        output.setEditable(false);
        configTextArea(output,6,1,4);

        //Encode button
        Button encode = new Button("Encode");
        configButton(encode, 0,3);

        //Clear input button
        Button clearInput = new Button("Clear");
        configButton(clearInput, 1,3);

        //Copy to clipboard button
        Button copyToClipboard = new Button("Copy to clipboard");
        configButton(copyToClipboard, 6,3);

        //Clear output button
        Button clearOutput = new Button("Clear");
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
}
