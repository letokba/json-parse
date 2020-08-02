package type;

/**
 * @author Wait
 */
public interface ValueResolver {

    /**
     *  a JSON literal <code>true</code> for Boolean Type
     */
    public static final String LITERAL_TRUE = "true";

    /**
     *  a JSON literal <code>false</code> for Boolean Type
     */
    public static final String LITERAL_FALSE = "false";

    /**
     *  a JSON literal <code>null</code> for Null Type
     */
    public static final String LITERAL_NULL = "null";

    /**
     *  the flag for String Type
     */
    public static final char FLAG_STRING = '"';

    /**
     *  a flag for Float or Double Type.
     */
    public static final String FLAG_FLOUT = ".";


    /**
     * 解析JSON中的值
     * @param value a string
     * @return a Object
     */
    public Object resolve(String value);



}
