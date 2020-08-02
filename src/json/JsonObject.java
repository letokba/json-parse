package json;

import resolve.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wait
 */
public class JsonObject  implements Json {
    private static final int DEFAULT_CAPACITY = 10;
    private Map<String , Object> map;

    public static class Null {

        @Override
        public String toString() {
            return "null";
        }


        @Override
        protected Object clone() {
            return this;
        }

        @Override
        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }
    }



    public JsonObject(Map<String, Object> map) {
        this.map = map;
    }

    public JsonObject(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    public JsonObject() {
        this(DEFAULT_CAPACITY);
    }

    public Object get(String name) {
        Object obj = this.map.get(name);
        if(obj == null) {
            throw new JsonException("object[\"" + name + "\"] is not exist");
        }
        return obj;
    }


    public String getString(String name) {
        Object obj = get(name);
        if(obj instanceof String) {
            return (String)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not String");
    }

    public Boolean getBoolean(String name) {
        Object obj = get(name);
        if( obj instanceof Boolean) {
            return (Boolean)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not Boolean");
    }

    public Boolean getJsonNull(String name) {
        Object obj = get(name);
        if( obj instanceof Boolean) {
            return (Boolean)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not Null");
    }


    public int getInt(String name) {
        Object obj = get(name);
        if(obj instanceof Integer) {
            return (Integer)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not Integer");
    }

    public double getDouble(String name) {
        Object obj = get(name);
        if(obj instanceof Double) {
            return (Double) obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not Double");
    }

    public long getLong(String name) {
        Object obj = get(name);
        if(obj instanceof Long) {
            return (Long)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not Long");
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public JsonObject put(String name, Object value) {
        if(value == null) {
            value = new Null();
        }
        this.map.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
