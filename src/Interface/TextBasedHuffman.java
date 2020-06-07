package Interface;

import Huffman.HuffmanDecoder;
import Huffman.HuffmanEncoder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A text based interface for Huffman Encoding
 */

public class TextBasedHuffman {
    private HuffmanEncoder encoder;
    private HuffmanDecoder decoder;
    private BufferedReader reader;

    /**
     * Constructor for TextBasedHuffman
     */
    public TextBasedHuffman(){
        encoder = new HuffmanEncoder();
        decoder = new HuffmanDecoder();
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The bulk of the Interface.TextBasedHuffman class. Will let a user encode or decode
     * any number of messages.
     */
    public void run(){
        boolean running = true;
        String input;
        System.out.println("Welcome to Huffman Encoder/Decoder by Julian Fisher");
        System.out.println("");
        System.out.println("");

        while(running){
            //temp string used to hold a string for short durations only. Misc. uses
            String temp;
            input = "";
            try {
                input = getInput("Would you like to encode or decode a message? Type \"encode\" to" + "\n" +
                        "encode a message or \"decode\" to decode a message. Or, type " + "\n" +
                        "\"stop\" to exit Huffman Encoder/Decoder.");
            }
            catch(IOException e){
                handleIOException(e);
            }

            //add some spaces in between choice and action
            System.out.println("");
            System.out.println("");
            System.out.println("");

            //if user wants to stop
            switch (input.toLowerCase()) {
                case "stop":
                    running = false;
                    break;

                //if user wants to encode
                case "encode":
                    try {
                        System.out.println("The encoded message will be copied to your clipboard.");
                        input = getMultiLineInput("Enter your message to be encoded.");
                        System.out.println();
                        System.out.println("Binary encoded message: ");
                        temp = encoder.encodeMessage(input);
                        System.out.println(temp);
                        copyToClipboard(temp);
                    }
                    catch (NoSuchFieldException e) {
                        handleNoSuchFieldException(e);
                    }
                    catch (IOException e) {
                        handleIOException(e);
                    }
                    break;

                //if user wants to decode
                case "decode":
                    try {
                        input = getInput("Enter your message to be decode.");
                        System.out.println();
                        System.out.println("Decoded message: ");
                        System.out.println(decoder.decodeMessage(input));
                    } catch (IOException e) {
                        handleIOException(e);
                    }
                    break;

                //if invalid user input
                default:
                    System.out.println("You have entered an invalid input. Please try again.");
                    break;
            }

            //add some spaces in between loops
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }

        System.out.println("Thanks for using my huffman encoder!");
    }

    /**
     * Gets multi line input from the user. The user must type <<end>> on a new line to
     * denote the end of the input.
     * @return The user's input
     * @throws IOException from BufferedReader
     */
    private String getMultiLineInput(String instructions) throws IOException {
        System.out.println(instructions);
        System.out.println("Please type <<end>> on a new line to mark the end of your input.");

        //accumulates multi line input
        ArrayList<String> inputs = new ArrayList<>();

        String in;
        while(inputs.size() == 0 || !inputs.get(inputs.size() - 1).equals("<<end>>")){
            in = reader.readLine();
            inputs.add(in);
        }

        //first is done automatically to prevent extra new line characters from
        //being added
        String out = inputs.get(0);
        //end at size - 1 as to not get <<end>> as part of the input
        for(int i = 1; i < inputs.size() -1 ; i++){
            out += "\n" + inputs.get(i);
        }

        return out;
    }

    /**
     * Get single line input from the user
     * @param instructions intructions to print
     * @return The user's input
     * @throws IOException from BufferedReader
     */
    private String getInput(String instructions) throws IOException{
        System.out.println(instructions);
        return reader.readLine();
    }

    /**
     * Copies a given string to the clipboard.
     * @param text Text to be copied
     */
    private void copyToClipboard(String text){
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Handles an IOException whenever it is thrown by a BufferedReader
     * @param e IOException being handled
     */
    public void handleIOException(IOException e){
        e.printStackTrace();
        System.out.println("An error has occurred. Please try again.");
    }

    /**
     * Handles a NoSuchFieldException thrown by the encoder
     * @param e NoSuchFieldException being handled
     */
    public void handleNoSuchFieldException(NoSuchFieldException e){
        e.printStackTrace();
        System.out.println("An encoding error has occurred. " +
                "\n Please try again.");
    }
}
