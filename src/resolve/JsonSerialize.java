package resolve;

import json.JsonArray;
import json.JsonObject;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Wait
 *
 * a JsonSerialize be used to serialize a JavaBean or JsonObject or JsonArray,so on.
 * it can create a json text by a object.
 */
public class JsonSerialize {

    /**
     * serialize entry method
     * @param object
     *              a object
     * @return a Json string
     */
    public String serialize(Object object) {
        StringBuilder builder = new StringBuilder();
        serializeValue(object, builder);
        return builder.toString();
    }

    /**
     * serialize JsonObject
     * @param object
     *              a JsonObject
     * @param builder
     *              a json string buffer
     */
    public void serializeObject(JsonObject object, StringBuilder builder) {
        if (object == null) {
            return;
        }
        Map<String, Object> map = object.getMap();
        serializeMap(map, builder);
    }

    /**
     * serialize JsonArray
     * @param array
     *              a JsonArray
     * @param builder
     *              a json string buffer
     */
    public void serializeArray(JsonArray array, StringBuilder builder) {
        if (array == null) {
            return;
        }
        List<Object> list = array.getList();
        serializeList(list, builder);
    }

    /**
     * serialize JavaBean
     * @param bean
     *              a JavaBean
     * @param builder
     *              a json string buffer
     */
    public void serializeJavaBean(Object bean, StringBuilder builder) {
        if (bean == null) {
            return;
        }
        Class<?> cl = bean.getClass();
        Field[] fields = cl.getDeclaredFields();
        builder.append('{');
        for (Field field : fields) {
            String key = field.getName();
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            appendAttribute(key, value, builder);
        }
        builder.setCharAt(builder.length() -1 , '}');
    }

    /**
     * serialize JsonArray
     * @param list
     *              a List
     * @param builder
     *              a json string buffer
     */
    public void serializeList(List list, StringBuilder builder) {
        if(list == null) {
            return;
        }
        if(list.isEmpty()) {
            builder.append("[]");
            return;
        }
        builder.append('[');
        for (Object value : list) {
            serializeValue(value, builder);
            builder.append(',');
        }
        builder.setCharAt(builder.length() -1 , ']');
    }

    /**
     * serialize JsonArray
     * @param map
     *              a Map
     * @param builder
     *              a json string buffer
     */
    public void serializeMap(Map<String, Object> map, StringBuilder builder) {
        if(map == null) {
            return;
        }
        if (map.isEmpty()) {
            builder.append("{}");
            return;
        }
        builder.append('{');
        for(Map.Entry<String, Object> item : map.entrySet()) {
            String key = item.getKey();
            Object value = item.getValue();
            appendAttribute(key, value, builder);
        }

        builder.setCharAt(builder.length() -1 , '}');
    }

    /**
     * append a key-value to the buffer.
     * @param key
     *          a key
     * @param value
     *          a value
     * @param builder
     *          a json string buffer.
     */
    private void appendAttribute(String key, Object value, StringBuilder builder){
        builder.append('"');
        builder.append(key);
        builder.append('"');
        builder.append(':');
        serializeValue(value, builder);
        builder.append(',');
    }

    /**
     * serialize dispatcher
     * @param value
     *              a object
     * @param builder
     *              a json string buffer.
     */
    private void serializeValue(Object value, StringBuilder builder) {
        if(value == null) {
            builder.append("null");
        }
        else if(value instanceof String) {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        }
        else if(isJsonLiteral(value)) {
            builder.append(value);
        }
        else if(value instanceof JsonObject) {
            serializeObject((JsonObject) value, builder);
        }
        else if(value instanceof JsonArray) {
            serializeArray((JsonArray) value, builder);
        }
        else if(value instanceof List) {
            serializeList((List) value, builder);
        }
        else if(value instanceof Map) {
            serializeMap((Map<String, Object>) value, builder);
        }
        else {
            serializeJavaBean(value, builder);
        }
    }

    /**
     * check the value is belong to the Json Literal Value and the Number Type
     * @param value
     *          a object
     * @return if check pass, return true.
     */
    private boolean isJsonLiteral(Object value) {
        return  value instanceof Boolean ||
                value instanceof Number  ||
                value instanceof JsonObject.Null;
    }
}
