package calcio;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class SoccerGUI extends JFrame {

    private static final Color FIELD_GREEN  = new Color(26, 92, 42);
    private static final Color HEADER_BG    = new Color(10, 50, 20);
    private static final Color CAPTAIN_GOLD = new Color(255, 215, 0);
    private static final Color REFEREE_BLUE = new Color(30, 60, 130); // ← NUOVO

    private final ArrayList<Team> teams;
    private final Referee[] referees; // ← NUOVO
    private final JTabbedPane tabbedPane;

    @SuppressWarnings("ManualArrayToCollectionCopy")
    public SoccerGUI(Referee[] referees, Team... initialTeams) { // ← MODIFICATO
        this.referees = referees; // ← NUOVO
        teams = new ArrayList<>();
        for (Team t : initialTeams) teams.add(t);

        setTitle("Soccer Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FIELD_GREEN);

        add(buildToolbar(), BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(FIELD_GREEN);
        for (Team t : teams) tabbedPane.addTab(t.getName(), buildTeamPanel(t));

        // ← NUOVO: tab referees
        tabbedPane.addTab("Referees", buildRefereesPanel());

        add(tabbedPane, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(800, 520));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ← NUOVO: pannello dei referee
    private JPanel buildRefereesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FIELD_GREEN);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(12, 30, 10, 30));
        JLabel title = new JLabel("Referees", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        JLabel count = new JLabel(referees.length + " referees", SwingConstants.CENTER);
        count.setFont(new Font("SansSerif", Font.PLAIN, 13));
        count.setForeground(new Color(160, 210, 160));
        header.add(title, BorderLayout.CENTER);
        header.add(count, BorderLayout.SOUTH);
        panel.add(header, BorderLayout.NORTH);

        // Griglia card
        int cols = 3;
        int rows = Math.max(1, (int) Math.ceil((double) referees.length / cols));
        JPanel grid = new JPanel(new GridLayout(rows, cols, 15, 15));
        grid.setBackground(FIELD_GREEN);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (Referee r : referees) grid.add(buildRefereeCard(r));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.getViewport().setBackground(FIELD_GREEN);
        scroll.setBorder(null);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ← NUOVO: singola card referee
    private JPanel buildRefereeCard(Referee referee) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(REFEREE_BLUE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(190, 200));

        card.add(centeredLabel("★ REFEREE", Font.BOLD, 11, CAPTAIN_GOLD));
        card.add(Box.createVerticalStrut(10));
        card.add(centeredLabel(referee.getName() + " " + referee.getSurname(), Font.BOLD, 14, Color.WHITE));
        card.add(Box.createVerticalStrut(8));
        card.add(centeredLabel("Age: " + referee.getAge(), Font.PLAIN, 12, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(4));
        card.add(centeredLabel("Badge: #" + referee.getBadgeNumber(), Font.BOLD, 12, CAPTAIN_GOLD));
        card.add(Box.createVerticalStrut(4));
        card.add(centeredLabel("Experience: " + referee.getYearsOfExperience() + " yrs", Font.PLAIN, 11, new Color(210, 210, 210)));
        return card;
    }

    // ---- Toolbar ----
    private JPanel buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        toolbar.setBackground(HEADER_BG);
        JButton addTeamBtn = new JButton("+ Add Team");
        addTeamBtn.addActionListener(e -> showAddTeamDialog());
        JButton addPlayerBtn = new JButton("+ Add Player");
        addPlayerBtn.addActionListener(e -> showAddPlayerDialog());
        toolbar.add(addTeamBtn);
        toolbar.add(addPlayerBtn);
        return toolbar;
    }

    private JPanel buildTeamPanel(Team team) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FIELD_GREEN);
        panel.add(buildHeader(team), BorderLayout.NORTH);
        panel.add(buildPlayersArea(team), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildHeader(Team team) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(12, 30, 10, 30));
        JLabel teamLabel = new JLabel("\u26BD " + team.getName().trim(), SwingConstants.CENTER);
        teamLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        teamLabel.setForeground(Color.WHITE);
        JLabel countLabel = new JLabel(team.getPlayers().length + " players", SwingConstants.CENTER);
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        countLabel.setForeground(new Color(160, 210, 160));
        header.add(teamLabel, BorderLayout.CENTER);
        header.add(countLabel, BorderLayout.SOUTH);
        return header;
    }

    private JScrollPane buildPlayersArea(Team team) {
        int cols = 3;
        int rows = Math.max(1, (int) Math.ceil((double) team.getPlayers().length / cols));
        JPanel grid = new JPanel(new GridLayout(rows, cols, 15, 15));
        grid.setBackground(FIELD_GREEN);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (Player p : team.getPlayers()) grid.add(buildPlayerCard(p, team));
        JScrollPane scroll = new JScrollPane(grid);
        scroll.getViewport().setBackground(FIELD_GREEN);
        scroll.setBorder(null);
        return scroll;
    }

    private JPanel buildPlayerCard(Player player, Team team) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(roleColor(player.getRole()));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(190, 250));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        topRow.setOpaque(false);
        String flag = (player.getNationality() != null) ? player.getNationality().getFlag() : "❓";
        JLabel flagLabel = new JLabel(emojiIcon(flag, 24));
        JLabel numLabel = new JLabel("#" + player.getNumber());
        numLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        numLabel.setForeground(Color.WHITE);
        topRow.add(flagLabel);
        topRow.add(numLabel);
        card.add(topRow);
        card.add(Box.createVerticalStrut(8));

        card.add(centeredLabel(player.getName() + " " + player.getSurname(), Font.BOLD, 14, Color.WHITE));
        card.add(Box.createVerticalStrut(6));

        JLabel roleLabel = centeredLabel(player.getRole().toString(), Font.BOLD, 11, Color.WHITE);
        roleLabel.setOpaque(true);
        roleLabel.setBackground(new Color(0, 0, 0, 70));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        card.add(roleLabel);

        if (player.getCap()) {
            card.add(Box.createVerticalStrut(6));
            card.add(centeredLabel("\u00A9 Captain", Font.BOLD, 11, CAPTAIN_GOLD));
        }

        card.add(Box.createVerticalStrut(6));
        card.add(centeredLabel(player.getAbbreviation(), Font.ITALIC, 12, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(4));
        card.add(centeredLabel(player.getBirthDate(), Font.PLAIN, 10, new Color(210, 210, 210)));
        card.add(Box.createVerticalStrut(4));
        card.add(centeredLabel(player.getFiscalCode(), Font.PLAIN, 10, new Color(210, 210, 210)));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showEditPlayerDialog(player, team);
            }
        });
        return card;
    }

    private void showAddTeamDialog() {
        JTextField nameField = new JTextField(20);
        JPanel form = new JPanel(new GridLayout(1, 2, 10, 0));
        form.add(new JLabel("Team name:"));
        form.add(nameField);
        int result = JOptionPane.showConfirmDialog(this, form, "Add Team", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;
        String name = nameField.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Team name cannot be empty."); return; }
        Team newTeam = new Team(name, new Player[0]);
        teams.add(newTeam);
        tabbedPane.addTab(newTeam.getName(), buildTeamPanel(newTeam));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    private void showAddPlayerDialog() {
        if (teams.isEmpty()) { JOptionPane.showMessageDialog(this, "Add a team first."); return; }
        int tabIndex = tabbedPane.getSelectedIndex();
        Team team = teams.get(tabIndex);
        PlayerFormResult result = showPlayerForm(null);
        if (result == null) return;
        Player newPlayer = new Player(result.name, result.surname, result.number, result.role, result.captain, result.nationality.name());
        newPlayer.setGender(result.gender);
        newPlayer.setBirthDate(result.birthDay, result.birthMonth, result.birthYear);
        Player[] old = team.getPlayers();
        Player[] updated = new Player[old.length + 1];
        for (int i = 0; i < old.length; i++) updated[i] = old[i];
        updated[old.length] = newPlayer;
        team.setPlayers(updated);
        refreshTeamTab(tabIndex, team);
    }

    private void showEditPlayerDialog(Player player, Team team) {
        PlayerFormResult result = showPlayerForm(player);
        if (result == null) return;
        player.setName(result.name);
        player.setSurname(result.surname);
        player.setNumber(result.number);
        player.setRole(result.role);
        player.setCap(result.captain);
        player.setNationality(result.nationality);
        player.setGender(result.gender);
        player.setBirthDate(result.birthDay, result.birthMonth, result.birthYear);
        refreshTeamTab(teams.indexOf(team), team);
    }

    private PlayerFormResult showPlayerForm(Player existing) {
        JTextField nameField    = new JTextField(existing != null ? existing.getName() : "", 15);
        JTextField surnameField = new JTextField(existing != null ? existing.getSurname() : "", 15);
        JSpinner   numSpinner   = new JSpinner(new SpinnerNumberModel(existing != null ? existing.getNumber() : 1, 1, 99, 1));
        JSpinner   daySpinner   = new JSpinner(new SpinnerNumberModel(existing != null ? existing.getBirthDay() : 1, 1, 31, 1));
        JSpinner   monthSpinner = new JSpinner(new SpinnerNumberModel(existing != null ? existing.getBirthMonth() : 1, 1, 12, 1));
        JSpinner   yearSpinner  = new JSpinner(new SpinnerNumberModel(existing != null ? existing.getBirthYear() : 2000, 1900, 2025, 1));
        JComboBox<Roles>         roleBox   = new JComboBox<>(Roles.values());
        JComboBox<Nationalities> natBox    = new JComboBox<>(Nationalities.values());
        JComboBox<String>        genderBox = new JComboBox<>(new String[]{"M", "F"});
        if (existing != null) genderBox.setSelectedItem(existing.getGender());
        natBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Nationalities n) { setIcon(emojiIcon(n.getFlag(), 16)); setText(n.name().replace("_", " ")); }
                return this;
            }
        });
        JCheckBox captainBox = new JCheckBox("Captain", existing != null && existing.getCap());
        if (existing != null) { roleBox.setSelectedItem(existing.getRole()); natBox.setSelectedItem(existing.getNationality()); }
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Name:"));        form.add(nameField);
        form.add(new JLabel("Surname:"));     form.add(surnameField);
        form.add(new JLabel("Shirt #:"));     form.add(numSpinner);
        form.add(new JLabel("Birth Day:"));   form.add(daySpinner);
        form.add(new JLabel("Birth Month:")); form.add(monthSpinner);
        form.add(new JLabel("Birth Year:"));  form.add(yearSpinner);
        form.add(new JLabel("Role:"));        form.add(roleBox);
        form.add(new JLabel("Nationality:")); form.add(natBox);
        form.add(new JLabel("Gender:"));      form.add(genderBox);
        form.add(new JLabel(""));             form.add(captainBox);
        String title = existing != null ? "Edit Player" : "Add Player";
        int res = JOptionPane.showConfirmDialog(this, form, title, JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return null;
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        if (name.isEmpty() || surname.isEmpty()) { JOptionPane.showMessageDialog(this, "Name and surname cannot be empty."); return null; }
        PlayerFormResult out = new PlayerFormResult();
        out.name = name; out.surname = surname;
        out.number = (int) numSpinner.getValue();
        out.role = (Roles) roleBox.getSelectedItem();
        out.nationality = (Nationalities) natBox.getSelectedItem();
        out.captain = captainBox.isSelected();
        out.gender = (String) genderBox.getSelectedItem();
        out.birthDay = (int) daySpinner.getValue();
        out.birthMonth = (int) monthSpinner.getValue();
        out.birthYear = (int) yearSpinner.getValue();
        return out;
    }

    private void refreshTeamTab(int tabIndex, Team team) {
        tabbedPane.setComponentAt(tabIndex, buildTeamPanel(team));
        tabbedPane.setTitleAt(tabIndex, team.getName());
        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    private static class PlayerFormResult {
        String name, surname; int number; Roles role; boolean captain;
        Nationalities nationality; String gender; int birthDay, birthMonth, birthYear;
    }

    private JLabel centeredLabel(String text, int style, int size, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private ImageIcon emojiIcon(String emoji, int size) {
        BufferedImage img = new BufferedImage(size * 2, size + 6, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Apple Color Emoji", Font.PLAIN, size));
        g.drawString(emoji, 0, size);
        g.dispose();
        return new ImageIcon(img);
    }

    private Color roleColor(Roles role) {
        if (role == Roles.ATT) return new Color(200, 50, 50);
        if (role == Roles.DEF) return new Color(50, 100, 200);
        if (role == Roles.CEN) return new Color(160, 120, 20);
        if (role == Roles.POR) return new Color(200, 120, 30);
        return new Color(80, 80, 80);
    }
}