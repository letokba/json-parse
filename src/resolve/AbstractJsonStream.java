package resolve;

import java.io.IOException;


/**
 * @author Wait
 */
public abstract class AbstractJsonStream  implements JsonStream {

    private static final int DEFAULT_SIZE = 256;

    private byte[] buf;
    private int pos = 0;
    private int count = 0;
    private boolean isString = false;

    public AbstractJsonStream(int size) {
        this.buf = new byte[size];
    }

    public int getSize() {
        return buf.length;
    }

    public AbstractJsonStream() {
        this(DEFAULT_SIZE);
    }

    @Override
    public int read() throws IOException {
        if(isNeedFill()){
            return -1;
        }
        int b = buf[pos++];

        return b;
    }

    private void inspectQuotation(int b) {
        if(b == '"'){
            this.isString = !isString;
        }
    }

    public boolean isNeedFill() {
        return pos >= count;
    }

    public void fill(byte[] buffer, int n)  {
        this.count = 0;
        for (int i = 0; i < n; i++) {
            byte b = buffer[i];
            inspectQuotation(b);
            if(isNotInvalid(b) || isString){

                buf[count++] = b;
            }
        }

        this.pos = 0;
    }

    private boolean isNotInvalid(byte b) {
        return b != ' ' && b != '\n' && b != '\r' && b != '\t';
    }
}
