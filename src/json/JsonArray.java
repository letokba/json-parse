package json;


import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wait
 */
public class JsonArray extends Json  {
    private static final int DEFAULT_CAPACITY = 10;
    private List<Object> list;



    public JsonArray(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    public JsonArray(List list) {
        this();
        this.list = ((JsonArray)toJson(list)).getList();
    }

    public JsonArray() {
        this(DEFAULT_CAPACITY);
    }

    public List<Object> getList() {
        return this.list;
    }


    public int size() {
        return list.size();
    }

    public Object get(int index) {
        if(index >= this.list.size()){
            throw new JsonException("array index >= size");
        }
        return this.list.get(index);
    }

    public <T> T get(int index, Class<T> tClass) {
        Object obj = get(index);
        if(tClass.isInstance(obj)) {
            return tClass.cast(obj);
        }
        return null;
    }

    public Class<?> getEleClass(int index) {
        return get(index).getClass();
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



    public JsonArray put(Object value) {
        if(! isJsonType(value)) {

            this.list.add(toJson(value));
        }else {
            if(value == null) {
                value = new JsonObject.Null();
            }
            this.list.add(value);
        }
        return this;
    }


    public JsonArray put(int index, Object obj) {
        if(! isJsonType(obj)) {

            this.list.add(index, toJson(obj));
        }else {
            if(obj == null) {
                obj = new JsonObject.Null();
            }
            this.list.add(index, obj);
        }
        return this;
    }

    public <T> List<T> toList(Class<T> genericType) {
        return toList(this, genericType);
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
