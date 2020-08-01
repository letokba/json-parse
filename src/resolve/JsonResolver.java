package resolve;

import json.JsonArray;
import json.JsonNode;
import json.JsonObject;
import type.JsonValueResolver;
import type.ValueResolver;

import java.io.*;
import java.util.LinkedList;

/**
 * @author Wait
 */
public  class JsonResolver{
    private LinkedList<JsonNode> containerStack = new LinkedList<>();
    private ValueResolver valueResolver;
    private JsonStream in;
    private boolean isSpecial = true;
    private String key;
    private StringBuilder buf = new StringBuilder();


    public JsonResolver() {
        this(new JsonObject(), new JsonValueResolver());
    }

    public JsonResolver(JsonNode container, ValueResolver valueResolver) {
        this.valueResolver = valueResolver;
        this.containerStack.push(container);
    }




    public void resolve(File file) throws IOException {
        resolve(new ImplJsonStream(new FileInputStream(file)));

    }

    public void resolve(JsonStream stream)  {
        this.in = stream;
        int b = -1;
        while ((b = in.read()) != -1) {
            if(charDispatcher((char)b)) {
                break;
            };
        }

    }

    public JsonNode getJsonNode() {
        return containerStack.getFirst();
    }

    /**
     * 字符分发器
     * @return 如果遇到结束字符 "}" or "]"，结束本轮解析
     */
    private boolean charDispatcher(char ch)  {
        if(! this.isSpecial){
            record(ch);
            return false;
        }
        switch (ch) {
            case ':': saveKey(); break;
            case ',': nextItem(); break;
            case '"': toggle(); record(ch); break;
            case '}': endObject(); return true;
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
        this.buf.append(ch);
    }

    private void toggle() {
        this.isSpecial = ! this.isSpecial;
    }



    /**
     * "{"构造字符触发，开启新的对象解析
     */
    private void beginObject()  {
        JsonNode container = getJsonNode();
        JsonNode object = null;
        String key = this.key;
        if(container instanceof JsonObject && key == null){
            object = container;

        }else {
            object = new JsonObject();
            if(key != null) {
                object.setNodeName(key);
            }
            container.appendChild(object);
        }
        this.containerStack.push(object);
        this.resolve(in);
        this.containerStack.pop();
    }


    /**
     * "["构造字符触发，开启新的数组解析
     */
    private void beginArray()  {
        JsonNode array  = new JsonArray();
        String key = this.key;
        if(key != null) {
            array.setNodeName(key);
        }
        this.containerStack.push(array);
        this.resolve(in);
        this.containerStack.pop();
        this.getJsonNode().appendChild(array);
    }


    /**
     * ":"构造字符，记录键值
     */
    private void saveKey() {
        this.key = getBuf();
        empty();
    }


    private void empty() {
        buf.setLength(0);
    }

    private String getBuf() {
        return this.buf.toString();
    }

    /**
     * ","构造字符触发，开启记录下一项
     * 对于对象类型，保存一个属性
     * 对于数组类型，保存一个元素
     */
    private  void nextItem() {
        JsonNode container = getJsonNode();
        String buf = getBuf();
        if(buf.length() == 0){
            return;
        }
        Object value = this.valueResolver.resolve(buf);
        if(container instanceof JsonArray){
            JsonArray array = (JsonArray)container;
            array.add(value);
        }
        if(container instanceof JsonObject){
            String key = this.key;
            JsonObject object = (JsonObject)container;
            object.put(key, value);
            this.key = null;
        }
        empty();
    }

}
