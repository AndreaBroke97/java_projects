package calcio;
import java.time.LocalDate;

public final class Player extends Person {
    public static int MAX_SHIRT_NUMBER;
    private int shirtNumber;
    private Roles role;
    private Nationalities nationality;
    private boolean captain;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private Gender gender;

    public Player() {
        super("Unknown", "Unknown", 0);
        this.shirtNumber = 1;
        this.role = Roles.UNKNOWN;
        this.captain = false;
        this.nationality = Nationalities.UNKNOWN;
        this.birthDay = 1;
        this.birthMonth = 1;
        this.birthYear = 1900;
        this.gender = Gender.M;
    }

    public Player(String name, String surname, int shirtNumber, Roles role, boolean captain, String nationality) {
        super(name, surname, 0);
        this.shirtNumber = shirtNumber;
        this.role = role;
        this.captain = captain;
        this.gender = Gender.M;
        if (nationality == null || nationality.isBlank()) {
            this.nationality = Nationalities.UNKNOWN;
        } else {
            try {
                this.nationality = Nationalities.valueOf(nationality.toUpperCase());
            } catch (IllegalArgumentException e) {
                this.nationality = Nationalities.UNKNOWN;
            }
        }
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\nSurname: " + surname
                + "\nRole: " + role + "\nShirt number: " + shirtNumber
                + "\nCaptain: " + captain + "\nNationality: " + nationality.getFlag()
                + "\nBirth Date: " + birthDay + "/" + birthMonth + "/" + birthYear
                + "\nGender: " + gender;
    }

    public String getAbbreviation() {
        return "" + name.charAt(0) + surname.charAt(0) + shirtNumber;
    }

    public String getFullInfo() {
        String flag = nationality != null ? nationality.getFlag() : "❓";
        return "\n - " + flag + " " + shirtNumber + " " + surname
                + " " + name.substring(0, 1) + " " + (captain ? "©" : "");
    }

    // --- Shirt number ---

    public void setNumber(int number) {
        if (number < 1 || number > 99)
            throw new IllegalArgumentException("Shirt number must be between 1 and 99.");
        this.shirtNumber = number;
    }

    public int getNumber() { return shirtNumber; }

    // --- Role ---

    public void setRole(Roles role) { this.role = role; }
    public Roles getRole() { return role; }

    // --- Captain ---

    public void setCap(boolean captain) { this.captain = captain; }
    public boolean getCap() { return captain; }

    // --- Nationality ---

    public void setNationality(Nationalities nationality) { this.nationality = nationality; }
    public Nationalities getNationality() { return nationality; }

    // --- Birth date ---

    public void setBirthDate(int day, int month, int year) {
        if (year < 1900 || year > 2025) { System.out.println("Error: year not valid"); return; }
        if (month < 1 || month > 12)    { System.out.println("Error: month not valid"); return; }
        int maxDay = switch (month) {
            case 2 -> 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
        if (day < 1 || day > maxDay) { System.out.println("Error: day not valid for month " + month); return; }
        this.birthDay = day;
        this.birthMonth = month;
        this.birthYear = year;
    }

    public String getBirthDate() { return birthDay + "/" + birthMonth + "/" + birthYear; }
    public int getBirthDay()     { return birthDay; }
    public int getBirthMonth()   { return birthMonth; }
    public int getBirthYear()    { return birthYear; }

    // --- Gender ---

    public void setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Errore: genere non valido. Usa M o F.");
        }
    }

    public String getGender() { return gender.name(); }

    // --- Fiscal code ---

    public String getFiscalCode() {
        String surnamePart = surname.substring(0, 3).toUpperCase();
        String namePart    = name.substring(0, 3).toUpperCase();
        String yearPart    = String.valueOf(birthYear).substring(2);
        String[] monthLetters = {"A","B","C","D","E","H","L","M","P","R","S","T"};
        String monthPart   = monthLetters[birthMonth - 1];
        int day = (gender == Gender.F) ? birthDay + 40 : birthDay;
        return surnamePart + namePart + yearPart + monthPart + day;
    }

    // --- Age ---

    @Override
    public int getAge() {
        return LocalDate.now().getYear() - birthYear;
    }
}
