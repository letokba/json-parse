package resolve;

import java.io.IOException;

/**
 * @author Wait
 */
public class JsonStringStream extends AbstractJsonStream {

    public JsonStringStream(String s) {
        super(s.length());
        fill(s.getBytes(), s.length());
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    public static void main(String[] args) throws IOException {
        String json = "{ds\"ss\nss\"}";
        JsonStringStream stream = new JsonStringStream(json);
        int b = -1;
        while ((b = stream.read()) != -1) {
            System.out.print((char)b);
        }
    }
}
