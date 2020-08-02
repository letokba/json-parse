package resolve;

import json.JsonArray;
import json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        builder.append('{');
        for(Map.Entry<String, Object> item : map.entrySet()) {
            String key = item.getKey();
            Object value = item.getValue();
            builder.append('"');
            builder.append(key);
            builder.append('"');
            builder.append(':');

            serializeValue(value, builder);
            builder.append(',');
        }
        builder.setCharAt(builder.length() -1 , '}');
    }

    public void serializeArray(JsonArray array, StringBuilder builder) {
        List<Object> list = array.getList();
        builder.append('[');
        for (Object value : list) {
            serializeValue(value, builder);
            builder.append(',');
        }
        builder.setCharAt(builder.length() -1 , ']');
    }


    private void serializeValue(Object value, StringBuilder builder) {
        if(value instanceof String) {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        }
        else if(value instanceof JsonObject) {
            serializeObject((JsonObject) value, builder);
        }

        else if(value instanceof JsonArray) {
            serializeArray((JsonArray) value, builder);
        }else {
            builder.append(value);
        }
    }
}
