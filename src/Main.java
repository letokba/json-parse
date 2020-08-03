import json.JsonArray;
import json.JsonObject;
import resolve.JsonResolver;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Wait
 */
public class Main {


    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonResolver();
        String file = "src/context.json";
        JsonObject object = resolver.resolveObject(new FileReader(file));
        System.out.println(object);

    }
}
