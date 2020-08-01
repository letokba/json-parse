package type;

/**
 * @author Wait
 */
public interface ValueResolver {

    /**
     * 解析字符串
     */
    public String parseString(String value);

    /**
     * 解析布尔值
     */
    public Boolean parseBoolean(String value);

    /**
     * 解析数字
     */
    public Number parseNumber(String value);

    /**
     * 解析JSON中 NULL类型
     */
    public Object parseNull(String value);


}
