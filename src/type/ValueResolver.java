package type;

/**
 * @author Wait
 */
public interface ValueResolver {

    /**
     * 解析JSON中的值
     * @param value a string
     * @return a Object
     */
    public Object resolve(String value);

    /**
     * parse a string to String Object
     * @param value
     *              a string
     * @return a String Object
     */
    public String parseString(String value);

    /**
     * parse a string to Boolean Object
     * @param value
     *              a string
     * @return a Boolean Object
     */
    public Boolean parseBoolean(String value);

    /**
     * parse a string to Number Object
     * @param value
     *              a string
     * @return a Number Object
     */
    public Number parseNumber(String value);

    /**
     * parse a string to Null Object
     * @param value
     *              a string
     * @return a Null Object
     */
    public Object parseNull(String value);

    /**
     * check String Type
     * @param value
     *          a string
     * @return <p>boolean</p>
     */
    public boolean isStringType(String value);
    /**
     * check Boolean Type
     * @param value
     *          a string
     * @return <p>boolean</p>
     */
    public boolean isBooleanType(String value);

    /**
     * check Number Type
     * @param value
     *          a string
     * @return <p>boolean</p>
     */
    public boolean isNumberType(String value);

    /**
     * check Null Type
     * @param value
     *          a string
     * @return <p>boolean</p>
     */
    public boolean isNullType(String value);

}
