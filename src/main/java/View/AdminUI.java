package View;

import Controller.AdminController;
import Languages.LanguageObservable;
import Languages.Observer;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame implements Observer {
    private JList<String> list1;
    private JList<String> list2;
    private JTextField textField1, textField2, textField3;
    private JButton adaugaButton, stergeButton, cautaButton, actualizeazaButton, filtruOpereButton;
    private JButton filtrareUsersButton;
    private JComboBox comboBox1;
    private DefaultListModel<String> listModel, listModel1;
    private AdminController adminController;
    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel, UNLabel, PWLabel, UTLabel;
    private LanguageObservable languageObservable;

    public AdminUI() {
        this.languageObservable = new LanguageObservable();
        adminController = new AdminController();
        listModel = new DefaultListModel<>();
        listModel1 = new DefaultListModel<>();
        frame = new JFrame("Interfață Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        createAndShowUI();
        adminController.setListModelArt(listModel);
        adminController.setListModelUser(listModel1);
        performListArtWorks();
        performListUsers();
    }

    private void createAndShowUI() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\sirbu\\Desktop\\Cauta\\Faculta 3.2\\PS\\MuseumApp\\FundalAdmin.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        titleLabel = new JLabel("Servicii Admin", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(150, 10, 500, 30);
        panel.add(titleLabel);

        UNLabel = new JLabel("UN: ", SwingConstants.HORIZONTAL);
        UNLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        UNLabel.setForeground(Color.BLACK);
        UNLabel.setBounds(65,135, 500, 30);
        panel.add(UNLabel);

        PWLabel = new JLabel("PW: ", SwingConstants.HORIZONTAL);
        PWLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        PWLabel.setForeground(Color.BLACK);
        PWLabel.setBounds(65,170, 500, 30);
        panel.add(PWLabel);

        UTLabel = new JLabel("UT: ", SwingConstants.HORIZONTAL);
        UTLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        UTLabel.setForeground(Color.BLACK);
        UTLabel.setBounds(65,205, 500, 30);
        panel.add(UTLabel);

        panel.add(comboBox1);

        setupComboBox(panel);
        initializeUI();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void initializeUI() {
        list1 = new JList<>(listModel);
        JScrollPane scrollPaneArtworks = new JScrollPane(list1);
        scrollPaneArtworks.setBounds(20, 165, 250, 200);

        list2 = new JList<>(listModel1);
        JScrollPane scrollPaneUsers = new JScrollPane(list2);
        scrollPaneUsers.setBounds(520, 165, 250, 200);

        textField1 = new JTextField();
        textField1.setBounds(330, 135, 150, 25);
        textField2 = new JTextField();
        textField2.setBounds(330, 170, 150, 25);
        textField3 = new JTextField();
        textField3.setBounds(330, 205, 150, 25);

        Color orangeBrown = new Color(238, 155, 69);

        adaugaButton = new JButton("Adaugă");
        adaugaButton.setBounds(300, 275, 90, 25);
        adaugaButton.setBackground(orangeBrown);

        stergeButton = new JButton("Șterge");
        stergeButton.setBounds(400, 275, 90, 25);
        stergeButton.setBackground(orangeBrown);

        cautaButton = new JButton("Caută User");
        cautaButton.setBounds(300, 310, 90, 25);
        cautaButton.setBackground(orangeBrown);

        actualizeazaButton = new JButton("Actualizează");
        actualizeazaButton.setBounds(400, 310, 90, 25);
        actualizeazaButton.setBackground(orangeBrown);

        filtruOpereButton = new JButton("Filtrare Lista Opere");
        filtruOpereButton.setBounds(300, 345, 190, 25);
        filtruOpereButton.setBackground(orangeBrown);

        filtrareUsersButton = new JButton("Filtrare Lista Users");
        filtrareUsersButton.setBounds(300, 380, 190, 25);
        filtrareUsersButton.setBackground(orangeBrown);

        panel.add(scrollPaneArtworks);
        panel.add(scrollPaneUsers);
        panel.add(textField1);
        panel.add(textField2);
        panel.add(textField3);
        panel.add(adaugaButton);
        panel.add(stergeButton);
        panel.add(cautaButton);
        panel.add(actualizeazaButton);
        panel.add(filtruOpereButton);
        panel.add(filtrareUsersButton);

        addEventHandlers();
    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(725, 500, 50, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();  // Asigură că limbajul inițial este setat corect după ce comboBox-ul a fost adăugat la panel
    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage(); // Asigură-te că updateLanguage este apelat când schimbi limba
    }


    private void addEventHandlers() {
        adaugaButton.addActionListener(e -> {
            performAddUser();
        });
        stergeButton.addActionListener(e -> {
            adminController.setUsername(textField1.getText());
            performDeleteUser();
        });
        cautaButton.addActionListener(e -> {
            adminController.setUsername(textField1.getText());
            performSearchUser();});
        actualizeazaButton.addActionListener(e -> {
            adminController.setUsername(textField1.getText());
            adminController.setPassword(textField2.getText());
            adminController.setUserType(textField3.getText());
            performUpdateUser();});
        filtruOpereButton.addActionListener(e -> performOpenToFilterListOfArtWorks());
        filtrareUsersButton.addActionListener(e -> performOpenToFilterUsers());
    }


    private void performAddUser()
    {
        adminController.setUsername(textField1.getText().trim());
        adminController.setPassword(textField2.getText().trim());
        adminController.setUserType(textField3.getText().trim());
        adminController.AddUser();
    }

    private void performDeleteUser()
    {
        adminController.getUsername();
        adminController.DeleteUser();
    }

    private void performSearchUser()
    {
        adminController.SearchUser();
    }

    private void performUpdateUser()
    {
        adminController.UpdateUser();
    }

    private void performListArtWorks()
    {
        adminController.ListArtWorks();
    }

    private void performListUsers()
    {
        adminController.ListUsers();
    }

    private void performOpenToFilterUsers()
    {
        adminController.OpenToFilterUsers();
    }
    private void performOpenToFilterListOfArtWorks()
    {
        adminController.OpenToFilterListOfArtWorks();
    }

    @Override
    public void updateLanguage() {
        adaugaButton.setText(languageObservable.getLanguageText("AdminUI.button.adaugaButton"));
        stergeButton.setText(languageObservable.getLanguageText("AdminUI.button.stergeButton"));
        cautaButton.setText(languageObservable.getLanguageText("AdminUI.button.cautaButton"));
        actualizeazaButton.setText((languageObservable.getLanguageText("AdminUI.button.actualizeazaButton")));
        filtruOpereButton.setText(languageObservable.getLanguageText("AdminUI.button.filtruOpereButton"));
        filtrareUsersButton.setText(languageObservable.getLanguageText("AdminUI.button.filtrareUsersButton"));
        titleLabel.setText(languageObservable.getLanguageText("AdminUI.label.titleLabel"));
        UNLabel.setText(languageObservable.getLanguageText("AdminUI.label.UNLabel"));
        PWLabel.setText(languageObservable.getLanguageText("AdminUI.label.PWLabel"));
        UTLabel.setText(languageObservable.getLanguageText("AdminUI.label.UTLabel"));
    }

}