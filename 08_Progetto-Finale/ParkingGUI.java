import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ParkingGUI extends JFrame {

    private Parking parking;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField plateField;
    private final JTextField entryTimeField;
    private final JLabel statusLabel;

    public ParkingGUI(Parking parking) {
        this.parking = parking;

        setTitle("Gestione Parcheggio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 460);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        // --- Pannello inserimento auto ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nuova auto"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0;
        form.add(new JLabel("Targa:"), gc);
        plateField = new JTextField(12);
        gc.gridx = 1; gc.weightx = 1;
        form.add(plateField, gc);

        gc.gridx = 2; gc.weightx = 0;
        form.add(new JLabel("Ora ingresso:"), gc);
        entryTimeField = new JTextField(6);
        gc.gridx = 3;
        form.add(entryTimeField, gc);

        JButton enterBtn = new JButton("Entra");
        gc.gridx = 4;
        form.add(enterBtn, gc);

        root.add(form, BorderLayout.NORTH);

        // --- Tabella auto ---
        tableModel = new DefaultTableModel(new Object[]{"#", "Targa", "Ora ingresso"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Pannello azioni in basso ---
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        JButton exitBtn = new JButton("Fai uscire (per targa)");
        JButton editTimeBtn = new JButton("Modifica ora ingresso");
        JButton refreshBtn = new JButton("Aggiorna");
        actions.add(exitBtn);
        actions.add(editTimeBtn);
        actions.add(refreshBtn);

        JPanel south = new JPanel(new BorderLayout());
        south.add(actions, BorderLayout.NORTH);
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        south.add(statusLabel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        // --- Listener ---
        enterBtn.addActionListener(e -> onEnter());
        exitBtn.addActionListener(e -> onExit());
        editTimeBtn.addActionListener(e -> onEditTime());
        refreshBtn.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void onEnter() {
        String plate = plateField.getText();
        if (plate == null || plate.trim().isEmpty()) {
            setStatus("Inserisci una targa.", true);
            return;
        }

        double entryTime;
        try {
            entryTime = Double.parseDouble(entryTimeField.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            setStatus("Ora ingresso non valida (es. 13.00).", true);
            return;
        }

        if (parking.isFull()) {
            setStatus("Il parcheggio e' pieno (" + parking.getCapacity() + " posti).", true);
            return;
        }

        parking.enter(new Car(plate, entryTime));
        plateField.setText("");
        entryTimeField.setText("");
        refreshTable();
        setStatus("Auto inserita.", false);
    }

    private void onExit() {
        int row = table.getSelectedRow();
        String plate;
        if (row >= 0) {
            plate = (String) tableModel.getValueAt(row, 1);
        } else {
            plate = JOptionPane.showInputDialog(this, "Targa dell'auto da far uscire:");
            if (plate == null) {
                return;
            }
            plate = plate.trim().toUpperCase();
        }

        int before = parking.size();
        parking.exit(plate);
        if (parking.size() < before) {
            setStatus("Auto " + plate + " uscita.", false);
        } else {
            setStatus("Auto non trovata: " + plate, true);
        }
        refreshTable();
    }

    private void onEditTime() {
        int row = table.getSelectedRow();
        if (row < 0) {
            setStatus("Seleziona un'auto dalla tabella.", true);
            return;
        }
        Car car = parking.getCar(row);
        String input = JOptionPane.showInputDialog(this,
                "Nuova ora ingresso per " + car.getPlate() + ":",
                car.getEntryTime());
        if (input == null) {
            return;
        }
        try {
            car.setEntryTime(Double.parseDouble(input.trim().replace(",", ".")));
            refreshTable();
            setStatus("Ora ingresso aggiornata.", false);
        } catch (NumberFormatException ex) {
            setStatus("Valore non valido.", true);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < parking.size(); i++) {
            Car c = parking.getCar(i);
            tableModel.addRow(new Object[]{i, c.getPlate(), c.getEntryTime()});
        }
        setTitle("Gestione Parcheggio  -  " + parking.size() + "/" + parking.getCapacity() + " posti");
    }

    private void setStatus(String message, boolean error) {
        statusLabel.setText(message);
        statusLabel.setForeground(error ? new Color(180, 0, 0) : new Color(0, 120, 0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Parking parking = new Parking(2);
            parking.enter(new Car("  ab123  ", 13.00));
            parking.enter(new Car(" cd456 ", 14.00));
            new ParkingGUI(parking).setVisible(true);
        });
    }
}
