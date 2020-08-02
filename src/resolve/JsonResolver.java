package resolve;

import json.Json;
import json.JsonArray;
import json.JsonObject;
import type.JsonValueResolver;
import type.ValueResolver;

import java.io.*;

/**
 * @author Wait
 *
 * a JsonResolver is used to resolve the sson string or the json file.
 * it can make the json string exchange to a Json Object.
 * the Json Object can be <code>JsonObject</code> or <code>JsonArray</code>
 * in the whole resolved process, firstly, it will dispatch the character
 * from the <code>JsonStream</code>. secondly, because the char can in  string
 * or out of string, acccording the char, select suit method to excute.
 *
 * of course, the json string only has a tier, so the JsonResolver could
 * open one or more resoler to completely resolve the json.
 * suit method to deal with
 */
public  class JsonResolver{

    /**
     * a Json container include JsonObject and JsonArray.
     * the resolved value will put to the container.
     */
    private Json container;

    /**
     * a Json value resolver.
     */
    private ValueResolver valueResolver;

    /**
     * a json data stream that implement the <code>JsonSteam</code> Interface
     * the JsonResolver Object resolve the character from the steam
     */
    private JsonStream in;

    /**
     * the flag to toggle the dispatcher pattern
     */
    private static final char FLAG_TOGGLE = '"';

    /**
     * show that the processing character is or not Special Char
     * default is true, show that will process Json Construct-Char
     */
    private boolean isSpecialPattern = true;

    /**
     * cache the key of JsonObject.
     */
    private String key;

    /**
     * record the normal character.
     */
    private StringBuilder buf = new StringBuilder();

    /**
     * construct a JsonResolver Object
     */
    public JsonResolver() {
        this(null, new JsonValueResolver());
    }


    /**
     * construct a JsonResolver Object and
     * @param container
     *                  a Json Type one of (JsonObject or JsonArray)
     * @param valueResolver
     *                  a Json Value Resolver
     */
    public JsonResolver(Json container, ValueResolver valueResolver) {
        this.valueResolver = valueResolver;
        this.container = container;
    }


    /**
     * resolve the json file
     * @param file
     *              the Json File Format
     * @throws IOException
     */
    public void resolve(File file) throws IOException {
        resolve(new ImplJsonStream(new FileInputStream(file)));

    }

    /**
     * resolve the Json Char Stream
     *
     * @param stream
     *              a Json data stream
     */
    public void resolve(JsonStream stream)  {
        this.in = stream;
        int b = -1;
        while ((b = in.read()) != -1) {
            if(charDispatcher((char)b)) {
                break;
            };
        }

    }

    /**
     * get the current Json Container one of (JsonArray or JsonObject)
     *
     * @return a Json Object inherited the interface of <code>Json</code>
     */
    public Json getJson() {
        return this.container;
    }

    public void setContainer(Json json) {
        this.container = json;
    }

    /**
     * get the value cached in the buf field.
     *
     * @return a String
     */
    private String getBuf() {
        return this.buf.toString();
    }

    /**
     * empty the buffer.
     */
    private void empty() {
        buf.setLength(0);
    }



    /**
     * a main method for the Json Resolver to dispatch each character.
     * the Dispatcher has two patterns.
     * one is Normal Pattern to directly record the character,
     * the other is Special Pattern to recognize six Json Construct-Character
     * and jump to specific method.
     *
     * @return <p>Boolean</p> if meet ending-character return true.
     */
    private boolean charDispatcher(char ch)  {
        if(ch == FLAG_TOGGLE){
            toggle();
        }
        if(! isSpecialPattern) {
            record(ch);
            return false;
        }
        switch (ch) {
            case '{': beginObject(); break;
            case '}': endObject(); return true;
            case '[': beginArray(); break;
            case ']': endArray(); return true;
            case ':': saveKey(); break;
            case ',': nextItem(); break;
            default: record(ch);
        }
        return false;
    }

    /**
     * toggle the pattern of CharDispatcher
     */
    private void toggle() {
        this.isSpecialPattern = ! this.isSpecialPattern;
    }

    /**
     * record the common char.
     */
    private void record(char ch) {
        this.buf.append(ch);
    }

    /**
     * one of six construct chars is "}"
     * indicate the JsonObject has resolved.
     */
    private void endObject() {
        nextItem();
    }

    /**
     * one of six construct chars is "]"
     * indicate the JsonArray has resolved.
     */
    private void endArray() {
        nextItem();
    }

    /**
     * one of six construct chars is "{"
     * indicate that start to resolve a new JsonObject.
     */
    private void beginObject()  {
        if(getJson() == null) {
            // if don't set container, default set JsonObject.
            setContainer(new JsonObject());
            return;
        }
        Json object = new JsonObject();
        startResolver(object);
    }

    /**
     * one of six construct chars is "["
     * indicate that start to resolve a new JsonArray.
     */
    private void beginArray()  {
        if(getJson() == null) {
            // if don't set container, default set JsonArray.
            setContainer(new JsonArray());
            return;
        }
        Json array  = new JsonArray();
        startResolver(array);
    }

    /**
     * create a new resolver
     * before new resolve, need to put the new container to current container.
     * @param nextContainer
     *            new Resolver Object's container
     */
    private void startResolver(Json nextContainer) {
        addToContainer(nextContainer);
        new JsonResolver(nextContainer, valueResolver).resolve(in);
    }

    /**
     * one of six construct chars is ":"
     * indicate that the key has record.
     * the next, it will start to record its value that is Json Type Value.
     */
    private void saveKey() {
        this.key = getBuf();
        empty();
    }

    /**
     * one of six construct chars is ","
     * indicate that a item has been record.
     */
    private  void nextItem() {
        String buf = getBuf();
        if(buf.length() == 0){
            // previous item is JsonObject or JsonArray
            return;
        }
        Object value = this.valueResolver.resolve(buf);
        addToContainer(value);
        empty();
    }

    /**
     * add a object for Json Value Type to current container.
     *
     * Because current container could be JsonObject or JsonArray,
     * so if it is JsonObject, we need to regard as a field of JsonObject
     * and record the key has saved.
     * But if it is JsonArray, we only need to regard as a element of JsonObject.
     *
     * @param obj
     *           a object for Json Value Type
     */
    private void addToContainer(Object obj){
        Json container = getJson();
        if(container instanceof JsonObject) {
            // put the attribute
            String key = this.key;
            ((JsonObject) container).put(key, obj);
            this.key = null;
        }else if( container instanceof JsonArray) {
            // add the element
            ((JsonArray)container).put(obj);
        }
    }
}
