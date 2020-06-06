public class Test {

    public static void main(String args[]) throws NoSuchFieldException {
        HuffmanEncoder encoder = new HuffmanEncoder();
        String text = "this is a test";

        String encoded = encoder.encodeMessage(text, true);
        System.out.println("Encoded: " + encoded);
    }
}
