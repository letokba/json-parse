package type;

/**
 * @author Wait
 */
public class JsonValueResolver implements ValueResolver {
    private static final String FLAG_FLOUT = ".";


    @Override
    public String parseString(String value) {
        if(value.length() == 2){
            return value;
        }
        return value.substring(1, value.length() - 1);
    }

    @Override
    public Boolean parseBoolean(String value) {
        if(value.equals(Jsonliteral.TRUE.value)){
            return true;
        }
        if(value.equals(Jsonliteral.FALSE.value)) {
            return false;
        }
        return null;
    }

    @Override
    public Number parseNumber(String value) {
        if(value.contains(FLAG_FLOUT)){
            return Double.parseDouble(value);
        }else{
            return Integer.parseInt(value);
        }
    }

    @Override
    public Object parseNull(String value) {
        if(value.equals(Jsonliteral.NULL.value)){
            return new JsonNull();
        }
        return null;
    }


}
