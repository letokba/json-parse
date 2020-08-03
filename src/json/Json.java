package json;

import org.json.JSONException;
import resolve.JsonResolver;
import resolve.JsonSerialize;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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


    /**
     * JsonObject to javaBean
     * @param json
     *          a JsonObject that is a JavaBean map
     * @param cl
     *          the JavaBean's Class
     * @param <T>
     *          the JavaBean's type
     * @return
     *          a instance for the JavaBean.
     */
    public static  <T> T  toJavaBean(JsonObject json, Class<T> cl) {
        if(json == null || cl == null) {
            return null;
        }
        try {
            // 1. 获取JavaBean的所有字段
            Field[] fields = cl.getDeclaredFields();
            // 2. 创建一个JavaBean的实例
            T t =  cl.newInstance();
            for(Field field : fields) {
                field.setAccessible(true);
                // 3. 获取字段的签名
                String name = field.getName();
                // 4. 根据字段的签名从JsonObject对象中查找对应的属性值
                Object value = json.get(name);
                if(value instanceof JsonObject.Null) {
                    // 如果属性值为字面量Null，则该字段不设置值
                }else if(value instanceof JsonArray) {
                    JsonArray array = (JsonArray)value;
                    // 5.2 获取字段的泛型类型
                    Type genericType = field.getGenericType();
                    if(genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType)genericType;
                        // 5.3 如果是参数化类型，就获取列表的泛型类型
                        Type  type = pt.getActualTypeArguments()[0];
                        // 5.4 JsonArray 映射成Java List
                        List<?> objects = toList(array, (Class<?>) type);
                        field.set(t, objects);
                    }
                }else if(value instanceof JsonObject) {
                    // 对应于类的组合
                    Object obj = toJavaBean((JsonObject) value, field.getType());
                    field.set(t, obj);
                }else {
                    // 对应于基本数据类型
                    field.set(t, value);
                }
            }
            return  t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JsonArray to Java List
     * @param array
     *              a Java List
     * @param genericType
     *              the List<T>'s generic type
     * @param <T>
     *              the class type
     * @return   a list
     */
    public static <T> List<T> toList(JsonArray array, Class<T> genericType) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i<array.size(); i++) {
           // 1. 查看element的类型
            Class<?> eleClass = array.getEleClass(i);
            if(eleClass == JsonObject.class) {
                // 2. 如果存放的是JsonObject类型，需要深度转换
                JsonObject jsonObject = array.get(i, JsonObject.class);
                T t = toJavaBean(jsonObject, genericType);
                list.add(t);
            }else {
                // 3. 反之，可以直接加入相应的类型
                T t = array.get(i, genericType);
                list.add(t);
            }
        }
        return list;
    }
}
