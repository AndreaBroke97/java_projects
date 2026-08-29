package calcio;

public class Main0 {
    public static void main(String[] args) {
        Player p = new Player("Mario", "Rossi", 10, Roles.ATT, true, "Italy");
        System.out.println(p.getName());

        p.setName("Gianni");
        System.out.println(p.getName());

        Date d = new Date(18, 2, 1967);
        System.out.println(d);
    }
}
