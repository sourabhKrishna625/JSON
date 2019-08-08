package sourabh.androdev.json;

public class Person {
    String name;
    Long id;
    int age;
    Adress adress;

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", adress=" + adress +
                '}';
    }
}
