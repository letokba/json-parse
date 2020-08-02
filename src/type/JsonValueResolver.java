package type;

import json.JsonObject;

/**
 * @author Wait
 * a JsonValueResolver is used to resolve the value that is belong to the JSON.
 * it can presolve the any type value for Json.
 * JSON values include String, Number, Object, Array, and three literals( true, false, null)
 */
public class JsonValueResolver implements ValueResolver {
    /**
     * receiving a string and resolve it
     * the method use  Chain Of Responsibility one of the design modes.
     * the resolve order is String -> Boolean -> Null -> Number
     * In Json, the String Type value has the most frequently,
     * so we will firstly check if it's String.
     * if the value check result is OK, start to parse it and return.
     * if it is Sorry, it will be passed to next resolve method.
     * if it keep Sorry, we will return null that is not Null Type.
     *
     * @param value
     *      the resolved string.
     * @return
     *      a object
     */
    @Override
    public  Object resolve(String value){
        return resolveString(value);
    }

    /**
     * resolve the String value that's character is "..."
     * @param value
     *          the resolved string.
     * @return
     *          a String Object
     */
    private Object resolveString(String value){
        if(isStringType(value)) {
            return parseString(value);
        }
        return resolveBoolean(value);
    }


    /**
     * resolve the String value that's character is false or true
     * @param value
     *          the resolved String that is expected to be false or true.
     * @return
     *          a Boolean Object
     */
    private Object resolveBoolean(String value) {
        if(isBooleanType(value)){
            return parseBoolean(value);
        }
        return resolveNull(value);
    }

    /**
     * resolve the String value that's character is null
     * @param value
     *          the resolved String that is expected to be null.
     * @return
     *          a Null Object for JSON
     */
    private Object resolveNull(String value) {
        if(isNullType(value)) {
            return parseNull(value);
        }
        return resolveNumber(value);
    }

    /**
     * resolve the String value that's character like 12, 12.1 so on.
     * @param value
     *          the resolved string that is expected to merely include 0-9 and '.' .
     * @return
     *          a String Object
     */
    private Object resolveNumber(String value) {
        if(isNumberType(value)) {
            return parseNumber(value);
        }
        return null;
    }

    /**
     * an actual parse method for String Type
     * @param value
     *          a string for String Type
     * @return
     *      a String Object
     */
    @Override
    public String parseString(String value) {
        int minLength = 2;
        if(value.length() == minLength){
            // the value is an empty String
            return value;
        }
        return value.substring(1, value.length() - 1);
    }


    /**
     * an actual parse method for Boolean Type
     * @param value
     *          a string for Boolean Type
     * @return
     *      a Boolean Object
     */
    @Override
    public Boolean parseBoolean(String value) {
        if(value.equals(LITERAL_TRUE)){
            return true;
        }
        if(value.equals(LITERAL_FALSE)) {
            return false;
        }
        return null;
    }

    /**
     * an actual parse method for Number Type
     * @param value
     *          a string for Number Type
     * @return
     *      a Number Object
     */
    @Override
    public Number parseNumber(String value) {
        if(value.contains(FLAG_FLOUT)){
            // the number string include The decimal point.
            return Double.parseDouble(value);
        }else{
            return Integer.parseInt(value);
        }
    }

    /**
     * an actual parse method for NULL Type
     * @param value
     *          a string for null one of three literals
     * @return
     *      a Boolean Object
     */
    @Override
    public Object parseNull(String value) {
        if(value.equals(LITERAL_NULL)){
            return new JsonObject.Null();
        }
        return null;
    }

    /**
     * check the value is or not String Type.
     * @param value
     *              a check String
     * @return  if value is String Type return true.
     *
     */
    public boolean isStringType(String value) {
        int lastIndex = value.length() - 1;
        return value.charAt(0) == FLAG_STRING && value.charAt(lastIndex) == FLAG_STRING;
    }

    /**
     * check the value is or not Number Type.
     * @param value
     *              a check String
     * @return  if value is Number Type return true.
     *
     */
    public boolean isNumberType(String value) {
        boolean result = true;
        for (int i = 0; i < value.length(); i++) {
            char c  = value.charAt(i);
            if(c < '0' || c > '9') {
                if(c == FLAG_FLOUT.charAt(0)) {
                    continue;
                }
                result =  false;
                break;
            }
        }
        return result;
    }

    /**
     * check the value is or not Boolean Type.
     * @param value
     *              a check String
     * @return  if value is Boolean Type return true.
     *
     */
    public boolean isBooleanType(String value) {
        return value.equals(LITERAL_TRUE) || value.equals(LITERAL_FALSE);
    }

    /**
     * check the value is or not Null Type.
     * @param value
     *              a check String
     * @return  if value is Null Type return true.
     *
     */
    public boolean isNullType(String value) {
        return value.equals(LITERAL_NULL);
    }



}
