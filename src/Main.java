import json.JsonObject;
import resolve.JsonResolver;

import java.io.File;
import java.io.IOException;

/**
 * @author Wait
 */
public class Main {


    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonResolver();
        File file = new File("src/context.json");
        resolver.resolve(file);
        JsonObject jsonObject = (JsonObject) resolver.getJson();
    }
}
