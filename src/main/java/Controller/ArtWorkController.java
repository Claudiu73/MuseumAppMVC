package Controller;


import Model.ArtWork;
import Repo.ArtWorkRepository;
import Repo.DAOException;
import View.ViewImages;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class ArtWorkController {

    private ArtWorkRepository artWorkRepository;
    private String title;
    private String author;
    private Integer year;
    private String type;
    private DefaultListModel<String> listModelArts;
    private ArtWork artWork;
    private String imagePath;

    public ArtWorkController() {
        this.artWorkRepository = new ArtWorkRepository();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DefaultListModel<String> getListModelArts() {
        return listModelArts;
    }

    public void setListModelArts(DefaultListModel<String> listModelArts) {
        this.listModelArts = listModelArts;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void SearchArtWorkByVisitor()
    {
        String searchTitle = getTitle().trim();
        if (searchTitle.isEmpty()) {
            setTitle("Trebuie completat titlul operei de arta!");
            return;
        }
        try {
            ArtWork searchedArtwork = artWorkRepository.getArtworkByName(searchTitle);
            if (searchedArtwork == null) {
                JOptionPane.showMessageDialog(null, "Eroare la cautarea acestei opere de arta.", "Eroare", JOptionPane.ERROR_MESSAGE);

            } else {
                String details = "Titlu: " + searchedArtwork.getTitle() +
                        "\nArtist: " + searchedArtwork.getArtist() +
                        "\nAn: " + searchedArtwork.getYear() +
                        "\nTip: " + searchedArtwork.getType();
                System.out.println(artWork.getImagePath());
                JOptionPane.showMessageDialog(null, details, "Detalii Opera de Artă", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la cautarea acestei opere de arta.", "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void ListArtWorksForVisitor() {
        try {
            List<ArtWork> artworks = artWorkRepository.getAllArtworks();
            listModelArts.clear(); // Asigură-te că modelul este golit înainte de a adăuga noi elemente
            for (ArtWork art : artworks) {
                listModelArts.addElement(art.toString());
            }
            System.out.println("ListArt populated with " + listModelArts.getSize() + " artworks.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eroare la încărcarea operelor de artă: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    public void ToFilterTitleForVisitor()
    {
        try {
            List<ArtWork> allArtworks = artWorkRepository.getAllArtworks();
            DefaultListModel<String> model = new DefaultListModel<>();

            String titleFilter = getTitle().trim().toLowerCase();

            List<ArtWork> filteredArtworks = allArtworks.stream()
                    .filter(artwork -> artwork.getTitle().toLowerCase().contains(titleFilter) || titleFilter.isEmpty())
                    .collect(Collectors.toList());

            for (ArtWork artwork : filteredArtworks) {
                model.addElement(artwork.toString());
                System.out.println(model);
            }

            setListModelArts(model);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea operelor de artă: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void ToFilterAuthorForVisitor()
    {
        try {
            List<ArtWork> allArtworks = artWorkRepository.getAllArtworks();
            DefaultListModel<String> model = new DefaultListModel<>();

            String authorFilter = getAuthor().trim().toLowerCase();

            List<ArtWork> filteredArtworks = allArtworks.stream()
                    .filter(artwork -> artwork.getArtist().toLowerCase().contains(authorFilter) || authorFilter.isEmpty())
                    .collect(Collectors.toList());

            for (ArtWork artwork : filteredArtworks) {
                model.addElement(artwork.toString());
            }
            setListModelArts(model);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea operelor de artă după autor: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void ToFilterYearForVisitor()
    {
        try {
            List<ArtWork> allArtworks = artWorkRepository.getAllArtworks();
            DefaultListModel<String> model = new DefaultListModel<>();

            String yearFilter = String.valueOf(getYear()).trim();

            List<ArtWork> filteredArtworks = allArtworks.stream()
                    .filter(artwork -> String.valueOf(artwork.getYear()).equals(yearFilter) || yearFilter.isEmpty())
                    .collect(Collectors.toList());

            for (ArtWork artwork : filteredArtworks) {
                model.addElement(artwork.toString());
            }

            setListModelArts(model);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea operelor de artă după an: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void ToFilterTypeForVisitor()
    {
        try {
            List<ArtWork> allArtworks = artWorkRepository.getAllArtworks();
            DefaultListModel<String> model = new DefaultListModel<>();

            String typeFilter = getType().trim().toLowerCase();

            List<ArtWork> filteredArtworks = allArtworks.stream()
                    .filter(artwork -> artwork.getType().toLowerCase().contains(typeFilter) || typeFilter.isEmpty())
                    .collect(Collectors.toList());

            for (ArtWork artwork : filteredArtworks) {
                model.addElement(artwork.toString());
            }

            setListModelArts(model);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea operelor de artă după tip: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void OpenImagesForVisitor() {
        SwingUtilities.invokeLater(() -> {
            ViewImages viewImages = new ViewImages();
            viewImages.setVisible(true);
        });
    }

}
