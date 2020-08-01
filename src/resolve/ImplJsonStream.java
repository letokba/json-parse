package resolve;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author Wait
 */
public  class ImplJsonStream  implements JsonStream {
    private BufferedInputStream in;
    private static final int DEFAULT_SIZE = 256;

    private byte[] buf;
    private int pos = 0;
    private int count = 0;
    private boolean isString = false;

    public ImplJsonStream(String json) {
        this(json.length());
        fill(json.getBytes(), json.length());
    }



    public ImplJsonStream(InputStream in) {
        this(DEFAULT_SIZE);
        this.in = new BufferedInputStream(in);
    }

    public ImplJsonStream(int size) {
        this.buf = new byte[size];
    }

    public int getSize() {
        return buf.length;
    }

    public ImplJsonStream() {
        this(DEFAULT_SIZE);
    }

    @Override
    public int read() {
        if(isNeedFill()){
            try {
                readBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(isNeedFill()){
                return -1;
            }
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

    private void readBytes() throws IOException {
        if(in == null) {
            return;
        }
        byte[] buffer = new byte[getSize()];
        int n =  in.read(buffer);
        fill(buffer, n);
    }

    private boolean isNotInvalid(byte b) {
        return b != ' ' && b != '\n' && b != '\r' && b != '\t';
    }
}
