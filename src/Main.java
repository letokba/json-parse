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
        JsonObject object = resolver.resolveObject("{\"property\":[{\"code\":[\"red\",\"green\"],\"name\":\"quality\",\"type\":\"node\",\"value\":0.2}],\"id\":\"fruit\",\"class\":\"bean.Fruit\"}");
        System.out.println(object);

    }
}
