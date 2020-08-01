package json;


import java.util.HashMap;

/**
 * @author Wait
 */
public class JsonArray extends AbstractJsonNode  {
    private int count = 0;

    public JsonArray() {
        setChildren(new HashMap<>(5));
        setNodeName(String.valueOf(hashCode()));
    }

    public Object get(int index) {
        return getChild(String.valueOf(index));
    }

    public <T> T get(int index, Class<T> cl) {
        JsonNode child = getChild(String.valueOf(index));
        return super.get(child, cl);
    }

    public void add(JsonNode node) {
        appendChild(node);
    }

    @Override
    public void appendChild(JsonNode node) {
        setIndex(node);
        super.appendChild(node);
    }

    public void add(Object item) {
        JsonField jsonField = new JsonField("element", item);
        this.appendChild(jsonField);
    }

    private void setIndex(JsonNode node) {
        node.setNodeName(String.valueOf(this.count));
        this.count++;
    }

}
