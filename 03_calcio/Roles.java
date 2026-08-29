package calcio;
public enum Roles {
    ATT("Attaccante"),
    DEF("Difensore"),
    CEN("Centrocampista"),
    POR("Portiere"),
    UNKNOWN("Sconosciuto");

    private final String label;

    Roles(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
