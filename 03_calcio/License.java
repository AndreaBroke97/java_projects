package calcio;
public enum License {
    REGIONAL("Regionale"),
    NATIONAL("Nazionale"),
    INTERNATIONAL("Internazionale");

    private final String label;

    License(String label) {
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
