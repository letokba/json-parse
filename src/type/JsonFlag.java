package type;

/**
 * @author Wait
 */
public enum JsonFlag {
    /**
     * 对象环境开启标志
     */
    OBJECT_BEGIN('{'),
    /**
     * 对象环境结束标志
     */
    OBJECT_END('}'),
    /**
     * 数组环境开启标志
     */
    ARRAY_BEGIN('['),
    /**
     * 数组类型结束标志
     */
    ARRAY_END(']'),

    FLAG_STRING('"'),

    FLAG_VALUE(':'),

    FLAG_NEXT(',');


    public final char code;

    JsonFlag(char c) {
        this.code = c;
    }


}
