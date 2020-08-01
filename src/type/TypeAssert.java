package type;

/**
 * @author Wait
 */
public interface TypeAssert {

    /**
     * 判断字符串类型
     */
    public boolean isStringType(String value);

    /**
     * 判断数字类型
     */
    public boolean isNumberType(String value);

    /**
     * 判断布尔类型
     */
    public boolean isBooleanType(String value);

    /**
     * 判断NULL类型
     */
    public boolean isNullType(String value);

    public boolean isObjectType(String value);

    public boolean isArrayType(String value);


}
