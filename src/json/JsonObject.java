package json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wait
 */
public class JsonObject  extends Json {
    private static final int DEFAULT_CAPACITY = 10;
    private Map<String , Object> map;


    /**
     * a Null be used to expressed as the literal value "null"
     */
    public static class Null extends Json {
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


    /**
     * construct a JsonObject by getting the map
     * @param map
     *          a Map Object
     */
    public JsonObject(Map<String, Object> map) {
        this();
        this.map = ((JsonObject)toJson(map)).getMap();
    }

    /**
     * construct a JsonObject by getting the initial capacity of map
     * @param initialCapacity
     *                       the map's initial capacity
     */
    public JsonObject(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    /**
     * default constructor, the map will use the default size.
     */
    public JsonObject() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * get a field by the name.
     * @param name
     *           field's key
     * @return
     *          field's value
     */
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

    public JsonObject getJsonObject(String name) {
        Object obj = get(name);
        if(obj instanceof JsonObject) {
            return (JsonObject)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not JsonObject");
    }

    public JsonArray getJsonArray(String name) {
        Object obj = get(name);
        if(obj instanceof JsonArray) {
            return (JsonArray)obj;
        }
        throw new JsonException("object[\"" + name + "\"] is not JsonArray");
    }

    /**
     * JsonObject and Map exchange
     * @return
     *         a Map object
     */
    public Map<String, Object> getMap() {
        return this.map;
    }

    public <T> T get(String name, Class<T> cl) {
        Object obj = get(name);
        if(cl.isInstance(obj)) {
            return cl.cast(obj);
        }
        return null;
    }



    /**
     * put a field has the key and value in map.
     * only save in the JsonArray and don't completely solve the object.
     * So, if you put a javaBean, the JsonArray look is very funny.
     * Because, it don't one-one mapping with the Json String.
     * But you can get legal Json String by using <code>toJsonString()</code>
     * @param name
     *          field's key
     * @param value
     *          field's value
     * @return
     *          new JsonObject
     */
    public JsonObject put(String name, Object value) {
        if(value == null) {
            value = new Null();
        }
        this.map.put(name, value);
        return this;
    }

    /**
     * the JsonObject translate to a JavaBean.
     * all field of the JavaBean must be in the JsonObject.
     * @param tClass
     *              the JavaBean Type
     * @return
     *          a JavaBean instance.
     */
    public <T> T getJavaBean(Class<T> tClass){
        return toJavaBean(this, tClass);
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
