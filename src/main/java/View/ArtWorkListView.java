package View;

import Controller.ArtWorkController;
import Languages.LanguageObservable;
import Languages.Observer;

import javax.swing.*;
import java.awt.*;

public class ArtWorkListView extends JFrame implements Observer {
    private JList<String> list1;
    private DefaultListModel<String> listModel;
    private JTextField textField1, textField2, textField3, textField4;
    private JButton anButton, titluButton, tipButton, artistButton, cautaButton;
    private JButton vizualizareImaginiButton;
    private JComboBox comboBox1;
    private JFrame frame;
    private JPanel panel;
    private ArtWorkController artWorkController;
    private LanguageObservable languageObservable;
    private JLabel titleLabel, labelTitleFilter, labelYearFilter, labelAuthorFilter, labelTypeFilter;

    public ArtWorkListView(boolean isEmployee) {
        this.languageObservable = new LanguageObservable();
        artWorkController = new ArtWorkController();
        listModel = new DefaultListModel<>();
        frame = new JFrame("Lista Opere de Arta");
        if(isEmployee == false )frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            else frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\sirbu\\Desktop\\Cauta\\Faculta 3.2\\PS\\MuseumApp\\FundalVizitator.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        frame.setContentPane(panel);
        initializeUI();
        performListArtWorksForVisitor();

        frame.setVisible(true);
    }
    private void initializeUI() {
        frame.setLayout(null);

        listModel = new DefaultListModel<>();
        list1 = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list1);
        artWorkController.setListModelArts(listModel);
        scrollPane.setBounds(20, 200, 350, 150);
        frame.add(scrollPane);

        setupLabels();
        setupComponents();
        setupComboBox(panel);
    }

    private void setupLabels() {
        titleLabel = new JLabel(" ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(150, 10, 500, 30);

        labelTitleFilter = new JLabel(" ");
        labelTitleFilter.setForeground(Color.WHITE);
        labelTitleFilter.setBounds(400, 205, 100, 25);

        labelYearFilter = new JLabel(" ");
        labelYearFilter.setForeground(Color.WHITE);
        labelYearFilter.setBounds(400, 235, 100, 25);

        labelAuthorFilter = new JLabel(" ");
        labelAuthorFilter.setForeground(Color.WHITE);
        labelAuthorFilter.setBounds(400, 275, 100, 25);

        labelTypeFilter = new JLabel(" ");
        labelTypeFilter.setForeground(Color.WHITE);
        labelTypeFilter.setBounds(400, 305, 100, 25);

        panel.add(titleLabel);
        frame.add(labelTitleFilter);
        frame.add(labelYearFilter);
        frame.add(labelAuthorFilter);
        frame.add(labelTypeFilter);

    }

    private void setupComboBox(JPanel panel) {
        comboBox1 = new JComboBox<>(new String[]{"RO", "EN", "FR", "DE"});
        comboBox1.setBounds(725, 500, 50, 30);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(e -> {
            notifyLanguageChange();
        });
        panel.add(comboBox1);
        notifyLanguageChange();
    }

    private void setupComponents() {
        textField1 = new JTextField();
        textField1.setBounds(475, 205, 150, 25);

        titluButton = new JButton(" ");
        titluButton.setBackground(Color.LIGHT_GRAY);
        titluButton.setBounds(635, 205, 80, 25);

        textField2 = new JTextField();
        textField2.setBounds(475, 235, 150, 25);

        anButton = new JButton(" ");
        anButton.setBackground(Color.LIGHT_GRAY);
        anButton.setBounds(635, 235, 80, 25);

        textField3 = new JTextField();
        textField3.setBounds(475, 275, 150, 25);

        artistButton = new JButton(" ");
        artistButton.setBackground(Color.LIGHT_GRAY);
        artistButton.setBounds(635, 275, 80, 25);

        cautaButton = new JButton(" ");
        cautaButton.setBackground(Color.LIGHT_GRAY);
        cautaButton.setBounds(300, 365, 200, 25);

        vizualizareImaginiButton = new JButton(" ");
        vizualizareImaginiButton.setBackground(Color.LIGHT_GRAY);
        vizualizareImaginiButton.setBounds(300, 395, 200, 25);

        textField4 = new JTextField();
        textField4.setBounds(475, 305, 150, 25);

        tipButton = new JButton(" ");
        tipButton.setBackground(Color.LIGHT_GRAY);
        tipButton.setBounds(635, 305, 80, 25);


        frame.add(textField1);
        frame.add(titluButton);
        frame.add(textField2);
        frame.add(anButton);
        frame.add(textField3);
        frame.add(artistButton);
        frame.add(textField4);
        frame.add(tipButton);
        frame.add(cautaButton);
        frame.add(vizualizareImaginiButton);

        addEventHandlers();
    }
    private void addEventHandlers() {
        titluButton.addActionListener(e -> {
            artWorkController.setTitle(textField1.getText());
            performToFilterTitleForVisitor();});
        anButton.addActionListener(e -> {
            artWorkController.setAuthor(textField2.getText());
            performToFilterAuthorForVisitor();});
        artistButton.addActionListener(e -> {
            artWorkController.setYear(Integer.parseInt(textField3.getText().trim()));
            performToFilterYearForVisitor();});
        tipButton.addActionListener(e -> {
            artWorkController.setType(textField4.getText());
            performToFilterTypeForVisitor();});
        cautaButton.addActionListener(e -> {
            artWorkController.setTitle(textField1.getText());
            performSearchArtWorkByVisitor();
        });
        vizualizareImaginiButton.addActionListener(e -> {
            openImages();
        });
    }

    private void performSearchArtWorkByVisitor()
    {
        artWorkController.SearchArtWorkByVisitor();
    }

    private void performListArtWorksForVisitor()
    {
        list1.setModel(listModel);
        artWorkController.ListArtWorksForVisitor();
    }

    private void performToFilterTitleForVisitor() {
        artWorkController.setTitle(textField1.getText());
        artWorkController.ToFilterTitleForVisitor();
        list1.setModel(artWorkController.getListModelArts());
    }

    private void performToFilterAuthorForVisitor()
    {
        artWorkController.setAuthor(textField2.getText());
        artWorkController.ToFilterAuthorForVisitor();
        list1.setModel(artWorkController.getListModelArts());
    }


    private void performToFilterYearForVisitor()
    {
        artWorkController.setYear(Integer.parseInt(textField3.getText()));
        artWorkController.ToFilterYearForVisitor();
        list1.setModel(artWorkController.getListModelArts());
    }

    private void performToFilterTypeForVisitor()
    {
        artWorkController.setType(textField4.getText());
        artWorkController.ToFilterTypeForVisitor();
        list1.setModel(artWorkController.getListModelArts());
    }

    private void openImages()
    {
        artWorkController.OpenImagesForVisitor();
    }

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage(); // Asigură-te că updateLanguage este apelat când schimbi limba
    }

    @Override
    public void updateLanguage() {
        anButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.anButton"));
        titluButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.titluButton"));
        tipButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.tipButton"));
        artistButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.artistButton"));
        cautaButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.cautaButton"));
        vizualizareImaginiButton.setText(languageObservable.getLanguageText("ArtWorkListView.button.vizualizareImaginiButton"));
        titleLabel.setText(languageObservable.getLanguageText("ArtWorkListView.label.titleLabel"));
        labelTitleFilter.setText(languageObservable.getLanguageText("ArtWorkListView.label.labelTitleFilter"));
        labelYearFilter.setText(languageObservable.getLanguageText("ArtWorkListView.label.labelYearFilter"));
        labelAuthorFilter.setText(languageObservable.getLanguageText("ArtWorkListView.label.labelAuthorFilter"));
        labelTypeFilter.setText(languageObservable.getLanguageText("ArtWorkListView.label.labelTypeFilter"));
    }
}
