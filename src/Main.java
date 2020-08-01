import com.alibaba.fastjson.JSONArray;
import json.JsonNode;
import resolve.JsonFileStream;
import resolve.JsonObjectResolver;
import resolve.JsonResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.System.in;

/**
 * @author Wait
 */
public class Main {

    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonObjectResolver();
        File file = new File("D:\\Wait\\Documents\\workspace\\Java2020\\projects\\Json Parse\\src\\house.json");
        resolver.resolve(file);
        JsonNode jsonObject = resolver.getJsonObject();
        System.out.println(jsonObject);
        JSONArray
    }
}
