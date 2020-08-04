package json;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Wait
 */
public class JsonArray extends Json {
    private static final int DEFAULT_CAPACITY = 10;
    private List<Object> list;

    /**
     * construct a JsonArray by assigning the initial capacity.
     * @param initialCapacity
     *                      the JsonArray's init capacity
     */
    public JsonArray(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    /**
     * construct a JsonArray by assigning the items.
     * @param list
     *             a list.
     */
    public JsonArray(List list) {
        this();
        this.list = ((JsonArray)toJson(list)).getList();
    }

    /**
     * construct a JsonArray by using the default init capacity.
     */
    public JsonArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * I don't wish to expose the inner list object.
     * but other methods need it.
     * the method would remove.
     */
    public List<Object> getList() {
        return this.list;
    }

    /**
     * get the JsonArray size
     */
    public int size() {
        return list.size();
    }

    /**
     * the general method to get the element from JsonArray.
     * @param index
     *             the index for needing element
     * @return
     *        the specified element.
     */
    public Object get(int index) {
        if(index >= this.list.size()){
            throw new JsonException("array index >= size");
        }
        return this.list.get(index);
    }

    /**
     * get a specified element by assigning the type.
     * @param tClass
     *              the element's Type
     * @return
     *          a element has specified type.
     */
    public <T> T get(int index, Class<T> tClass) {
        Object obj = get(index);
        if(tClass.isInstance(obj)) {
            return tClass.cast(obj);
        }
        return null;
    }

    /**
     * look over a element's type
     */
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


    /**
     * put a object to this JsonArray.
     * only save in the JsonArray and don't completely solve the object.
     * So, if you put a javaBean, the JsonArray look is very funny.
     * Because, it don't one-one mapping with the Json String.
     * But you can get legal Json String by using <code>toJsonString()</code>
     *
     *
     * through it allow to put objects of different types,
     * but as mush as possible put common type.
     *
     * @param obj
     *              a object
     * @return a new JsonArray.
     */
    public JsonArray put(Object obj) {
        if(obj == null) {
            obj = new JsonObject.Null();
        }
        this.list.add(obj);
        return this;
    }


    /**
     * put a object but don't serialize the object.
     * you can use <code>get(int,Object)</code> to get same Object.
     * @param index
     *             the object's position
     * @param obj
     *              a object
     * @return a new JsonArray.
     */
    public JsonArray put(int index, Object obj) {
        if(obj == null) {
            obj = new JsonObject.Null();
        }
        this.list.add(index, obj);
        return this;
    }

    /**
     * the JsonArray to a List Object.
     * @param genericType
     *          the object's type that saved in list.
     * @return  a list
     */
    public <T> List<T> toList(Class<T> genericType) {
        return toList(this, genericType);
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
