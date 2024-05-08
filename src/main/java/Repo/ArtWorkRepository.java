package Repo;

import Model.ArtWork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtWorkRepository implements IArtWork{

    @Override
    public void addArtwork(ArtWork artwork) throws DAOException {
        String sql = "INSERT INTO artworks (title, artist, year, type, imagePath) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, artwork.getTitle());
            statement.setString(2, artwork.getArtist());
            statement.setInt(3, artwork.getYear());
            statement.setString(4, artwork.getType());
            statement.setString(5, artwork.getImagePath());
            statement.executeUpdate();
            System.out.println("Opera de arta adaugata cu succes.");        }
        catch (SQLException e) {
            throw new DAOException("Error adding artwork", e);
        }

    }

    @Override
    public ArtWork getArtworkByName(String name) throws DAOException {
        String sql = "SELECT * FROM artworks WHERE title = ?";
        ArtWork artwork = null;

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = ((ResultSet) resultSet).getInt("id");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                int year = resultSet.getInt("year");
                String type = resultSet.getString("type");
                String imagePath = resultSet.getString("imagePath");
                artwork = new ArtWork(title, artist, year, type, imagePath);
                artwork.setId(id);
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching artwork by name", e);
        }
        return artwork;
    }


    @Override
    public List<ArtWork> getAllArtworks() throws DAOException {
        List<ArtWork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM artworks";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                int year = resultSet.getInt("year");
                String type = resultSet.getString("type");
                String imagePath = resultSet.getString("imagePath");
                ArtWork artwork = new ArtWork(title, artist, year, type, imagePath);
                artwork.setId(id);
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving all artworks", e);
        }
        return artworks;
    }


    @Override
    public void updateArtwork(ArtWork artwork) throws DAOException {
        // SQL pentru a actualiza o operă de artă folosind ID-ul dacă este disponibil, altfel folosește titlul ca fallback.
        String sql = "UPDATE artworks SET artist = ?, year = ?, type = ?, imagePath = ? WHERE title = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, artwork.getArtist());
            statement.setInt(2, artwork.getYear());
            statement.setString(3, artwork.getType());
            statement.setString(4, artwork.getImagePath());
            statement.setString(5, artwork.getTitle());

            // Loghează fiecare valoare pentru a verifica ce se trimite către DB
            System.out.println("Updating Artwork:");
            System.out.println("Artist: " + artwork.getArtist());
            System.out.println("Year: " + artwork.getYear());
            System.out.println("Type: " + artwork.getType());
            System.out.println("Image Path: " + artwork.getImagePath());
            System.out.println("Title: " + artwork.getTitle());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Opera de artă a fost actualizată cu succes.");
            } else {
                System.out.println("Nu s-a găsit opera de artă cu titlul specificat pentru actualizare.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error updating artwork", e);
        }
    }


    @Override
    public void deleteArtwork(String title) throws DAOException {
        String sql = "DELETE FROM artworks WHERE title=?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, title);

            int affectedRows = statement.executeUpdate();
            System.out.println("Stergerea a reusit.");
            if (affectedRows == 0) {
                throw new DAOException("Ștergerea operelor de artă a eșuat; niciun rând afectat.");
            }
        } catch (SQLException e) {
            throw new DAOException("Eroare la ștergerea operelor de artă", e);
        }
    }

}
