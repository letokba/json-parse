package json;


import java.util.List;

/**
 * @author Wait
 */
public class Person {
    private String name;
    private int age;
    private List<Person> children;

    public Person() { }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
