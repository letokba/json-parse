package type;

/**
 * @author Wait
 */
public enum Jsonliteral {
    /**
     * 布尔量 false
     */
    FALSE("false"),
    /**
     * 布尔量 true
     */
    TRUE("true"),
    /**
     * 空值 null
     */
    NULL("null");

    public final String value;

    Jsonliteral(String value){
        this.value =value;
    }
}
