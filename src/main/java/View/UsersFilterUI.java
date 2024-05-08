package View;

import Controller.UsersFilterController;
import Languages.LanguageObservable;
import Languages.Observer;

import javax.swing.*;
import java.awt.*;


public class UsersFilterUI extends JFrame implements Observer{
    private JList<String> list1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JComboBox comboBox1;
    private UsersFilterController usersFilterController;
    private DefaultListModel<String> listModel;
    private LanguageObservable languageObservable;

    public UsersFilterUI() {
        this.languageObservable = new LanguageObservable();
        usersFilterController = new UsersFilterController();
        listModel = new DefaultListModel<>();
        initializeUI();
        performLisToBeFiltered();
        this.setVisible(true);

    }

    private void initializeUI() {
        this.setTitle("Filtrare Utilizatori");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        list1 = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list1);
        usersFilterController.setListModelUser(listModel);
        this.add(scrollPane, BorderLayout.CENTER);
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        button1 = new JButton("Username");
        button2 = new JButton("Password");
        button3 = new JButton("Type");


        this.add(new JScrollPane(list1), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(textField1);
        panel.add(button1);
        button1.addActionListener(e -> {
            usersFilterController.setUsername(textField1.getText());
            performToFilterUsernameForAdmin();});
        panel.add(textField2);
        panel.add(button2);
        button2.addActionListener(e -> {
                    usersFilterController.setPassword(textField2.getText());
            performToFilterPasswordForAdmin();});
        panel.add(textField3);
        panel.add(button3);
        button3.addActionListener(e -> {
            usersFilterController.setUserType(textField3.getText());
            performToFilterUserTypeForAdmin();});

        setupComboBox(panel);
        panel.add(comboBox1);

        this.add(panel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(425, 400, 50, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();  // Asigură că limbajul inițial este setat corect după ce comboBox-ul a fost adăugat la panel
    }

    private void performLisToBeFiltered()
    {
        usersFilterController.ListUsersToBeFiltered();
    }

    private void performToFilterUsernameForAdmin()
    {
        usersFilterController.setUsername(textField1.getText());
        usersFilterController.ToFilterUsernameForAdmin();
        list1.setModel(usersFilterController.getListModelUser());
    }

    private void performToFilterPasswordForAdmin()
    {
        usersFilterController.setPassword(textField2.getText());
        usersFilterController.ToFilterPasswordForAdmin();
        list1.setModel(usersFilterController.getListModelUser());
    }

    private void performToFilterUserTypeForAdmin()
    {
        usersFilterController.setUserType(textField3.getText());
        usersFilterController.ToFilterUserTypeForAdmin();
        list1.setModel(usersFilterController.getListModelUser());

    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        button1.setText(languageObservable.getLanguageText("UsersFilterUI.button.button1"));
        button2.setText(languageObservable.getLanguageText("UsersFilterUI.button.button2"));
        button3.setText(languageObservable.getLanguageText("UsersFilterUI.button.button3"));
    }
}
