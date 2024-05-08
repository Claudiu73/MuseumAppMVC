package View;

import Languages.LanguageObservable;
import Languages.Observer;
import Repo.UserRepository;
import Controller.LogInController;

import javax.swing.*;
import java.awt.*;

public class LogIn implements Observer{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton logInButton;
    private JComboBox comboBox1;
    private JFrame frame;
    private LogInController logInController;
    private JLabel usernameLabel, passwordLabel;
    private LanguageObservable languageObservable;

    public LogIn(LogInController viewModel, UserRepository userRepository) {
        this.logInController = viewModel;  // Folosește instanța pasată, nu crea una nouă
        initializeUI();
    }

    private void initializeUI() {
        this.languageObservable = new LanguageObservable();
        frame = new JFrame("Autentificare");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\sirbu\\Desktop\\Cauta\\Faculta 3.2\\PS\\MuseumApp\\LogIn fundal.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        setupLabelsAndFields(panel);

        logInButton = new JButton("Conectare");
        logInButton.setBounds(100, 120, 100, 30);
        logInButton.setBackground(new Color(232, 181, 17, 150));
        logInButton.setOpaque(true);
        logInButton.setBorderPainted(false);
        logInButton.addActionListener(e -> {performLogin();
            frame.dispose();});
        panel.add(logInButton);
        panel.add(comboBox1);

        setupComboBox(panel);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void setupLabelsAndFields(JPanel panel) {
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 10, 200, 20);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        panel.add(usernameLabel);

        textField1 = new JTextField(20);
        textField1.setBounds(50, 30, 200, 30);
        panel.add(textField1);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 60, 200, 20);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        panel.add(passwordLabel);

        passwordField1 = new JPasswordField(20);
        passwordField1.setBounds(50, 80, 200, 30);
        panel.add(passwordField1);
    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(225, 120, 50, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();
    }

    private void performLogin() {
        logInController.setUsername(textField1.getText().trim());
        logInController.setPassword(new String(passwordField1.getPassword()).trim());
        logInController.initiateLogin();
    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        logInButton.setText(languageObservable.getLanguageText("LogIn.button.logInButton"));
        usernameLabel.setText(languageObservable.getLanguageText("LogIn.label.usernameLabel"));
        passwordLabel.setText(languageObservable.getLanguageText("LogIn.label.passwordLabel"));
    }


    public void showScreen() {
        frame.setVisible(true);
    }
}
