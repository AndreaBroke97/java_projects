package calcio;
public class Person {
    protected String name;
    protected String surname;
    private int age;

    public Person() {
        this.name = "Unknown";
        this.surname = "Unknown";
        this.age = 0;
    }

    public Person(String name, String surname, int age) {
        this.name = capitalize(name);
        this.surname = capitalize(surname);
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = capitalize(name); }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = capitalize(surname); }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    private String capitalize(String base) {
        if (base == null || base.isEmpty()) return base;
        return base.substring(0, 1).toUpperCase() + base.substring(1).toLowerCase().trim();
    }
}
