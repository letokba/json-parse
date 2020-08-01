package type;

/**
 * @author Wait
 */
public class JsonTypeAssert implements TypeAssert{

    @Override
    public boolean isStringType(String value) {

        int lastIndex = value.length() - 1;
        char flag = JsonFlag.FLAG_STRING.code;
        return value.charAt(0) == flag && value.charAt(lastIndex) == flag;
    }

    @Override
    public boolean isNumberType(String value) {
        return true;
    }

    @Override
    public boolean isBooleanType(String value) {
        return value.equals(Jsonliteral.FALSE.value) || value.equals(Jsonliteral.TRUE.value);
    }

    @Override
    public boolean isNullType(String value) {
        return value.equals(Jsonliteral.NULL.value);
    }

    @Override
    public boolean isObjectType(String value) {
        boolean start = value.charAt(0) == JsonFlag.OBJECT_BEGIN.code;
        boolean end = value.charAt(value.length() - 1) == JsonFlag.OBJECT_END.code;
        return start && end;
    }

    @Override
    public boolean isArrayType(String value) {
        boolean start = value.charAt(0) == JsonFlag.ARRAY_BEGIN.code;
        boolean end = value.charAt(value.length() - 1) == JsonFlag.ARRAY_END.code;
        return start && end;
    }
}
