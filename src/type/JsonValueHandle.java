package type;



/**
 * @author Wait
 */
public class JsonValueHandle implements ValueHandler {

    private TypeAssert typeAssert = new JsonTypeAssert();
    private ValueResolver valueResolver = new JsonValueResolver();



    public void setTypeAssert(TypeAssert typeAssert) {
        this.typeAssert = typeAssert;
    }

    public void setValueResolver(ValueResolver valueResolver) {
        this.valueResolver = valueResolver;
    }

    @Override
    public  Object resolve(String value){
        return resolveString(value);
    }

    private Object resolveString(String value){
        if(typeAssert.isStringType(value)) {
            return valueResolver.parseString(value);
        }
        return resolveBoolean(value);
    }



    private Object resolveBoolean(String value) {
        if(typeAssert.isBooleanType(value)){
            return valueResolver.parseBoolean(value);
        }
        return resolveNull(value);
    }

    private Object resolveNull(String value) {
        if(typeAssert.isNullType(value)) {
            return valueResolver.parseNull(value);
        }
        return resolveNumber(value);
    }


    private Object resolveNumber(String value) {
        if(typeAssert.isNumberType(value)) {
            return valueResolver.parseNumber(value);
        }
        return null;
    }

    public static void main(String[] args) {
        JsonValueHandle handle = new JsonValueHandle();
        String o = (String)handle.resolveString("\"\"");
        System.out.println(o);
    }


}
