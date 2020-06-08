import Interfaces.GUIBasedHuffman;
import Interfaces.TextBasedHuffman;

import java.io.IOException;

public class Test {

    public static void main(String args[]) throws NoSuchFieldException, IOException {
       TextBasedHuffman tbh = new TextBasedHuffman();
       GUIBasedHuffman gbh = new GUIBasedHuffman();
       gbh.run();
    }
}
