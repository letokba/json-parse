import json.Json;
import json.JsonArray;
import json.JsonObject;
import json.Person;
import resolve.JsonResolver;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wait
 */
public class Main {


    public static void main(String[] args) throws IOException {
        JsonResolver resolver = new JsonResolver();
        String file = "src/context.json";
        JsonObject jsonObject = new JsonObject();
        System.out.println(jsonObject);
        List<Person> list = new LinkedList<>();
        list.add(new Person("jack", 20));
        list.add(new Person("mike", 25));
        list.add(new Person("rose", 22));
        list.add(new Person("wait", 21));
        Person person = new Person("jack", 12);
        person.setChildren(list);
        Json json = Json.toJson(person);
        System.out.println(json);

        System.out.println(list.getClass());
    }
}
