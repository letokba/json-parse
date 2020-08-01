package resolve;

import json.JsonNode;

import javax.rmi.CORBA.ValueHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Wait
 */
public interface JsonResolver {

    public void resolve(File file) throws IOException;

    public void resolve(JsonStream stream) throws IOException;

    public JsonNode getJsonObject();

}
