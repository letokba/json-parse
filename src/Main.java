import json.JsonArray;
import json.JsonObject;
import resolve.JsonResolver;
import resolve.JsonSerialize;
import type.JsonValueResolver;
import type.ValueResolver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Wait
 */
public class Main {


    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonResolver();
        String jsonFile = "src/house.json";
        String jsonFile2 = "src/context.json";
        long start = System.nanoTime();
        JsonArray array = resolver.resolveArray(new FileReader(jsonFile));
        long end = System.nanoTime();
        System.out.println(array.toJsonString());
        JsonObject object = resolver.resolveObject(new FileReader(jsonFile2));
        System.out.println(object);
        System.out.println("json parse time is " + (double)(end - start) / 1000000 + " ms");
    }
}
