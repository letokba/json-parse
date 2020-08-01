package resolve;

import java.io.*;

/**
 * @author Wait
 */
public class JsonFileStream extends AbstractJsonStream {
    private BufferedInputStream in;

    public JsonFileStream(InputStream in) {
        this.in = new BufferedInputStream(in);
    }

    public JsonFileStream(File jsonFile) throws FileNotFoundException {
        this.in = new BufferedInputStream(new FileInputStream(jsonFile));
    }

    @Override
    public int read() throws IOException {
        if(isNeedFill()){
            readBytes();
            if(isNeedFill()){
                return -1;
            }
        }
        return super.read();
    }

    private void readBytes() throws IOException {
        byte[] buffer = new byte[getSize()];
        int n =  in.read(buffer);
        fill(buffer, n);
    }


}
