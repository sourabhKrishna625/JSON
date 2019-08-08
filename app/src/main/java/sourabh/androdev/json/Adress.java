package sourabh.androdev.json;

public class Adress {
    String line1;
    String city;
    String state;
    String zip;

    public Adress() {
    }

    @Override
    public String toString() {
        return "Adress{" +
                "line1='" + line1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
