package resolve;


import json.JsonArray;
import json.JsonNode;
import type.JsonValueResolver;
import type.ValueResolver;

import javax.rmi.CORBA.ValueHandler;

/**
 * @author Wait
 */
public class JsonArrayResolver extends AbstractJsonResolver {

    public JsonArrayResolver() {
        this(new JsonArray(), new JsonValueResolver());
    }

    public JsonArrayResolver(JsonNode container, ValueResolver valueResolver) {
        super(container, valueResolver);
    }


    @Override
    public JsonNode getJsonObject() {
        return getContainer();
    }
}
