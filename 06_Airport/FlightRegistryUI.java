import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlightRegistryUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color ACCENT      = new Color(41, 128, 185);
    private static final Color ACCENT_DARK = new Color(28, 99, 148);
    private static final Color BG          = new Color(240, 243, 248);
    private static final Color PANEL_BG    = Color.WHITE;
    private static final Color TEXT_MUTED  = new Color(130, 130, 140);

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter D_FMT  = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private transient final NationalFlightRegistry registry = new NationalFlightRegistry();
    private JTextArea  resultsArea;
    private JLabel     resultsTitle;

    // Add-flight fields
    private JTextField tfDepIata, tfArrIata, tfDepTime, tfArrTime, tfModel, tfSeatCap;
    private JComboBox<String> cbPlaneType;
    private JLabel lblSeatCap;

    // Filter fields
    private JComboBox<String> cbFilterType;
    private JTextField tfFDep, tfFArr, tfFDepDate, tfFArrDate;
    private JLabel lblFArr, lblFDepDate, lblFArrDate;

    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            applyNimbus();
            new FlightRegistryUI().setVisible(true);
        });
    }

    @SuppressWarnings("this-escape")
    public FlightRegistryUI() {
        super("National Flight Registry");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 700);
        setMinimumSize(new Dimension(760, 520));
        setLocationRelativeTo(null);
        buildUI();
        loadSampleData();
        showAll();
    }

    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(buildHeader(), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(),
                buildResultsPanel());
        split.setDividerLocation(390);
        split.setDividerSize(6);
        split.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(split, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(ACCENT);
        p.setBorder(BorderFactory.createEmptyBorder(16, 26, 16, 26));

        JLabel title = new JLabel("✈  National Flight Registry");
        title.setFont(new Font("SansSerif", Font.BOLD, 21));
        title.setForeground(Color.WHITE);
        p.add(title, BorderLayout.WEST);

        JLabel sub = new JLabel("Domestic flights tracker");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(new Color(200, 220, 240));
        p.add(sub, BorderLayout.EAST);
        return p;
    }

    private JTabbedPane buildLeftPanel() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabs.setBackground(PANEL_BG);
        tabs.addTab("  Add Flight  ", buildAddTab());
        tabs.addTab("  Filter  ",     buildFilterTab());
        return tabs;
    }

    // --- Add Flight tab ---

    private JPanel buildAddTab() {
        JPanel p = padded(new JPanel(new GridBagLayout()), 20, 24);
        p.setBackground(PANEL_BG);
        GridBagConstraints c = gbc();

        tfDepIata   = field(4);
        tfArrIata   = field(4);
        tfDepTime   = field("2026-05-20 10:00", 16);
        tfArrTime   = field("2026-05-20 11:30", 16);
        cbPlaneType = new JComboBox<>(new String[]{"Airline", "Cargo"});
        tfModel     = field("Boeing 737", 16);
        tfSeatCap   = field("250", 6);
        lblSeatCap  = label("Seats:");

        cbPlaneType.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbPlaneType.addActionListener(e ->
            lblSeatCap.setText("Airline".equals(cbPlaneType.getSelectedItem()) ? "Seats:" : "Capacity (kg):"));

        int r = 0;
        addRow(p, c, r++, "Departure IATA:",      tfDepIata);
        addRow(p, c, r++, "Arrival IATA:",         tfArrIata);
        addRow(p, c, r++, "Departure Time:",       tfDepTime);
        addRow(p, c, r++, "Arrival Time:",         tfArrTime);
        addRow(p, c, r++, "Plane Type:",           cbPlaneType);
        addRow(p, c, r++, "Model:",                tfModel);

        c.gridx = 0; c.gridy = r; c.gridwidth = 1; c.weightx = 0;
        p.add(lblSeatCap, c);
        c.gridx = 1; c.weightx = 1;
        p.add(tfSeatCap, c);
        r++;

        c.gridx = 0; c.gridy = r; c.gridwidth = 2; c.weightx = 1;
        c.insets = new Insets(2, 4, 12, 4);
        JLabel hint = hint("Format: yyyy-MM-dd HH:mm");
        p.add(hint, c);
        r++;

        JButton btn = accentButton("+ Add Flight");
        c.gridy = r; c.insets = new Insets(4, 4, 4, 4);
        p.add(btn, c);
        r++;

        filler(p, c, r);
        btn.addActionListener(e -> addFlight());
        return p;
    }

    // --- Filter tab ---

    private JPanel buildFilterTab() {
        JPanel p = padded(new JPanel(new GridBagLayout()), 20, 24);
        p.setBackground(PANEL_BG);
        GridBagConstraints c = gbc();

        cbFilterType = new JComboBox<>(new String[]{
                "By Departure",
                "By Departure + Arrival",
                "By Dep. + Arr. + Departure Date",
                "By All Fields (+ Arrival Date)"
        });
        cbFilterType.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tfFDep     = field(6);
        tfFArr     = field(6);
        tfFDepDate = field("2026-05-20", 12);
        tfFArrDate = field("2026-05-20", 12);

        lblFArr     = label("Arrival IATA:");
        lblFDepDate = label("Departure Date:");
        lblFArrDate = label("Arrival Date:");

        int r = 0;
        addRow(p, c, r++, "Filter Type:", cbFilterType);
        addRow(p, c, r++, "Departure IATA:", tfFDep);

        c.gridx = 0; c.gridy = r; c.gridwidth = 1; c.weightx = 0; c.insets = new Insets(6, 4, 6, 4);
        p.add(lblFArr, c);
        c.gridx = 1; c.weightx = 1;
        p.add(tfFArr, c);
        r++;

        c.gridx = 0; c.gridy = r; c.gridwidth = 1; c.weightx = 0;
        p.add(lblFDepDate, c);
        c.gridx = 1; c.weightx = 1;
        p.add(tfFDepDate, c);
        r++;

        c.gridx = 0; c.gridy = r; c.gridwidth = 1; c.weightx = 0;
        p.add(lblFArrDate, c);
        c.gridx = 1; c.weightx = 1;
        p.add(tfFArrDate, c);
        r++;

        c.gridx = 0; c.gridy = r; c.gridwidth = 2; c.weightx = 1;
        c.insets = new Insets(2, 4, 12, 4);
        p.add(hint("Date format: yyyy-MM-dd"), c);
        r++;

        JPanel btnRow = new JPanel(new GridLayout(1, 2, 8, 0));
        btnRow.setBackground(PANEL_BG);
        JButton btnSearch = accentButton("Search");
        JButton btnAll    = grayButton("Show All");
        btnRow.add(btnSearch);
        btnRow.add(btnAll);

        c.gridy = r; c.insets = new Insets(4, 4, 4, 4);
        p.add(btnRow, c);
        r++;

        filler(p, c, r);

        cbFilterType.addActionListener(e -> syncFilterFields());
        syncFilterFields();
        btnSearch.addActionListener(e -> applyFilter());
        btnAll.addActionListener(e -> showAll());
        return p;
    }

    // --- Results panel ---

    private JPanel buildResultsPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL_BG);

        resultsTitle = new JLabel("All Flights");
        resultsTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.add(resultsTitle, BorderLayout.WEST);
        p.add(header, BorderLayout.NORTH);

        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsArea.setEditable(false);
        resultsArea.setBackground(new Color(248, 250, 253));
        resultsArea.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(resultsArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // -------------------------------------------------------------------------
    // Actions
    // -------------------------------------------------------------------------

    private void addFlight() {
        try {
            String dep = tfDepIata.getText().trim().toUpperCase();
            String arr = tfArrIata.getText().trim().toUpperCase();
            if (dep.isEmpty() || arr.isEmpty())
                throw new IllegalArgumentException("IATA codes cannot be empty.");
            LocalDateTime depTime = LocalDateTime.parse(tfDepTime.getText().trim(), DT_FMT);
            LocalDateTime arrTime = LocalDateTime.parse(tfArrTime.getText().trim(), DT_FMT);
            String model = tfModel.getText().trim();
            if (model.isEmpty()) throw new IllegalArgumentException("Model cannot be empty.");
            int val = Integer.parseInt(tfSeatCap.getText().trim());
            boolean isAirline = "Airline".equals(cbPlaneType.getSelectedItem());
            Airplane plane = isAirline ? new Airline(model, val) : new Cargo(model, val);
            registry.addFlight(new Fly(dep, arr, depTime, arrTime, plane));
            showAll();
            JOptionPane.showMessageDialog(this,
                    "Flight " + dep + " → " + arr + " added successfully.",
                    "Flight Added", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeParseException ex) {
            error("Invalid date/time format.\nUse: yyyy-MM-dd HH:mm");
        } catch (NumberFormatException ex) {
            error("Seats / Capacity must be a whole number.");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void applyFilter() {
        try {
            int idx = cbFilterType.getSelectedIndex();
            String dep = tfFDep.getText().trim().toUpperCase();
            if (dep.isEmpty()) throw new IllegalArgumentException("Departure IATA is required.");

            ArrayList<Fly> result;
            String lbl;

            switch (idx) {
                case 0 -> {
                    result = registry.filterByDeparture(dep);
                    lbl = "Departures from " + dep;
                }
                case 1 -> {
                    String arr = tfFArr.getText().trim().toUpperCase();
                    result = registry.filterByDepartureAndArrival(dep, arr);
                    lbl = dep + " → " + arr;
                }
                case 2 -> {
                    String arr = tfFArr.getText().trim().toUpperCase();
                    LocalDate dd = LocalDate.parse(tfFDepDate.getText().trim(), D_FMT);
                    result = registry.filterByDepartureArrivalAndDepartureDate(dep, arr, dd);
                    lbl = dep + " → " + arr + "  |  dep " + dd;
                }
                case 3 -> {
                    String arr = tfFArr.getText().trim().toUpperCase();
                    LocalDate dd = LocalDate.parse(tfFDepDate.getText().trim(), D_FMT);
                    LocalDate ad = LocalDate.parse(tfFArrDate.getText().trim(), D_FMT);
                    result = registry.filterByAllFields(dep, arr, dd, ad);
                    lbl = dep + " → " + arr + "  |  dep " + dd + "  arr " + ad;
                }
                default -> { return; }
            }
            display(result, lbl);
        } catch (DateTimeParseException ex) {
            error("Invalid date format.\nUse: yyyy-MM-dd");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void showAll() {
        display(registry.getAllFlights(), "All Flights");
    }

    // -------------------------------------------------------------------------
    // Display helpers
    // -------------------------------------------------------------------------

    private void display(ArrayList<Fly> list, String title) {
        int n = list.size();
        resultsTitle.setText(title + "   (" + n + " result" + (n != 1 ? "s" : "") + ")");
        if (list.isEmpty()) {
            resultsArea.setText("  No flights found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Fly f = list.get(i);
            sb.append(String.format("  #%d  %s  →  %s%n",
                    i + 1, f.getDepartureIata(), f.getArrivalIata()));
            sb.append(String.format("      Departure : %s%n", f.getDepartureTime()));
            sb.append(String.format("      Arrival   : %s%n",
                    f.getArrivalTime() != null ? f.getArrivalTime() : "N/A"));
            if (f.getAirplane() != null)
                sb.append(String.format("      Aircraft  : %s%n", f.getAirplane()));
            if (i < n - 1) sb.append("%n".formatted());
        }
        resultsArea.setText(sb.toString());
        resultsArea.setCaretPosition(0);
    }

    private void syncFilterFields() {
        int idx = cbFilterType.getSelectedIndex();
        enableRow(lblFArr,     tfFArr,     idx >= 1);
        enableRow(lblFDepDate, tfFDepDate, idx >= 2);
        enableRow(lblFArrDate, tfFArrDate, idx >= 3);
    }

    private void enableRow(JLabel lbl, JTextField tf, boolean on) {
        lbl.setEnabled(on);
        tf.setEnabled(on);
        tf.setBackground(on ? Color.WHITE : new Color(235, 235, 240));
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Sample data
    // -------------------------------------------------------------------------

    private void loadSampleData() {
        Airline b747 = new Airline("Boeing 747", 300);
        Airline b737 = new Airline("Boeing 737", 250);
        Cargo   md11 = new Cargo("MD-11", 30000);
        registry.addFlight(new Fly("CTA", "MPX", ldt(2026,5,20,10,10), ldt(2026,5,20,11,30), b747));
        registry.addFlight(new Fly("CTA", "FCO", ldt(2026,6,20,10,10), ldt(2026,6,20,11, 0), b737));
        registry.addFlight(new Fly("CTA", "NAP", ldt(2026,7,20,11,20), ldt(2026,7,20,12,45), md11));
        registry.addFlight(new Fly("FCO", "NAP", ldt(2026,5,20,14, 0), ldt(2026,5,20,15,10), b747));
    }

    private static LocalDateTime ldt(int y, int mo, int d, int h, int m) {
        return LocalDateTime.of(y, mo, d, h, m);
    }

    // -------------------------------------------------------------------------
    // Layout / style utilities
    // -------------------------------------------------------------------------

    private void addRow(JPanel p, GridBagConstraints c, int r, String text, JComponent comp) {
        c.gridx = 0; c.gridy = r; c.gridwidth = 1; c.weightx = 0; c.insets = new Insets(6, 4, 6, 4);
        p.add(label(text), c);
        c.gridx = 1; c.weightx = 1;
        p.add(comp, c);
    }

    private void filler(JPanel p, GridBagConstraints c, int r) {
        c.gridx = 0; c.gridy = r; c.gridwidth = 2; c.weighty = 1;
        p.add(new JLabel(), c);
    }

    private static GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 4, 6, 4);
        return c;
    }

    private static JPanel padded(JPanel p, int v, int h) {
        p.setBorder(BorderFactory.createEmptyBorder(v, h, v, h));
        return p;
    }

    private static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return l;
    }

    private static JLabel hint(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.ITALIC, 11));
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private static JTextField field(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return tf;
    }

    private static JTextField field(String text, int cols) {
        JTextField tf = field(cols);
        tf.setText(text);
        return tf;
    }

    private static JButton accentButton(String text) {
        return styledButton(text, ACCENT, ACCENT_DARK);
    }

    private static JButton grayButton(String text) {
        return styledButton(text, new Color(110, 115, 125), new Color(80, 85, 95));
    }

    private static JButton styledButton(String text, Color bg, Color hover) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    private static void applyNimbus() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try { UIManager.setLookAndFeel(info.getClassName()); } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {}
                break;
            }
        }
    }
}