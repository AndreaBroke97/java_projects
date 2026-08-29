package calcio;
public class Referee extends Person {
    private int badgeNumber;
    private int yearsOfExperience;
    private License license;

    public Referee() {
        super("Unknown", "Unknown", 0);
        this.badgeNumber = 0;
        this.yearsOfExperience = 0;
        this.license = License.REGIONAL;
    }

    public Referee(String name, String surname, int age, int badgeNumber, int yearsOfExperience, License license) {
        super(name, surname, age);
        this.badgeNumber = badgeNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.license = license;
    }

    public int getBadgeNumber() { return badgeNumber; }
    public void setBadgeNumber(int badgeNumber) { this.badgeNumber = badgeNumber; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public License getLicense() { return license; }
    public void setLicense(License license) { this.license = license; }

    @Override
    public String toString() {
        return name + " " + surname + " | Badge: " + badgeNumber
                + " | Experience: " + yearsOfExperience + " yrs | License: " + license;
    }
}
