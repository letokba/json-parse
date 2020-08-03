import json.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wait
 */
public class Main {


    public static void main(String[] args) throws IOException {
        JsonObject jsonObject = new JsonObject();
        List<Person> children = new ArrayList<>();
        children.add( new Person("mike", 20));
        children.add(new Person("james", 10));
        Person ben = new Person("ben", 40);
        Person kitty = new Person("kitty", 39);
        ben.setChildren(children);
        ben.setSpouse(kitty);
        jsonObject.put("family", ben);
        System.out.println(jsonObject);
    }
}
