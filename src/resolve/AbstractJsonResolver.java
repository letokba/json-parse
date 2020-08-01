package resolve;

import json.JsonArray;
import json.JsonNode;
import json.JsonObject;
import type.ValueResolver;

import java.io.*;

/**
 * @author Wait
 */
public abstract class AbstractJsonResolver implements JsonResolver {
    private JsonNode container;
    private ValueResolver valueResolver;
    private JsonStream in;
    private JsonKeyBuffer buffer = new JsonKeyBuffer();

    public JsonNode getContainer() {
        return container;
    }

    public AbstractJsonResolver(JsonNode container, ValueResolver valueResolver) {
        this.container = container;
        this.valueResolver = valueResolver;
    }


    @Override
    public void resolve(File file) throws IOException {
        resolve(new JsonFileStream(file));

    }

    @Override
    public void resolve(JsonStream stream) throws IOException {
        this.in = stream;
        int b = -1;
        while ((b = in.read()) != -1) {
            if(charDispatcher((char)b)) {
                break;
            };
        }

    }

    /**
     * 字符分发器
     * @return 如果遇到结束字符 "}" or "]"，结束本轮解析
     * @throws IOException
     */
    private boolean charDispatcher(char ch) throws IOException {
        if(buffer.isString()){
            record(ch);
            return false;
        }
        switch (ch) {
            case '}': endObject(); return true;
            case ':': saveKey(); break;
            case ',': nextItem(); break;
            case '{': beginObject(); break;
            case '[': beginArray(); break;
            case ']': endArray(); return true;
            default: record(ch);
        }
        return false;
    }

    /**
     * "}"构造字符，结束本轮对象解析
     */
    private void endObject() {
        nextItem();
    }

    /**
     * "]"构造字符，结束本轮数组解析
     */
    private void endArray() {
        nextItem();
    }

    /**
     * 记录常规字符
     */
    private void record(char ch) {
        buffer.write(ch);
    }

    /**
     * ":"构造字符，记录键值
     */
    private void saveKey() {
        buffer.setKey();
        buffer.empty();
    }

    /**
     * "{"构造字符触发，开启新的对象解析
     * @throws IOException
     */
    private void beginObject() throws IOException {
        JsonNode object = null;
        String key = this.buffer.getKey();
        if(this.container instanceof JsonObject && key == null){
            object = this.container;

        }else {
            object = new JsonObject();
            if(key != null) {
                object.setNodeName(key);
            }
            this.container.appendChild(object);
        }
        new JsonObjectResolver(object, valueResolver).resolve(in);
    }


    /**
     * "["构造字符触发，开启新的数组解析
     * @throws IOException
     */
    private void beginArray() throws IOException {
        JsonNode array  = new JsonArray();
        String key = this.buffer.getKey();
        if(key != null) {
            array.setNodeName(key);
        }
        new JsonArrayResolver(array, valueResolver).resolve(in);
        this.container.appendChild(array);
    }


    /**
     * ","构造字符触发，开启记录下一项
     * 对于对象类型，保存一个属性
     * 对于数组类型，保存一个元素
     */
    private  void nextItem() {
        String buf = this.buffer.getBuf();
        if(buf.length() == 0){
            return;
        }
        Object value = this.valueResolver.resolve(buf);
        if(container instanceof JsonArray){
            JsonArray array = (JsonArray)container;
            array.add(value);
        }
        if(container instanceof JsonObject){
            String key = this.buffer.getKey();
            JsonObject object = (JsonObject)container;
            object.put(key, value);
            this.buffer.setKey(null);
        }
        this.buffer.empty();
    }

}
