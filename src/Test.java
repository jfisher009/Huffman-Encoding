public class Test {

    public static void main(String args[]) throws NoSuchFieldException {
        HuffmanEncoder encoder = new HuffmanEncoder();
        HuffmanDecoder decoder = new HuffmanDecoder();
        String text = "this is a test";

        System.out.println("Original: " + text);

        String encoded = encoder.encodeMessage(text, true);
        System.out.println("Encoded: " + encoded);

        String decoded = decoder.decodeMessage(encoded);
        System.out.println("Decoded: " + decoded);

    }
}
