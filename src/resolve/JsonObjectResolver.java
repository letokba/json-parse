package resolve;

import json.JsonNode;
import json.JsonObject;
import type.JsonValueHandle;
import type.ValueHandler;


/**
 * @author Wait
 */
public class JsonObjectResolver extends AbstractJsonResolver {

    public JsonObjectResolver() {
        this(new JsonObject(), new JsonValueHandle());
    }

    public JsonObjectResolver(JsonNode container, ValueHandler valueHandler) {
        super(container, valueHandler);
    }


    @Override
    public JsonNode getJsonObject() {
        return getContainer();
    }
}
