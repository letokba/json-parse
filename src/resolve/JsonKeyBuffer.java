package resolve;

/**
 * @author Wait
 */
public class JsonKeyBuffer {
    private StringBuilder buf = new StringBuilder();
    private String key;
    private boolean isString = false;

    public boolean isString() {
        return isString;
    }

    public void write(char b){
        inspectQuotation(b);
        buf.append(b);
    }

    public void empty() {
        buf.setLength(0);
    }

    public void setKey(){
        key = buf.toString().substring(1, buf.length() -1);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getBuf() {
        return buf.toString();
    }

    private void inspectQuotation(char b){
        if(b == '"'){
            this.isString = ! this.isString;
        }
    }
}
