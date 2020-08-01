package resolve;

import json.JsonNode;
import json.JsonObject;
import type.JsonValueResolver;
import type.ValueResolver;

import javax.rmi.CORBA.ValueHandler;


/**
 * @author Wait
 */
public class JsonObjectResolver extends AbstractJsonResolver {

    public JsonObjectResolver() {
        this(new JsonObject(), new JsonValueResolver());
    }

    public JsonObjectResolver(JsonNode container, ValueResolver valueResolver) {
        super(container, valueResolver);
    }


    @Override
    public JsonNode getJsonObject() {
        return getContainer();
    }
}
