package View;

import Controller.EmployeeController;
import Languages.LanguageObservable;
import Languages.Observer;
import Repo.ArtWorkRepository;

import javax.swing.*;
import java.awt.*;

public class EmployeeUI extends JFrame implements Observer {
    private EmployeeController employeeController;
    private JList<String> list1;
    private DefaultListModel<String> listModel;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton adaugaButton, stergeButton, actualizeazaButton, cautaButton, generareCsvButton, generareJSONButton;
    private JButton generareXMLButton;
    private JButton generareDocButton;
    private JButton graficeButton;
    private JComboBox comboBox1;
    private JButton vizitatorButton;
    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel, labelTitlu, labelArtist, labelYear, labelType, labelPath;
    private LanguageObservable languageObservable;
    public EmployeeUI() {
        this.languageObservable = new LanguageObservable();
        employeeController = new EmployeeController();
        frame = new JFrame("Servicii Angajat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\sirbu\\Desktop\\Cauta\\Faculta 3.2\\PS\\MuseumApp\\FundalAngajat.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        titleLabel = new JLabel("Servicii Angajat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(150, 10, 500, 30);
        panel.add(titleLabel);
        panel.add(comboBox1);

        initializeComponents(panel);
        setupComboBox(panel);
        performListArtWorks();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void initializeComponents(JPanel panel) {
        listModel = new DefaultListModel<>();
        list1 = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list1);
        scrollPane.setBounds(520, 160, 210, 220);
        panel.add(scrollPane);

        employeeController.setArtworks(listModel);

        int yPosition = 245;
        Color color = new Color(206, 193, 193);

        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        adaugaButton = new JButton("Adaugă");
        stergeButton = new JButton("Șterge");
        actualizeazaButton = new JButton("Actualizează");
        cautaButton = new JButton("Caută");
        generareCsvButton = new JButton("Generare CSV");
        generareJSONButton = new JButton("Generare JSON");
        generareXMLButton = new JButton("Generare XML");
        generareDocButton = new JButton("Generare DOC");
        graficeButton = new JButton("Grafice");
        vizitatorButton = new JButton("Acceseaza ca Vizitator");

        adaugaButton.setBackground(color);
        stergeButton.setBackground(color);
        actualizeazaButton.setBackground(color);
        cautaButton.setBackground(color);
        generareCsvButton.setBackground(color);
        generareJSONButton.setBackground(color);
        generareXMLButton.setBackground(color);
        generareDocButton.setBackground(color);
        graficeButton.setBackground(color);
        vizitatorButton.setBackground(color);

        addLabeledField();

        adaugaButton.setBounds(220, yPosition + 175, 122, 30);
        stergeButton.setBounds(350, yPosition + 175, 122, 30);
        actualizeazaButton.setBounds(480, yPosition + 175, 122, 30);
        cautaButton.setBounds(610, yPosition + 175, 122, 30);
        generareCsvButton.setBounds(220, yPosition + 225, 122, 30);
        generareJSONButton.setBounds(350,yPosition+225, 122, 30);
        generareXMLButton.setBounds(480, yPosition+225, 122, 30);
        generareDocButton.setBounds(610, yPosition+225, 122, 30);
        graficeButton.setBounds(220, yPosition + 275, 254, 30);
        vizitatorButton.setBounds(480, yPosition + 275, 254, 30 );
        panel.add(adaugaButton);
        panel.add(stergeButton);
        panel.add(actualizeazaButton);
        panel.add(cautaButton);
        panel.add(generareCsvButton);
        panel.add(generareJSONButton);
        panel.add(generareXMLButton);
        panel.add(generareDocButton);
        panel.add(graficeButton);
        panel.add(vizitatorButton);

        adaugaButton.addActionListener(e -> performAddArtWork());
        stergeButton.addActionListener(e -> {
            employeeController.setTitle(textField1.getText());
            performDeleteArtWork();});
        actualizeazaButton.addActionListener(e -> {
            employeeController.setTitle(textField1.getText());
            employeeController.setAuthor(textField2.getText());
            employeeController.setYear(Integer.parseInt(textField3.getText().toString()));
            employeeController.setType(textField4.getText());
            employeeController.setImagePath(textField5.getText());
            performUpdateArtWork();
        });
        cautaButton.addActionListener(e -> {
            employeeController.setTitle(textField1.getText());
            performSearchArtWork();});
        generareCsvButton.addActionListener(e -> performGenerateCSVFile());
        generareJSONButton.addActionListener(e -> performGenerateJSONFile());
        generareXMLButton.addActionListener(e -> performGenerateXMLFile());
        generareDocButton.addActionListener(e -> perfomGenerateDOCFile());
        graficeButton.addActionListener(e -> performGraphics());
        vizitatorButton.addActionListener(e -> openVisitor());
    }

    private void addLabeledField() {
        labelTitlu = new JLabel();
        labelTitlu.setBounds(233, 245-40, 50, 30);
        textField1.setBounds(233 + 60, 245 - 40, 150, 30);
        panel.add(labelTitlu);
        panel.add(textField1);

        labelArtist = new JLabel();
        labelArtist.setBounds(233, 245, 50, 30);
        textField2.setBounds(233 + 60, 245, 150, 30);
        panel.add(labelArtist);
        panel.add(textField2);

        labelYear = new JLabel();
        labelYear.setBounds(233, 245 + 40, 50, 30);
        textField3.setBounds(233 + 60, 245 + 40, 150, 30);
        panel.add(labelYear);
        panel.add(textField3);

        labelType = new JLabel();
        labelType.setBounds(233, 245 + 80, 50, 30);
        textField4.setBounds(233 + 60, 245 + 80, 150, 30);
        panel.add(labelType);
        panel.add(textField4);

        labelPath = new JLabel();
        labelPath.setBounds(233, 245 + 120, 50, 30);
        textField5.setBounds(233 + 60, 245 + 120, 150, 30);
        panel.add(labelPath);
        panel.add(textField5);
    }

    private void performAddArtWork()
    {
        employeeController.setTitle(textField1.getText().trim());
        employeeController.setAuthor(textField2.getText().trim());
        employeeController.setYear(Integer.parseInt(textField3.getText().trim()));
        employeeController.setType(textField4.getText().trim());
        employeeController.setImagePath(textField5.getText().trim());
        employeeController.AddArtWorkClicked();
    }

    private void performDeleteArtWork()
    {
        employeeController.getTitle();
        employeeController.DeleteArtWorkClicked();
    }

    private void performUpdateArtWork()
    {
        employeeController.getTitle();
        employeeController.UpdateArtWorkClicked();
    }

    private void performSearchArtWork()
    {
        employeeController.getTitle();
        employeeController.SearchArtWorkClicked();
    }

    private void performGenerateCSVFile()
    {
        employeeController.GenerateCSVFile();
    }

    private void performGenerateJSONFile()
    {
        employeeController.GenerateJSONFile();
    }

    private void performGenerateXMLFile()
    {
        employeeController.GenerateXMLFile();
    }

    private void perfomGenerateDOCFile()
    {
        employeeController.GenerateDOCFile();
    }

    private void performGraphics()
    {
        ArtWorkRepository artWorkRepository = new ArtWorkRepository();
        ArtworkStatisticsRadialView artworkStatisticsView = new ArtworkStatisticsRadialView(artWorkRepository);
        ArtworkStatisticsLinearView artworkStatisticsLinearView = new ArtworkStatisticsLinearView(artWorkRepository);
        ArtworkStatisticsInelarView artworkStatisticsInelarView = new ArtworkStatisticsInelarView(artWorkRepository);
    }
    private void performListArtWorks()
    {
        employeeController.getArtworks();
        employeeController.getAuthor();
        employeeController.getTitle();
        employeeController.getYear();
        employeeController.getType();
        employeeController.ListArtWorks();
    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(740, 245 + 275, 45, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();
    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage();
    }

    private void openVisitor()
    {
        ArtWorkListView artWorkListView = new ArtWorkListView(true);
    }

    @Override
    public void updateLanguage() {
        adaugaButton.setText(languageObservable.getLanguageText("EmployeeUI.button.adaugaButton"));
        stergeButton.setText(languageObservable.getLanguageText("EmployeeUI.button.stergeButton"));
        actualizeazaButton.setText(languageObservable.getLanguageText("EmployeeUI.button.actualizeazaButton"));
        cautaButton.setText(languageObservable.getLanguageText("EmployeeUI.button.cautaButton"));
        generareCsvButton.setText(languageObservable.getLanguageText("EmployeeUI.button.generareCsvButton"));
        generareJSONButton.setText(languageObservable.getLanguageText("EmployeeUI.button.generareJSONButton"));
        generareXMLButton.setText(languageObservable.getLanguageText("EmployeeUI.button.generareXMLButton"));
        generareDocButton.setText(languageObservable.getLanguageText("EmployeeUI.button.generareDocButton"));
        graficeButton.setText(languageObservable.getLanguageText("EmployeeUI.button.graficeButton"));
        vizitatorButton.setText(languageObservable.getLanguageText("EmployeeUI.button.vizitatorButton"));
        titleLabel.setText(languageObservable.getLanguageText("EmployeeUI.label.titleLabel"));
        labelTitlu.setText(languageObservable.getLanguageText("EmployeeUI.label.labelTitlu"));
        labelArtist.setText(languageObservable.getLanguageText("EmployeeUI.label.labelArtist"));
        labelYear.setText(languageObservable.getLanguageText("EmployeeUI.label.labelYear"));
        labelType.setText(languageObservable.getLanguageText("EmployeeUI.label.labelType"));
        labelPath.setText(languageObservable.getLanguageText("EmployeeUI.label.labelPath"));
    }

}