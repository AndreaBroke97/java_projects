package calcio;
public class Team {
    private String name;
    private Player[] players;

    public Team() {
        this.name = "Unknown";
        this.players = new Player[0];
    }

    public Team(String name, Player[] players) {
        this.name = name;
        this.players = players;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Player[] getPlayers() { return players; }
    public void setPlayers(Player[] players) { this.players = players; }
}
