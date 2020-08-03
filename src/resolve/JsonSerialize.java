package resolve;

import json.JsonArray;
import json.JsonObject;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Wait
 */
public class JsonSerialize {


    public String serialize(Object object) {
        StringBuilder builder = new StringBuilder();
        serializeValue(object, builder);
        return builder.toString();
    }


    public void serializeObject(JsonObject object, StringBuilder builder) {
        Map<String, Object> map = object.getMap();
        serializeMap(map, builder);
    }

    public void serializeArray(JsonArray array, StringBuilder builder) {
        List<Object> list = array.getList();
        serializeList(list, builder);
    }

    public void serializeJavaBean(Object bean, StringBuilder builder) {
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

    public void serializeList(List list, StringBuilder builder) {
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

    public void serializeMap(Map<String, Object> map, StringBuilder builder) {
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

    private void appendAttribute(String key, Object value, StringBuilder builder){
        builder.append('"');
        builder.append(key);
        builder.append('"');
        builder.append(':');
        serializeValue(value, builder);
        builder.append(',');
    }

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


    private boolean isJsonLiteral(Object value) {
        return  value instanceof Boolean ||
                value instanceof Number  ||
                value instanceof JsonObject.Null;
    }
}
