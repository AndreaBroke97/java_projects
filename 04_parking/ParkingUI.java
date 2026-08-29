/*
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class ParkingUI extends JFrame {

    private Parking  parking;
    private int      selectedSlot = -1;
    private int      searchedSlot = -1;
    private JPanel[] slotPanels;
    private JPanel   gridPanel;
    private JPanel   southPanel;

    private final SignPanel  signPanel    = new SignPanel();
    private final JLabel     statusLabel  = new JLabel("AVAILABLE", SwingConstants.CENTER);
    private final JLabel     spotsLabel   = new JLabel("", SwingConstants.CENTER);
    private final JTextField plateField   = new JTextField(12);
    private final JTextField searchField  = new JTextField(12);
    private final JLabel     searchResult = new JLabel(" ", SwingConstants.CENTER);
    private final JButton    addBtn       = new JButton("+ Add Car");
    private final JButton    removeBtn    = new JButton("- Remove Car");
    private final JButton    historyBtn   = new JButton("History");

    public ParkingUI(int size) {
        parking    = new Parking(size);
        slotPanels = new JPanel[size];

        setTitle("Parking Lot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        Color bg = new Color(25, 25, 35);
        ((JPanel) getContentPane()).setBackground(bg);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        add(buildTopBar(bg), BorderLayout.NORTH);
        add(buildCenter(bg), BorderLayout.CENTER);
        add(buildSouth(bg),  BorderLayout.SOUTH);

        refresh();
        pack();
        setMinimumSize(new Dimension(420, 560));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ---- top bar: search + resize ----
    private JPanel buildTopBar(Color bg) {
        JButton searchBtn = new JButton("Find");
        JButton resizeBtn = new JButton("Resize Park");
        styleButton(searchBtn, new Color(55, 95, 175));
        styleButton(resizeBtn, new Color(95, 55, 160));
        searchBtn.setPreferredSize(new Dimension(60,  34));
        resizeBtn.setPreferredSize(new Dimension(105, 34));

        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setHorizontalAlignment(JTextField.CENTER);

        searchResult.setFont(new Font("Arial", Font.ITALIC, 11));
        searchResult.setForeground(new Color(180, 180, 80));

        searchBtn.addActionListener(e -> searchPlate());
        searchField.addActionListener(e -> searchPlate());  // Enter triggers search
        resizeBtn.addActionListener(e -> resizeParking());

        JLabel searchLabel = new JLabel("Search plate:", SwingConstants.RIGHT);
        searchLabel.setForeground(new Color(140, 140, 140));
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        searchLabel.setPreferredSize(new Dimension(90, 0));

        JPanel searchRow = new JPanel(new BorderLayout(6, 0));
        searchRow.setBackground(bg);
        searchRow.add(searchLabel, BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);
        searchRow.add(searchBtn,   BorderLayout.EAST);

        JPanel topBar = new JPanel(new BorderLayout(8, 4));
        topBar.setBackground(bg);
        topBar.add(searchRow,   BorderLayout.CENTER);
        topBar.add(resizeBtn,   BorderLayout.EAST);
        topBar.add(searchResult, BorderLayout.SOUTH);
        return topBar;
    }

    // ---- center: P sign + status labels ----
    private JPanel buildCenter(Color bg) {
        signPanel.setPreferredSize(new Dimension(200, 200));
        signPanel.setOpaque(false);

        statusLabel.setFont(new Font("Arial", Font.BOLD, 22));
        statusLabel.setForeground(new Color(80, 220, 80));

        spotsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        spotsLabel.setForeground(new Color(160, 160, 160));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bg);
        signPanel.setAlignmentX(CENTER_ALIGNMENT);
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        spotsLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(signPanel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(statusLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(spotsLabel);
        return panel;
    }

    // ---- south: slot grid + plate input + buttons ----
    private JPanel buildSouth(Color bg) {
        gridPanel = buildGrid();

        plateField.setFont(new Font("Arial", Font.PLAIN, 14));
        plateField.setHorizontalAlignment(JTextField.CENTER);

        styleButton(addBtn,     new Color(40, 150, 40));
        styleButton(removeBtn,  new Color(170, 45, 45));
        styleButton(historyBtn, new Color(70, 120, 180));
        addBtn.setEnabled(false);
        removeBtn.setEnabled(false);

        historyBtn.addActionListener(e -> showHistory());

        addBtn.addActionListener(e -> {
            String plate = plateField.getText().trim().toUpperCase();
            if (plate.isEmpty() || selectedSlot < 0) return;
            parking.addCar(new Car(plate), selectedSlot);
            selectedSlot = -1;
            plateField.setText("");
            refresh();
        });

        removeBtn.addActionListener(e -> {
            if (selectedSlot < 0) return;
            parking.removeCar(selectedSlot);
            selectedSlot = -1;
            plateField.setText("");
            refresh();
        });

        JLabel plateLabel = new JLabel("Plate:", SwingConstants.RIGHT);
        plateLabel.setForeground(new Color(140, 140, 140));
        plateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        plateLabel.setPreferredSize(new Dimension(45, 0));

        JPanel inputRow = new JPanel(new BorderLayout(6, 0));
        inputRow.setBackground(bg);
        inputRow.add(plateLabel, BorderLayout.WEST);
        inputRow.add(plateField, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new GridLayout(1, 3, 8, 0));
        btnRow.setBackground(bg);
        btnRow.add(addBtn);
        btnRow.add(removeBtn);
        btnRow.add(historyBtn);

        JLabel hint = new JLabel("Click a slot to select it", SwingConstants.CENTER);
        hint.setFont(new Font("Arial", Font.ITALIC, 11));
        hint.setForeground(new Color(100, 100, 100));

        JPanel inputArea = new JPanel();
        inputArea.setLayout(new BoxLayout(inputArea, BoxLayout.Y_AXIS));
        inputArea.setBackground(bg);
        inputArea.add(hint);
        inputArea.add(Box.createVerticalStrut(6));
        inputArea.add(inputRow);
        inputArea.add(Box.createVerticalStrut(6));
        inputArea.add(btnRow);

        southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.setBackground(bg);
        southPanel.add(gridPanel,  BorderLayout.NORTH);
        southPanel.add(inputArea,  BorderLayout.SOUTH);
        return southPanel;
    }

    // builds the clickable slot grid (called also on resize)
    private JPanel buildGrid() {
        int size = parking.size();
        int cols = Math.min(5, size);
        int rows = (int) Math.ceil((double) size / cols);

        JPanel grid = new JPanel(new GridLayout(rows, cols, 6, 6));
        grid.setBackground(new Color(35, 35, 50));
        grid.setBorder(new EmptyBorder(8, 8, 8, 8));

        slotPanels = new JPanel[size];
        for (int i = 0; i < size; i++) {
            final int idx = i;
            JPanel slot = new JPanel(new BorderLayout(0, 2));
            slot.setPreferredSize(new Dimension(60, 52));
            slot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedSlot = (selectedSlot == idx) ? -1 : idx;
                    searchedSlot = -1;
                    searchResult.setText(" ");
                    Car car = parking.getCarAt(idx);
                    String p = "";
                    if (car != null) p = car.getPlate();
                    plateField.setText(p);
                    refresh();
                }
            });
            slotPanels[i] = slot;
            grid.add(slot);
        }
        // fill remainder cells so the grid stays rectangular
        int remainder = rows * cols - size;
        for (int i = 0; i < remainder; i++) {
            JPanel empty = new JPanel();
            empty.setBackground(new Color(35, 35, 50));
            grid.add(empty);
        }
        return grid;
    }

    private void searchPlate() {
        String query = searchField.getText().trim().toUpperCase();
        if (query.isEmpty()) {
            searchedSlot = -1;
            searchResult.setText(" ");
            refresh();
            return;
        }
        for (int i = 0; i < parking.size(); i++) {
            Car car = parking.getCarAt(i);
            if (car != null && car.getPlate().equalsIgnoreCase(query)) {
                searchedSlot = i;
                selectedSlot = -1;
                searchResult.setText("Found in slot " + (i + 1));
                searchResult.setForeground(new Color(80, 220, 80));
                refresh();
                return;
            }
        }
        searchedSlot = -1;
        searchResult.setText("Plate \"" + query + "\" not found");
        searchResult.setForeground(new Color(255, 100, 100));
        refresh();
    }

    private void resizeParking() {
        String input = JOptionPane.showInputDialog(this, "Enter new number of parking spots:", parking.size());
        if (input == null) return;
        try {
            int newSize = Integer.parseInt(input.trim());
            if (newSize < 1 || newSize > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100.", "Invalid size", JOptionPane.WARNING_MESSAGE);
                return;
            }
            parking      = new Parking(newSize);
            selectedSlot = -1;
            searchedSlot = -1;
            plateField.setText("");
            searchField.setText("");
            searchResult.setText(" ");

            southPanel.remove(gridPanel);
            gridPanel = buildGrid();
            southPanel.add(gridPanel, BorderLayout.NORTH);
            southPanel.revalidate();
            southPanel.repaint();
            pack();
            refresh();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refresh() {
        boolean full = parking.isFull();
        signPanel.setFull(full);
        statusLabel.setText(full ? "FULL" : "AVAILABLE");
        statusLabel.setForeground(full ? new Color(255, 80, 80) : new Color(80, 220, 80));
        spotsLabel.setText(parking.availablePlaces() + " spots free of " + parking.size());

        for (int i = 0; i < slotPanels.length; i++) {
            JPanel slot = slotPanels[i];
            slot.removeAll();

            Car     car = parking.getCarAt(i);
            boolean occ = car != null;
            boolean sel = i == selectedSlot;
            boolean fnd = i == searchedSlot;

            slot.setBackground(occ ? new Color(180, 50, 50) : new Color(45, 170, 45));

            if (fnd)      slot.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
            else if (sel) slot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            else          slot.setBorder(new EmptyBorder(3, 3, 3, 3));

            JLabel numLbl = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            numLbl.setForeground(new Color(255, 255, 255, 160));
            numLbl.setFont(new Font("Arial", Font.PLAIN, 9));

            String slotText = "free";
            if (car != null) slotText = car.getPlate();
            JLabel textLbl = new JLabel(slotText, SwingConstants.CENTER);
            textLbl.setForeground(Color.WHITE);
            textLbl.setFont(new Font("Arial", Font.BOLD, occ ? 9 : 10));

            slot.add(numLbl,  BorderLayout.NORTH);
            slot.add(textLbl, BorderLayout.CENTER);
        }

        if (selectedSlot >= 0) {
            boolean slotOcc = parking.getCarAt(selectedSlot) != null;
            addBtn.setEnabled(!slotOcc);
            removeBtn.setEnabled(slotOcc);
        } else {
            addBtn.setEnabled(false);
            removeBtn.setEnabled(false);
        }

        revalidate();
        repaint();
    }

    private void showHistory() {
        ArrayList<StopOver> history = parking.getHistory();
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No history yet.", "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s  %-10s  %-8s%n", "Plate", "Minutes", "Price"));
        sb.append("─".repeat(35)).append("\n");
        for (StopOver s : history) {
            sb.append(String.format("%-10s  %-10d  €%.2f%n",
                s.car.getPlate(), s.getDuration(), s.getPrice()));
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Parking History", JOptionPane.PLAIN_MESSAGE);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 36));
    }

    // ---- circular P sign ----
    class SignPanel extends JPanel {
        private boolean full = false;
        void setFull(boolean full) { this.full = full; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int size = Math.min(w, h) - 10;
            int x = (w - size) / 2, y = (h - size) / 2;

            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillOval(x + 4, y + 4, size, size);

            g2.setColor(full ? new Color(200, 40, 40) : new Color(30, 160, 30));
            g2.fillOval(x, y, size, size);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5));
            g2.drawOval(x + 3, y + 3, size - 6, size - 6);

            g2.setFont(new Font("Arial", Font.BOLD, (int)(size * 0.58)));
            FontMetrics fm = g2.getFontMetrics();
            int tx = (w - fm.stringWidth("P")) / 2;
            int ty = y + (size + fm.getAscent() - fm.getDescent()) / 2;
            g2.setColor(new Color(0, 0, 0, 40));
            g2.drawString("P", tx + 2, ty + 2);
            g2.setColor(Color.WHITE);
            g2.drawString("P", tx, ty);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String input = JOptionPane.showInputDialog(null, "How many parking spots?", "10");
            int size = 10;
            if (input != null) {
                try { size = Math.max(1, Math.min(100, Integer.parseInt(input.trim()))); }
                catch (NumberFormatException ignored) {}
            }
            ParkingUI ui = new ParkingUI(size);
            ui.setVisible(true);
        });
    }
}
*/
// ParkingUI = interfaccia grafica del parcheggio, costruita con Java Swing
// Swing è la libreria Java per creare finestre, bottoni, etichette ecc.
// Questa classe NON contiene la logica del parcheggio — si appoggia a Parking, Car e StopOver
import javax.swing.*;        // JFrame, JLabel, JButton, JTextField, JTextArea, ecc.
import javax.swing.border.*; // TitledBorder per i riquadri con titolo
import java.awt.*;           // BorderLayout, GridLayout, FlowLayout, Color, Font, ecc.
import java.util.HashMap;    // HashMap: struttura dati chiave→valore
import java.util.Map;        // interfaccia di Map (usata con HashMap)

public class ParkingUI {

    // costante: numero fisso di posti — static final = non cambia mai, condiviso da tutti
    private static final int TOTAL_PLACES = 10;

    // oggetto logica del parcheggio (dove avviene il vero calcolo)
    private final Parking parking = new Parking(TOTAL_PLACES);

    // mappa che associa ogni NUMERO DI POSTO all'oggetto Car parcheggiato lì
    // es: {1 → Car("AB123"), 3 → Car("XY456")}
    // serve per trovare velocemente quale auto è in quale posto
    // NOTA: usiamo la stessa istanza Car che è dentro StopOver, così il confronto
    // con == in exitCarFromParking funziona correttamente
    private final Map<Integer, Car> slotCars = new HashMap<>();

    // componenti grafici della finestra (tutti privati: solo questa classe li gestisce)
    private JFrame frame;           // la finestra principale
    private JLabel[] slotLabels;    // array di etichette: una per ogni posto del parcheggio
    private JLabel availableLabel;  // mostra "Posti liberi: X / 10" nell'header
    private JLabel amountLabel;     // mostra l'incasso totale nell'header
    private JTextField plateEnterField;  // campo testo per la targa in ingresso
    private JTextField positionField;    // campo testo per il numero del posto
    private JTextField plateExitField;   // campo testo per la targa in uscita
    private JTextArea logArea;      // area di testo dove appaiono i messaggi di log

    // costruttore: creare un ParkingUI costruisce subito la finestra
    public ParkingUI() {
        buildUi();
    }

    // ==========================================================================
    // COSTRUZIONE DELL'INTERFACCIA
    // ==========================================================================

    // buildUi: assembla la finestra dividendola in tre zone (BorderLayout):
    //   NORTH  → header blu con titolo e statistiche
    //   CENTER → griglia dei posti
    //   SOUTH  → pannello comandi (entra/esci) + log
    private void buildUi() {
        frame = new JFrame("Parking Lot Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // chiude il programma alla X
        frame.setSize(860, 620);
        frame.setLayout(new BorderLayout(8, 8)); // gap 8px tra le zone
        ((JPanel) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        frame.add(buildHeader(),     BorderLayout.NORTH);
        frame.add(buildSlotsPanel(), BorderLayout.CENTER);
        frame.add(buildBottomPanel(), BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // centra la finestra nello schermo
        frame.setVisible(true);
    }

    // buildHeader: crea la barra blu in cima con titolo + posti liberi + incasso
    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 60, 114)); // blu scuro
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("Parking Lot Manager");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        // le due label di stato vengono aggiornate da refreshStatus()
        availableLabel = new JLabel();
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        availableLabel.setForeground(new Color(180, 220, 255));

        amountLabel = new JLabel();
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        amountLabel.setForeground(new Color(160, 255, 160));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        right.setOpaque(false); // trasparente: mostra il blu del pannello padre
        right.add(availableLabel);
        right.add(amountLabel);

        panel.add(title, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);

        refreshStatus(); // popola subito le label con i valori iniziali
        return panel;
    }

    // buildSlotsPanel: crea la griglia visiva dei posti (es. 2 righe × 5 colonne per 10 posti)
    // ogni posto è una JLabel con numero e stato (LIBERO / targa)
    private JPanel buildSlotsPanel() {
        int cols = 5;
        int rows = (int) Math.ceil((double) TOTAL_PLACES / cols); // es. 10/5 = 2 righe
        JPanel panel = new JPanel(new GridLayout(rows, cols, 10, 10)); // griglia con gap 10px
        panel.setBorder(BorderFactory.createCompoundBorder(
            titledBorder("Posti Parcheggio", new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        slotLabels = new JLabel[TOTAL_PLACES];
        for (int i = 0; i < TOTAL_PLACES; i++) {
            slotLabels[i] = buildSlotLabel(i + 1, null); // null = posto libero
            panel.add(slotLabels[i]);
        }
        return panel;
    }

    // buildBottomPanel: zona sotto con i controlli e il log
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.add(buildControlsRow(), BorderLayout.NORTH);  // due pannelli affiancati: entra / esci
        panel.add(buildLogArea(),     BorderLayout.CENTER); // area log sotto
        return panel;
    }

    // buildControlsRow: affianca il pannello "Entra auto" e "Esci auto"
    private JPanel buildControlsRow() {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 riga, 2 colonne
        row.add(buildEnterPanel());
        row.add(buildExitPanel());
        return row;
    }

    // buildEnterPanel: form per far ENTRARE un'auto
    // campi: targa + numero posto + bottone "Parcheggia"
    private JPanel buildEnterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        panel.setBorder(titledBorder("Entra auto", new Color(30, 100, 200)));

        plateEnterField = new JTextField(10);
        plateEnterField.setToolTipText("Targa (es. AB123CD)");

        positionField = new JTextField(4);
        positionField.setToolTipText("Numero posto (1-" + TOTAL_PLACES + ")");

        JButton btn = colorButton("Parcheggia", new Color(25, 118, 210));
        btn.addActionListener(e -> parkCar()); // lambda: al click chiama parkCar()
        plateEnterField.addActionListener(e -> positionField.requestFocus()); // Invio → sposta cursore
        positionField.addActionListener(e -> parkCar());                       // Invio → parcheggia

        panel.add(new JLabel("Targa:"));
        panel.add(plateEnterField);
        panel.add(new JLabel("Posto:"));
        panel.add(positionField);
        panel.add(btn);
        return panel;
    }

    // buildExitPanel: form per far USCIRE un'auto
    // campo: solo targa (il posto viene trovato automaticamente dalla mappa slotCars)
    private JPanel buildExitPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        panel.setBorder(titledBorder("Esci auto", new Color(180, 30, 30)));

        plateExitField = new JTextField(10);
        plateExitField.setToolTipText("Targa dell'auto da far uscire");

        JButton btn = colorButton("Fai uscire", new Color(183, 28, 28));
        btn.addActionListener(e -> exitCar());
        plateExitField.addActionListener(e -> exitCar());

        panel.add(new JLabel("Targa:"));
        panel.add(plateExitField);
        panel.add(btn);
        return panel;
    }

    // buildLogArea: area di testo non modificabile dove vengono stampati [OK], [WARN], [ERR]
    private JScrollPane buildLogArea() {
        logArea = new JTextArea(5, 60);
        logArea.setEditable(false); // l'utente non può scrivere qui
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(248, 248, 248));

        JScrollPane scroll = new JScrollPane(logArea); // aggiunge scrollbar automatica
        scroll.setBorder(titledBorder("Log eventi", new Color(100, 100, 100)));
        return scroll;
    }

    // ==========================================================================
    // GESTIONE DELLE ETICHETTE DEI POSTI
    // ==========================================================================

    // crea una nuova JLabel per un posto: verde se libero, rossa con targa se occupato
    private JLabel buildSlotLabel(int position, String plate) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setOpaque(true); // necessario per mostrare il colore di sfondo
        label.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        label.setPreferredSize(new Dimension(130, 75));
        applySlotStyle(label, position, plate);
        return label;
    }

    // applica stile visivo alla label del posto:
    // - plate == null → posto LIBERO (sfondo verde chiaro)
    // - plate != null → posto OCCUPATO con la targa (sfondo rosso chiaro)
    // usa HTML inline per formattare il testo dentro la JLabel
    private void applySlotStyle(JLabel label, int position, String plate) {
        if (plate == null) {
            label.setText("<html><center><big><b>P" + position + "</b></big><br>"
                + "<font color='#2e7d32'>LIBERO</font></center></html>");
            label.setBackground(new Color(232, 245, 233)); // verde chiaro
        } else {
            label.setText("<html><center><big><b>P" + position + "</b></big><br>"
                + "<font color='#b71c1c'><b>" + plate + "</b></font></center></html>");
            label.setBackground(new Color(255, 235, 238)); // rosso chiaro
        }
    }

    // aggiorna visivamente la label di un singolo posto (dopo entrata o uscita)
    // position è 1-based (posto 1 = slotLabels[0])
    private void updateSlot(int position, String plate) {
        applySlotStyle(slotLabels[position - 1], position, plate);
        slotLabels[position - 1].repaint(); // forza il ridisegno del componente
    }

    // ==========================================================================
    // AZIONI UTENTE
    // ==========================================================================

    // parkCar: eseguita quando si clicca "Parcheggia"
    // 1. legge targa e posto dai campi testo
    // 2. valida i dati (campi vuoti, fuori range, posto già occupato, parcheggio pieno)
    // 3. chiama parking.addCar() e aggiorna la UI
    private void parkCar() {
        String plate  = plateEnterField.getText().trim().toUpperCase(); // toUpperCase: normalizza la targa
        String posStr = positionField.getText().trim();

        // validazione: campi vuoti
        if (plate.isEmpty() || posStr.isEmpty()) {
            log("[WARN] Inserisci targa e numero posto.");
            return;
        }

        // validazione: il posto deve essere un numero intero
        int position;
        try {
            position = Integer.parseInt(posStr); // converte stringa → int
        } catch (NumberFormatException ex) {
            log("[ERR] Numero posto non valido: deve essere un numero intero.");
            return;
        }

        // validazione: il posto deve essere nel range 1..TOTAL_PLACES
        if (position < 1 || position > TOTAL_PLACES) {
            log("[ERR] Posto " + position + " fuori range (1-" + TOTAL_PLACES + ").");
            return;
        }

        // validazione: il posto non deve essere già occupato (controllo sulla mappa locale)
        if (slotCars.containsKey(position)) {
            log("[WARN] Posto " + position + " gia' occupato da "
                + slotCars.get(position).getPlate() + ".");
            return;
        }

        // validazione: il parcheggio non deve essere pieno
        if (parking.isCompleted()) {
            log("[WARN] Parcheggio pieno!");
            return;
        }

        // tutto ok: crea l'auto, aggiungila alla logica e aggiorna la UI
        Car car = new Car(plate);
        if (parking.addCar(car, position)) {
            slotCars.put(position, car); // salva riferimento nella mappa locale
            updateSlot(position, plate);
            refreshStatus();
            log("[OK] Auto " + plate + " parcheggiata al posto " + position + ".");
            plateEnterField.setText("");
            positionField.setText("");
            plateEnterField.requestFocus(); // rimette il cursore nel campo targa
        } else {
            log("[ERR] Impossibile parcheggiare al posto " + position + ".");
        }
    }

    // exitCar: eseguita quando si clicca "Fai uscire"
    // 1. legge la targa dal campo testo
    // 2. cerca il posto nella mappa slotCars
    // 3. chiama parking.exitCarFromParking() e aggiorna la UI
    private void exitCar() {
        String plate = plateExitField.getText().trim().toUpperCase();
        if (plate.isEmpty()) {
            log("[WARN] Inserisci la targa dell'auto da far uscire.");
            return;
        }

        // cerca il posto associato a questa targa scorrendo la mappa
        Integer position = null;
        Car car = null;
        for (Map.Entry<Integer, Car> entry : slotCars.entrySet()) {
            if (entry.getValue().getPlate().equals(plate)) {
                position = entry.getKey(); // numero del posto trovato
                car = entry.getValue();    // oggetto Car trovato
                break;
            }
        }

        // se non trovata nella mappa → auto non è nel parcheggio
        if (position == null || car == null) {
            log("[ERR] Auto " + plate + " non trovata nel parcheggio.");
            return;
        }

        // usiamo car.getPlate() e NON la variabile "plate": è lo stesso oggetto String
        // che è dentro StopOver, quindi il confronto == in exitCarFromParking funziona
        parking.exitCarFromParking(car.getPlate());
        slotCars.remove(position);       // rimuove dalla mappa locale
        updateSlot(position, null);      // aggiorna visivamente il posto a LIBERO
        refreshStatus();
        log("[OK] Auto " + plate + " uscita dal posto " + position
            + " | Incasso totale: " + formatAmount(parking.getAmount()));
        plateExitField.setText("");
        plateExitField.requestFocus();
    }

    // ==========================================================================
    // AGGIORNAMENTO STATO
    // ==========================================================================

    // refreshStatus: aggiorna header e titolo finestra con i dati attuali del parcheggio
    // va chiamata dopo ogni entrata o uscita
    private void refreshStatus() {
        int available = parking.getAvailablePlaces();
        availableLabel.setText("Posti liberi: " + available + " / " + TOTAL_PLACES);
        amountLabel.setText("Incasso: " + formatAmount(parking.getAmount()));

        // se pieno, segnala nel titolo della finestra
        if (parking.isCompleted()) {
            frame.setTitle("Parking Lot Manager [PARCHEGGIO PIENO]");
        } else {
            frame.setTitle("Parking Lot Manager");
        }
    }

    // ==========================================================================
    // METODI DI UTILITÀ (privati, riusati più volte)
    // ==========================================================================

    // log: aggiunge una riga nella logArea e fa lo scroll automatico in fondo
    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength()); // scroll in fondo
    }

    // formatAmount: formatta il numero come stringa con 2 decimali
    private String formatAmount(double amount) {
        return String.format("%.2f ms-units", amount);
    }

    // titledBorder: crea un bordo con titolo colorato (riusato per ogni sezione)
    private static TitledBorder titledBorder(String title, Color color) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(color, 1),
            title,
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12));
    }

    // colorButton: crea un bottone con sfondo colorato e testo bianco
    private static JButton colorButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        return btn;
    }

    // ==========================================================================
    // PUNTO DI INGRESSO
    // ==========================================================================

    // main: avvia l'interfaccia grafica
    // SwingUtilities.invokeLater assicura che la UI venga creata sul thread corretto
    // (Swing non è thread-safe: tutta la grafica deve girare sull'Event Dispatch Thread)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParkingUI::new); // ParkingUI::new = () -> new ParkingUI()
    }
}

