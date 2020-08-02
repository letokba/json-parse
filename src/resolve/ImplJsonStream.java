package resolve;

import java.io.*;


/**
 * @author Wait
 */
public  class ImplJsonStream  implements JsonStream {
    private BufferedReader reader;
    private static final int DEFAULT_SIZE = 256;

    private char[] buf;
    private int pos = 0;
    private int count = 0;
    private boolean isString = false;

    public ImplJsonStream(String json) {
        this(json.length());
        fill(json.toCharArray(), json.length());
    }



    public ImplJsonStream(Reader reader) {
        this(DEFAULT_SIZE);
        this.reader = new BufferedReader(reader);
    }

    public ImplJsonStream(int size) {
        this.buf = new char[size];
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

    private void inspectQuotation(char b) {
        if(b == '"'){
            this.isString = !isString;
        }
    }

    public boolean isNeedFill() {
        return pos >= count;
    }

    public void fill(char[] buffer, int n)  {
        this.count = 0;
        for (int i = 0; i < n; i++) {
            char b = buffer[i];
            inspectQuotation(b);
            if(isNotInvalid(b) || isString){
                buf[count++] = b;
            }
        }

        this.pos = 0;
    }

    private void readBytes() throws IOException {
        if(this.reader == null) {
            return;
        }
        char[] buffer = new char[getSize()];
        int n =  this.reader.read(buffer);
        fill(buffer, n);
    }

    private boolean isNotInvalid(char b) {
        return b != ' ' && b != '\n' && b != '\r' && b != '\t';
    }
}
