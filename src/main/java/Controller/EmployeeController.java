package Controller;

import Model.ArtWork;
import Repo.ArtWorkRepository;
import Repo.DAOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;


public class EmployeeController {
    private ArtWorkRepository artWorkRepository;
    private String title;
    private String author;
    private Integer year;
    private String type;
    private String imagePath;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> list;

    public EmployeeController() {
        this.artWorkRepository = new ArtWorkRepository();
    }
    public void AddArtWorkClicked()
    {
        ArtWork newArtWork = new ArtWork(getTitle(), getAuthor(), getYear(), getType(), getImagePath());
        try {
            artWorkRepository.addArtwork(newArtWork);
            System.out.println("Opera de artă a fost adăugată cu succes.");
            ListArtWorks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Trebuie completate toate spatiile", "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void DeleteArtWorkClicked()
    {
        String titleToDelete = getTitle().trim();
        if (titleToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vă rugăm introduceți titlul operei de artă pentru ștergere.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(null, "Sunteți sigur că doriți să ștergeți opera de artă cu titlul: '" + titleToDelete + "'?", "Confirmare ștergere", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try {
                artWorkRepository.deleteArtwork(titleToDelete);
                JOptionPane.showMessageDialog(null, "Opera de artă a fost ștearsă cu succes.", "Ștergere Reușită", JOptionPane.INFORMATION_MESSAGE);
                ListArtWorks();
            } catch (DAOException e) {
                JOptionPane.showMessageDialog(null, "Eroare la ștergerea operei de artă: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void UpdateArtWorkClicked()
    {
        String title = getTitle().trim();
        String artist = getAuthor().trim();
        int year = Integer.parseInt(getYear().toString());
        String type = getType().trim();
        String imagePath = getImagePath().trim();

        ArtWork artworkToBeUpdated;
        try {
            artworkToBeUpdated = artWorkRepository.getArtworkByName(title);

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la cautarea operei de arta.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (artworkToBeUpdated != null) {
            artworkToBeUpdated.setArtist(artist);
            artworkToBeUpdated.setYear(year);
            artworkToBeUpdated.setType(type);
            artworkToBeUpdated.setImagePath(imagePath);

            try {
                artWorkRepository.updateArtwork(artworkToBeUpdated);
                JOptionPane.showMessageDialog(null, "Opera de arta a fost actualizata cu succes", "Succes", JOptionPane.INFORMATION_MESSAGE);
                ListArtWorks();
            } catch (DAOException e) {
                JOptionPane.showMessageDialog(null, "Eroare la actualizare.", "Eroare", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Opera de arta nu a fost gasita.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public void SearchArtWorkClicked()
    {
        String searchTitle = getTitle().trim();
        if (searchTitle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trebuie completat titlul!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            ArtWork searchedArtwork = artWorkRepository.getArtworkByName(searchTitle);
            if (searchedArtwork == null) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit titlul.", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String details = "Titlu: " + searchedArtwork.getTitle() +
                        "\nArtist: " + searchedArtwork.getArtist() +
                        "\nAn: " + searchedArtwork.getYear() +
                        "\nTip: " + searchedArtwork.getType();
                System.out.println(details);
                JOptionPane.showMessageDialog(null, details, "Detalii Opera de Artă", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DAOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void GenerateCSVFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvează ca...");
        fileChooser.setSelectedFile(new File("opere_arta.csv"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new File(fileToSave + ".csv");
            }
            try (PrintWriter pw = new PrintWriter(fileToSave)) {
                List<ArtWork> artworks = artWorkRepository.getAllArtworks();
                pw.println("ID,Titlu,Artist,An,Tip");
                for (ArtWork artwork : artworks) {
                    pw.println(artwork.getId() + "," + artwork.getTitle() + "," + artwork.getArtist() + "," + artwork.getYear() + "," + artwork.getType());
                }
            } catch (DAOException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void GenerateJSONFile()
    {
        ArtWorkRepository artWorkRepository = new ArtWorkRepository();
        List<ArtWork> artworks = null;
        try {
            artworks = artWorkRepository.getAllArtworks();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        for (int i = 0; i < artworks.size(); i++) {
            ArtWork art = artworks.get(i);
            jsonBuilder.append("  {\n");
            jsonBuilder.append(String.format("    \"title\": \"%s\",\n", art.getTitle()));
            jsonBuilder.append(String.format("    \"artist\": \"%s\",\n", art.getArtist()));
            jsonBuilder.append(String.format("    \"year\": %d,\n", art.getYear()));
            jsonBuilder.append(String.format("    \"type\": \"%s\"\n", art.getType()));
            jsonBuilder.append(i < artworks.size() - 1 ? "  },\n" : "  }\n");
        }
        jsonBuilder.append("]");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save JSON File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
        fileChooser.setSelectedFile(new File("artworks.json"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getPath().toLowerCase().endsWith(".json")) {
                fileToSave = new File(fileToSave + ".json");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(jsonBuilder.toString());
                writer.flush();
                JOptionPane.showMessageDialog(null, "JSON file has been saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing JSON file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void GenerateXMLFile()
    {
        ArtWorkRepository artWorkRepository = new ArtWorkRepository();
        List<ArtWork> artworks = null;
        try {
            artworks = artWorkRepository.getAllArtworks();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("Artworks");
            doc.appendChild(rootElement);

            for (ArtWork art : artworks) {
                Element artwork = doc.createElement("ArtWork");
                rootElement.appendChild(artwork);

                Element title = doc.createElement("Title");
                title.appendChild(doc.createTextNode(art.getTitle()));
                artwork.appendChild(title);

                Element artist = doc.createElement("Artist");
                artist.appendChild(doc.createTextNode(art.getArtist()));
                artwork.appendChild(artist);

                Element year = doc.createElement("Year");
                year.appendChild(doc.createTextNode(String.valueOf(art.getYear())));
                artwork.appendChild(year);

                Element type = doc.createElement("Type");
                type.appendChild(doc.createTextNode(art.getType()));
                artwork.appendChild(type);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save XML File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
            fileChooser.setSelectedFile(new File("artworks.xml"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getPath().toLowerCase().endsWith(".xml")) {
                    fileToSave = new File(fileToSave + ".xml");
                }
                StreamResult result = new StreamResult(fileToSave);

                transformer.transform(source, result);
                JOptionPane.showMessageDialog(null, "XML file has been saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ParserConfigurationException | TransformerException e) {
            JOptionPane.showMessageDialog(null, "Error writing XML file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void GenerateDOCFile()
    {
        ArtWorkRepository artWorkRepository = new ArtWorkRepository();
        List<ArtWork> artworks = null;
        try {
            artworks = artWorkRepository.getAllArtworks();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as Simple Word Document");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Word Files", "doc"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getPath().toLowerCase().endsWith(".doc")) {
                fileToSave = new File(fileToSave.getPath() + ".doc");
            }

            try (FileWriter writer = new FileWriter(fileToSave)) {
                for (ArtWork art : artworks) {
                    writer.write("Titlu: " + art.getTitle() + "\n");
                    writer.write("Artist: " + art.getArtist() + "\n");
                    writer.write("An: " + art.getYear() + "\n");
                    writer.write("Tip: " + art.getType() + "\n");
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(null, "Documentul a fost salvat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Eroare la scrierea în fișier: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void ListArtWorks()
    {
        try {
            List<ArtWork> artworks = artWorkRepository.getAllArtworks();
            SwingUtilities.invokeLater(() -> {
                DefaultListModel<String> listModel = getListModel();
                listModel.clear();
                for (ArtWork artwork : artworks) {
                    listModel.addElement(artwork.toString());
                    System.out.println(artwork);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }
    public JList<String> getArtworks()
    {
        return list;
    }
    public void setArtworks(DefaultListModel<String> list)
    {
        this.listModel = list;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

}
