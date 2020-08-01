package resolve;


import json.JsonArray;
import json.JsonNode;
import type.JsonValueHandle;
import type.ValueHandler;

/**
 * @author Wait
 */
public class JsonArrayResolver extends AbstractJsonResolver {

    public JsonArrayResolver() {
        this(new JsonArray(), new JsonValueHandle());
    }

    public JsonArrayResolver(JsonNode container, ValueHandler valueHandler) {
        super(container, valueHandler);
    }


    @Override
    public JsonNode getJsonObject() {
        return getContainer();
    }
}
