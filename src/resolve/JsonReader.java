package resolve;

import json.JsonException;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Wait
 */
public class JsonReader extends Reader {

    private Reader in;

    /**
     * buffer default size
     */
    private static final int DEFAULT_SIZE = 256;

    /**
     * char buffer
     */
    private char[] cb;


    private int pos, count, size;

    /**
     * a flag that char is or not in "..."
     */
    private boolean isInString = false;


    public JsonReader(String text) {
        this.size = text.length();
        this.cb = new char[this.size];
        this.filterInvalid(text.toCharArray(), this.size);
    }

    public JsonReader(Reader in) {
        this(in, DEFAULT_SIZE);
    }

    public JsonReader(Reader in, int bufSize) {
        if(bufSize <= 0) {
            throw new ArrayIndexOutOfBoundsException("buf size <= 0");
        }
        this.in = in;
        this.size = bufSize;
        this.cb = new char[bufSize];
    }

    @Override
    public synchronized int read()  {
        if(pos >= count){
            try {
                fill();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pos >= count) {
                return -1;
            }
        }
        return this.cb[pos++];
    }

    /**
     * fill the buffer by reading from the I/O and filter invalid character.
     * @throws IOException
     */
    private void fill() throws IOException {
        if(in == null){
            return;
        }
        char[] buffer = new char[this.size];
        int n = read(buffer, 0, this.size);
        filterInvalid(buffer, n);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if(len < 0 ){
            throw new JsonException(" len < 0");
        }
        if((off + len) > cbuf.length) {
            throw new JsonException(" off + len > cbuf.length");
        }
        return in.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        if(in != null) {
            in.close();
        }
    }

    /**
     * filter invalid chars
     * @param buffer
     *          a char array
     * @param n
     *          the char array length
     */
    public void filterInvalid(char[] buffer, int n) {
        this.count = 0;
        for (int i = 0; i<n; i++) {
            char c = buffer[i];
            if(c == '"') {
                // meet the \", change the flag.
                toggle();
            }
            if(isNotInvalid(c) || this.isInString) {
                // record normal char and invalid char in "".
                this.cb[count++] = c;
            }
        }
        this.pos = 0;
    }

    /**
     * check char is or not invalid.
     * @param b
     *          a character
     * @return if b is not invalid, return true.
     */
    private boolean isNotInvalid(char b) {
        return b != ' ' && b != '\n' && b != '\r' && b != '\t';
    }

    /**
     * toggle the flag: isInString
     */
    private void toggle() {
        this.isInString =  ! this.isInString;
    }

}
