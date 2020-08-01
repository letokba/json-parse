package json;

import java.util.Map;

/**
 * @author Wait
 */
public abstract class AbstractJsonNode implements JsonNode {
    private String nodeName;
    private Map<String, JsonNode> children;




    public void setChildren(Map<String, JsonNode> children) {
        this.children = children;
    }



    @Override
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public void appendChild(JsonNode jsonNode) {
        this.children.put(jsonNode.getNodeName(), jsonNode);
    }



    @Override
    public JsonNode getChild(String name) {
        return children.get(name);
    }

    @Override
    public boolean hasChild() {
        return ! children.isEmpty();
    }

    public <T> T get(Object child, Class<T> cl) {
        if(cl.isInstance(child)){
            return cl.cast(child);
        }else if(child instanceof JsonField){
            JsonField field = (JsonField)child;
            Object value = field.getValue();
            if(cl.isInstance(value)){
                return cl.cast(value);
            }
        }
        return null;
    }
}
