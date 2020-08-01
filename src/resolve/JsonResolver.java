package resolve;

import json.JsonNode;
import type.ValueHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Wait
 */
public interface JsonResolver {

    public void resolve(File file) throws FileNotFoundException;

    public void resolve(JsonStream stream) throws IOException;

    public JsonNode getJsonObject();

    public void setValueHandler(ValueHandler handler);
}
