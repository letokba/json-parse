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


    /**
     * an actual parse method for String Type
     * @param value
     *          a string for String Type
     * @return
     *      a String Object
     */
    public String parseString(String value);


    /**
     * an actual parse method for Boolean Type
     * @param value
     *          a string for Boolean Type
     * @return
     *      a Boolean Object
     */
    public Boolean parseBoolean(String value);

    /**
     * an actual parse method for Number Type
     * @param value
     *          a string for Number Type
     * @return
     *      a Number Object
     */
    public Number parseNumber(String value);

    /**
     * an actual parse method for NULL Type
     * @param value
     *          a string for null one of three literals
     * @return
     *      a Boolean Object
     */
    public Object parseNull(String value);




}
