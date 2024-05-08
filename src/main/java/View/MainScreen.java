package View;

import Controller.LogInController;
import Controller.MainScreenController;
import Languages.LanguageObservable;
import Languages.Observer;
import Repo.UserRepository;

import javax.swing.*;
import java.awt.*;

public class MainScreen implements Observer {

    private MainScreenController mainScreenController;
    private JButton vizitatorButton;
    private JButton angajatButton;
    private JButton adminButton;
    private JComboBox comboBox1;
    private JFrame frame;
    private LogInController logInController;
    private UserRepository userRepository;
    private LanguageObservable languageObservable;
    private JLabel titleLabel;
    private JLabel promptLabel;

    public MainScreen() {
        this.languageObservable = new LanguageObservable();
        logInController = new LogInController();
        mainScreenController = new MainScreenController(logInController, userRepository);
        userRepository = new UserRepository();
        frame = new JFrame("Aplicatie Muzeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        languageObservable.addObserver(this);
        setupUI();
    }


    private void setupUI() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\sirbu\\Desktop\\Cauta\\Faculta 3.2\\PS\\MuseumApp\\FundalMainScreen.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        setupButtons(panel);
        setupComboBox(panel);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }


    private void setupButtons(JPanel panel) {
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 40, 500, 30);
        panel.add(titleLabel);

        promptLabel = new JLabel("", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        promptLabel.setForeground(Color.BLACK);
        promptLabel.setBounds(0, 140, 500, 20);
        panel.add(promptLabel);

        int centerX = 250;
        vizitatorButton = new JButton(" ");
        vizitatorButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        vizitatorButton.setBounds(centerX - 50, 190, 100, 30);
        vizitatorButton.setBackground(new Color(238, 238, 238));
        vizitatorButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        vizitatorButton.addActionListener(e -> {performOpenVisitor();
            frame.dispose();});
        panel.add(vizitatorButton);

        angajatButton = new JButton(" ");
        angajatButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        angajatButton.setBounds(centerX - 50, 230, 100, 30);
        angajatButton.setBackground(new Color(238, 238, 238));
        angajatButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        angajatButton.addActionListener(e -> {
            logInController.setUserType("angajat");
            frame.dispose();
            new LogIn(logInController, userRepository).showScreen();
        });
        panel.add(angajatButton);

        adminButton = new JButton(" ");
        adminButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        adminButton.setBounds(centerX - 50, 270, 100, 30);
        adminButton.setBackground(new Color(238, 238, 238));
        adminButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        adminButton.addActionListener(e -> {
            logInController.setUserType("admin");
            frame.dispose();
            new LogIn(logInController, userRepository).showScreen();
        });
        panel.add(adminButton);

    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(425, 400, 50, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();
    }

    private void performOpenVisitor()
    {
        mainScreenController.OpenVisitor();
    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        vizitatorButton.setText(languageObservable.getLanguageText("MainScreen.button.vizitatorButton"));
        angajatButton.setText(languageObservable.getLanguageText("MainScreen.button.angajatButton"));
        adminButton.setText(languageObservable.getLanguageText("MainScreen.button.adminButton"));
        titleLabel.setText(languageObservable.getLanguageText("MainScreen.label.titleLabel"));
        promptLabel.setText(languageObservable.getLanguageText("MainScreen.label.promptLabel"));
        comboBox1.setToolTipText(languageObservable.getLanguageText("MainScreen.checkbox.checkBox1"));
    }
}
