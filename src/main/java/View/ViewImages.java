package View;

import Languages.LanguageObservable;
import Languages.Observer;
import Model.ArtWork;
import Repo.ArtWorkRepository;
import Repo.DAOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewImages extends JFrame implements Observer{
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JButton nextButton;
    private JButton backButton;
    private JComboBox comboBox1;
    private ImageIcon[] images;
    private int currentIndex = 0;
    private ArtWork[] artWorks;
    private ArtWorkRepository artWorkRepository;
    private LanguageObservable languageObservable;

    public ViewImages()
    {
        this.languageObservable = new LanguageObservable();
        artWorkRepository = new ArtWorkRepository();

        try {
            loadArtWorks();
        } catch (DAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la încărcarea operelor de artă: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            return;  // Opriți inițializarea dacă datele nu pot fi încărcate
        }
        setTitle("Galeria de Artă");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        imageLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        updateImage();

        nextButton = new JButton("Următoarea");
        backButton = new JButton("Precedenta");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < artWorks.length - 1) {
                    currentIndex++;
                    updateArtWorkDisplay();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateArtWorkDisplay();
                }
            }
        });

        add(imageLabel, BorderLayout.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        setupComboBox(buttonPanel);
        //add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
        updateArtWorkDisplay();
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

    private void notifyLanguageChange() {
        String selectedLanguage = (String) comboBox1.getSelectedItem();
        languageObservable.setLanguage(selectedLanguage);
        updateLanguage(); // Asigură-te că updateLanguage este apelat când schimbi limba
    }

    private void updateImage() {
        if (images != null && images.length > 0) {
            imageLabel.setIcon(images[currentIndex]);
            backButton.setEnabled(currentIndex > 0);
            nextButton.setEnabled(currentIndex < images.length - 1);
        }
    }

    private void loadArtWorks() throws DAOException {
        List<ArtWork> artWorkList = artWorkRepository.getAllArtworks();
        artWorks = artWorkList.toArray(new ArtWork[0]);
        System.out.println("Loaded " + artWorks.length + " artworks.");
    }

    private void updateArtWorkDisplay() {
        if (artWorks != null && artWorks.length > 0) {
            ArtWork currentArtWork = artWorks[currentIndex];
            ImageIcon icon = new ImageIcon(currentArtWork.getImagePath());
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("Failed to load image: " + currentArtWork.getImagePath());
            }
            imageLabel.setIcon(icon);
            titleLabel.setText(currentArtWork.getTitle());
            backButton.setEnabled(currentIndex > 0);
            nextButton.setEnabled(currentIndex < artWorks.length - 1);
        }
    }

    @Override
    public void updateLanguage() {
        nextButton.setText(languageObservable.getLanguageText("ViewImages.button.nextButton"));
        backButton.setText(languageObservable.getLanguageText("ViewImages.button.backButton"));
    }
}
