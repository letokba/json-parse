package json;

/**
 * @author Wait
 */
public class JsonField extends AbstractJsonNode {
    private Object value;

    public JsonField(String name, Object value) {
        this.value = value;
        super.setNodeName(name);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }


}
