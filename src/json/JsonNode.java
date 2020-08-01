package json;

/**
 * @author Wait
 */
public interface JsonNode {

    public void setNodeName(String name);

    public String getNodeName();

    public void appendChild(JsonNode jsonNode);

    public JsonNode getChild(String name);

    public boolean hasChild();

}
