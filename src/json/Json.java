package json;

import resolve.JsonResolver;
import resolve.JsonSerialize;

import java.lang.reflect.Field;
import java.util.List;


/**
 * @author Wait
 */
public abstract class Json {

    /**
     * Json Object serialize to Json string.
     * @return
     *      a json string
     */
    public String toJsonString() {
        return new JsonSerialize().serialize(this);
    }

    /**
     * Java Object to Json.
     * @param object
     *          a java Object
     * @return
     *          Json abject
     */
     public static Json toJson(Object object) {
        String s = new JsonSerialize().serialize(object);
        return new JsonResolver().resolve(s);
    }

    /**
     * check object is or not Json Type
     * @param object
     *              checked object
     * @return if check pass, return true.
     */
     public boolean isJsonType(Object object) {
        return  object instanceof Json ||
                object instanceof String ||
                object instanceof Boolean ||
                object instanceof Number ||
                object == null;
    }


}
