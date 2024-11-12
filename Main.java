// Main.java
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}

// LoginFrame.java
class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static ArrayList<User> users = new ArrayList<>(); // List to store registered users

    public LoginFrame() {
        setTitle("Bus Ticket Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Welcome to Bus Ticket System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login fields
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            if (validateLogin()) {
                new MainFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        registerButton.addActionListener(e -> new RegistrationFrame(users)); // Open registration frame

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(loginPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private boolean validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}

// RegistrationFrame.java
class RegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ArrayList<User> users;

    public RegistrationFrame(ArrayList<User> users) {
        this.users = users;

        setTitle("Register - Bus Ticket System");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());

        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
        } else {
            users.add(new User(username, password));
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
        }
    }
}

// User.java
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// MainFrame.java
// (No changes here)
// MainFrame.java
class MainFrame extends JFrame {
    private ArrayList<Bus> buses;
    private JPanel contentPanel;

    public MainFrame() {
        initializeBuses();
        setupUI();
    }

    private void initializeBuses() {
        buses = new ArrayList<>();
        buses.add(new Bus("Express-1", "Mumbai", "Delhi", "10:00 AM", 1500.0, 40));
        buses.add(new Bus("Express-2", "Delhi", "Bangalore", "11:00 AM", 2000.0, 40));
        buses.add(new Bus("Luxury-1", "Chennai", "Hyderabad", "09:00 AM", 1800.0, 40));
    }

    private void setupUI() {
        setTitle("Bus Ticket Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main content panel
        contentPanel = new JPanel(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create bus list panel
        JComponent busListPanel = createBusListPanel(); // Changed from JPanel to JComponent
        contentPanel.add(busListPanel, BorderLayout.CENTER);

        add(contentPanel);
        setVisible(true);
    }

    private JComponent createBusListPanel() { // Changed return type from JPanel to JComponent
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Available Buses");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        for (Bus bus : buses) {
            panel.add(createBusCard(bus));
            panel.add(Box.createVerticalStrut(10));
        }

        return new JScrollPane(panel);
    }

    private JPanel createBusCard(Bus bus) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Bus info
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.add(new JLabel("Bus Name: " + bus.getName()));
        infoPanel.add(new JLabel("Route: " + bus.getSource() + " to " + bus.getDestination()));
        infoPanel.add(new JLabel("Time: " + bus.getDepartureTime()));
        infoPanel.add(new JLabel("Fare: ₹" + bus.getFare()));
        infoPanel.add(new JLabel("Available Seats: " + bus.getAvailableSeats()));

        // Book button
        JButton bookButton = new JButton("Book Tickets");
        bookButton.addActionListener(e -> showSeatSelection(bus));

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(bookButton, BorderLayout.EAST);

        return card;
    }

    private void showSeatSelection(Bus bus) {
        JDialog seatDialog = new JDialog(this, "Select Seats", true);
        seatDialog.setSize(600, 400);
        seatDialog.setLocationRelativeTo(this);

        JPanel seatPanel = new JPanel(new GridLayout(8, 5, 5, 5));
        ArrayList<JToggleButton> seatButtons = new ArrayList<>();

        // Create seat buttons
        for (int i = 1; i <= 40; i++) {
            JToggleButton seatButton = new JToggleButton("Seat " + i);
            if (!bus.isSeatAvailable(i)) {
                seatButton.setEnabled(false);
                seatButton.setText("Booked");
            }
            seatButtons.add(seatButton);
            seatPanel.add(seatButton);
        }

        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(e -> {
            ArrayList<Integer> selectedSeats = new ArrayList<>();
            for (int i = 0; i < seatButtons.size(); i++) {
                if (seatButtons.get(i).isSelected()) {
                    selectedSeats.add(i + 1);
                }
            }
            if (!selectedSeats.isEmpty()) {
                double totalFare = selectedSeats.size() * bus.getFare();
                int confirm = JOptionPane.showConfirmDialog(
                        seatDialog,
                        "Total fare: ₹" + totalFare + "\nConfirm booking?",
                        "Confirm Booking",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bus.bookSeats(selectedSeats);
                    JOptionPane.showMessageDialog(seatDialog, "Booking confirmed!");
                    seatDialog.dispose();
                    refreshBusList();
                }
            } else {
                JOptionPane.showMessageDialog(seatDialog, "Please select at least one seat");
            }
        });

        seatDialog.setLayout(new BorderLayout());
        seatDialog.add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        seatDialog.add(confirmButton, BorderLayout.SOUTH);
        seatDialog.setVisible(true);
    }

    private void refreshBusList() {
        contentPanel.removeAll();
        contentPanel.add(createBusListPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

// Bus.java
class Bus {
    private String name;
    private String source;
    private String destination;
    private String departureTime;
    private double fare;
    private boolean[] seats;
    private int totalSeats;

    public Bus(String name, String source, String destination, String departureTime, double fare, int totalSeats) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.fare = fare;
        this.totalSeats = totalSeats;
        this.seats = new boolean[totalSeats];
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public double getFare() {
        return fare;
    }

    public int getAvailableSeats() {
        int available = 0;
        for (boolean seat : seats) {
            if (!seat)
                available++;
        }
        return available;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return !seats[seatNumber - 1];
    }

    public void bookSeats(ArrayList<Integer> seatNumbers) {
        for (int seatNumber : seatNumbers) {
            seats[seatNumber - 1] = true;
        }
    }
}