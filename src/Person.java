import java.util.List;

/**
 * @author Wait
 */
public class Person {
    private String name;
    private int age;
    private Person spouse;
    private List<Person> children;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }
}
