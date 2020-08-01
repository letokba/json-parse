import json.JsonNode;
import resolve.JsonResolver;

import java.io.File;
import java.io.IOException;

/**
 * @author Wait
 */
public class Main {

    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonResolver();
        File file = new File("src/house.json");
        resolver.resolve(file);
        JsonNode jsonObject = resolver.getJsonNode();
        JsonNode property = jsonObject.getChild("property");

    }
}
