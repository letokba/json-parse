package json;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wait
 */
public class JsonArray implements Json {
    private static final int DEFAULT_CAPACITY = 10;
    private ArrayList<Object> list;


    public JsonArray(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    public JsonArray(List<Object> list) {
        this(list.size());
        this.list.addAll(list);
    }

    public JsonArray() {
        this(DEFAULT_CAPACITY);
    }

    public Object get(int index) {
        if(index >= this.list.size()){
            throw new JsonException("array index >= size");
        }
        return this.list.get(index);
    }

    public String getString(int index) {
        Object obj = get(index);
        if(obj instanceof String) {
            return (String)obj;
        }
        throw new JsonException("array[" + index + "] is not String");
    }

    public Boolean getBoolean(int index) {
        Object obj = get(index);
        if( obj instanceof Boolean) {
            return (Boolean)obj;
        }
        throw new JsonException("array[" + index + "] is not Boolean");
    }

    public Boolean getJsonNull(int index) {
        Object obj = get(index);
        if( obj instanceof Boolean) {
            return (Boolean)obj;
        }
        throw new JsonException("array[" + index + "] is not Null");
    }


    public int getInt(int index) {
        Object obj = get(index);
        if(obj instanceof Integer) {
            return (Integer)obj;
        }
        throw new JsonException("array[" + index + "] is not Integer");
    }

    public double getDouble(int index) {
        Object obj = get(index);
        if(obj instanceof Double) {
            return (Double) obj;
        }
        throw new JsonException("array[" + index + "] is not Double");
    }

    public long getLong(int index) {
        Object obj = get(index);
        if(obj instanceof Long) {
            return (Long)obj;
        }
        throw new JsonException("array[" + index + "] is not Long");
    }

    public JsonObject getJsonObject(int index) {
        Object obj = get(index);
        if(obj instanceof JsonObject) {
            return (JsonObject) obj;
        }
        throw new JsonException("array[" + index + "] is not JsonObject");
    }

    public JsonArray getJsonArray(int index) {
        Object obj = get(index);
        if(obj instanceof JsonArray) {
            return (JsonArray) obj;
        }
        throw new JsonException("array[" + index + "] is not JsonArray");
    }



    public JsonArray put(Object obj) {
        if(obj == null){
            obj = new Null();
        }
        this.list.add(obj);
        return this;
    }

    public JsonArray putString(String obj){

        return put(obj);
    }

    public JsonArray putBoolean(Boolean obj){
        if(obj == null){
            throw new JsonException("the Boolean object is null");
        }
        return put(obj);
    }

    public JsonArray putInteger(Integer obj){
        if(obj == null){
            throw new JsonException("the Integer object is null");
        }
        return put(obj);
    }

    public JsonArray putLong(Long obj){
        if(obj == null){
            throw new JsonException("the Long object is null");
        }
        return put(obj);
    }

    public JsonArray putDouble(Double obj){
        if(obj == null){
            throw new JsonException("the Double object is null");
        }
        return put(obj);
    }

    public JsonArray putJsonNull(Null obj){
        if(obj == null){
            throw new JsonException("the Null object is null");
        }
        return put(obj);
    }


    public JsonArray putJsonObject(JsonObject obj) {
        if(obj == null){
            throw new JsonException("the JsonObject object is null");
        }
        return put(obj);
    }

    public JsonArray putJsonArray(JsonArray obj) {
        if(obj == null){
            throw new JsonException("the JsonArray object is null");
        }
        return put(obj);
    }

    public JsonArray putList(List<Object> list) {
        if(list == null) {
            throw new JsonException("the List object is null");
        }
        return put(new JsonArray(list));
    }

    public JsonArray putMap(Map<String, Object> map) {
        if(map == null) {
            throw new JsonException("this Map object is null");
        }
        return put(new JsonObject(map));
    }
}
