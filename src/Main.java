import json.Json;
import json.JsonArray;
import json.JsonObject;
import test.Address;
import test.Person;
import resolve.JsonResolver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wait
 */
public class Main {




    public static void main(String[] args) throws IOException {
        String file = "src/context.json";
        List<Person> list = new LinkedList<>();
        list.add(new Person("jack", 20));
        list.add(new Person("mike", 25));
        list.add(new Person("rose", 22));
        list.add(new Person("wait", 21));
        Person person = new Person("jack", 12);
        Address address = new Address("江西省", "沿海城市");
        person.setAddress(address);
        person.setChildren(list);
        JsonArray array = new JsonArray(list);
        List<Person> people = array.toList(Person.class);
        System.out.println(people);
    }
}
