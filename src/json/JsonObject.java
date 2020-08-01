package json;

import java.util.HashMap;

/**
 * @author Wait
 */
public class JsonObject extends AbstractJsonNode {

    public JsonObject() {
        setChildren(new HashMap<>(5));
        setNodeName(String.valueOf(hashCode()));
    }

    public void put(String name, Object value) {
        JsonField field = new JsonField(name, value);
        super.appendChild(field);
    }

    public void put(String name, JsonNode node) {
        node.setNodeName(name);
        super.appendChild(node);
    }

    public void put(JsonField field){
        super.appendChild(field);
    }

    public <T> T get(String name, Class<T> cl) {
        JsonNode child = getChild(name);
        return super.get(child, cl);
    }

    public Object get(String name) {
        JsonNode child = getChild(name);
        if(child instanceof JsonField){
            return ((JsonField) child).getValue();
        }else {
            return child;
        }
    }

    public void remove(String name) {

    }


}
