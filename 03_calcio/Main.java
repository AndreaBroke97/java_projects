package calcio;

public class Main {
    public static void main(String[] args) throws Exception {
        Player p1 = new Player("Roberto", "Baggio", 10, Roles.ATT, true, "Italy");
        p1.setBirthDate(18, 2, 1967);
        p1.setGender("M");

        Player p2 = new Player("Alessandro", "Nesta", 2, Roles.DEF, false, "Italy");
        p2.setBirthDate(19, 3, 1976);
        p2.setGender("M");

        Player p3 = new Player("Niccolò", "Barella", 15, Roles.CEN, false, "Italy");
        p3.setBirthDate(7, 2, 1997);
        p3.setGender("M");

        Player[] t1Players = { p1, p2, p3 };
        Team t1 = new Team("Italy AllTimeLegends", t1Players);

        Player p4 = new Player("Kylian", "Mbappè", 10, Roles.ATT, true, "France");
        p4.setBirthDate(20, 12, 1998);
        p4.setGender("M");

        Player p5 = new Player("Olivier", "Giroud", 9, Roles.ATT, false, "France");
        p5.setBirthDate(30, 9, 1986);
        p5.setGender("M");

        Player p6 = new Player("Zinedine", "Zidane", 5, Roles.CEN, false, "France");
        p6.setBirthDate(23, 6, 1972);
        p6.setGender("M");

        Player[] t2Players = { p4, p5, p6 };
        Team t2 = new Team("France AllTimeLegends", t2Players);

        System.out.println(p1.getSurname() + " " + p1.getName() + " → " + p1.getFiscalCode());
        System.out.println(p2.getSurname() + " " + p2.getName() + " → " + p2.getFiscalCode());
        System.out.println(p3.getSurname() + " " + p3.getName() + " → " + p3.getFiscalCode());
        System.out.println(p4.getSurname() + " " + p4.getName() + " → " + p4.getFiscalCode());
        System.out.println(p5.getSurname() + " " + p5.getName() + " → " + p5.getFiscalCode());
        System.out.println(p6.getSurname() + " " + p6.getName() + " → " + p6.getFiscalCode());

        Player.MAX_SHIRT_NUMBER = 10;

        Referee r1 = new Referee("Pierluigi", "Collina", 55, 1234, 20, License.INTERNATIONAL);
        System.out.println(r1);

        Referee r2 = new Referee();
        System.out.println(r2);

        Referee[] referees = { r1, r2 };

        @SuppressWarnings("unused")
        SoccerGUI gui = new SoccerGUI(referees, t1, t2);
    }
}
