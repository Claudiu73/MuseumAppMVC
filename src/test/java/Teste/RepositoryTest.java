package Teste;

import Model.ArtWork;
import Model.User;
import Repo.ArtWorkRepository;
import Repo.DAOException;
import Repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private ArtWorkRepository repository;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws DAOException {
        repository = new ArtWorkRepository();
        userRepository = new UserRepository();
        repository.addArtwork(new ArtWork("Mona Lisa", "Leonardo da Vinci", 1503, "Pictură", " "));
        repository.addArtwork(new ArtWork("Noapte instelata", "Vincent van Gogh", 1889, "Pictură", " "));
    }

    @Test
    void addArtworkAndRetrieveTest() throws DAOException {
        ArtWork artwork = new ArtWork("Titlu", "Artist", 2020, "Pictură", " ");
        repository.addArtwork(artwork);

        List<ArtWork> artworks = repository.getAllArtworks();

        boolean found = artworks.stream().anyMatch(a ->
                a.getTitle().equals(artwork.getTitle()) &&
                        a.getArtist().equals(artwork.getArtist()) &&
                        a.getYear() == artwork.getYear() &&
                        a.getType().equals(artwork.getType())
        );

        assertTrue(found, "Opera de artă ar trebui să fie în lista recuperată");
    }

    @Test
    void deleteArtworkTest() throws DAOException {
        ArtWork artwork = new ArtWork("TestTitleForDeletion", "TestArtist", 2000, "TestType", " ");
        repository.addArtwork(artwork);

        repository.deleteArtwork("TestTitleForDeletion");

        ArtWork deletedArtwork = repository.getArtworkByName("TestTitleForDeletion");
        assertNull(deletedArtwork, "Opera de artă nu ar trebui să mai fie în baza de date după ștergere");
    }




    @Test
    void getAllArtworksTest() throws DAOException {
        List<ArtWork> artworks = repository.getAllArtworks();

        assertFalse(artworks.isEmpty(), "Lista de opere de artă nu ar trebui să fie goală.");

        assertTrue(artworks.stream().anyMatch(artwork -> "Mona Lisa".equals(artwork.getTitle()) && "Leonardo da Vinci".equals(artwork.getArtist())),
                "Lista de opere de artă ar trebui să conțină 'Mona Lisa' de Leonardo da Vinci");
        assertTrue(artworks.stream().anyMatch(artwork -> "Noapte instelata".equals(artwork.getTitle()) && "Vincent van Gogh".equals(artwork.getArtist())),
                "Lista de opere de artă ar trebui să conțină 'Noapte instelata' de Vincent van Gogh");
    }

    @Test
    void addUserTest() throws DAOException {
        User newUser = new User("testUser", "testPass", "userType");
        userRepository.addUser(newUser);

        User retrievedUser = userRepository.getUserByUsername("testUser");
        assertNotNull(retrievedUser, "Utilizatorul ar trebui să existe în baza de date");
        assertEquals(newUser.getUsername(), retrievedUser.getUsername(), "Numele de utilizator ar trebui să fie identic");
    }

    @Test
    void deleteUserTest() throws DAOException {
        String testUsername = "testDeleteUser";
        String testPassword = "testPassword";
        String testUserType = "testType";

        User testUser = new User(testUsername, testPassword, testUserType);
        userRepository.addUser(testUser);

        User addedUser = userRepository.getUserByUsername(testUsername);
        assertNotNull(addedUser, "Utilizatorul ar trebui să fie adăugat în baza de date");

        userRepository.deleteUser(testUsername);

        User deletedUser = userRepository.getUserByUsername(testUsername);
        assertNull(deletedUser, "Utilizatorul ar trebui să fie șters din baza de date");
    }


    @Test
    void getUserByUsernameTest() throws DAOException {
        User testUser = new User("testUsername", "testPassword", "testType");
        userRepository.addUser(testUser);

        User retrievedUser = userRepository.getUserByUsername("testUsername");

        assertNotNull(retrievedUser, "Utilizatorul ar trebui să fie găsit în baza de date.");
        assertEquals("testUsername", retrievedUser.getUsername(), "Username-ul utilizatorului ar trebui să fie identic.");
        assertEquals("testPassword", retrievedUser.getPassword(), "Parola utilizatorului ar trebui să fie identică.");
        assertEquals("testType", retrievedUser.getUserType(), "Tipul utilizatorului ar trebui să fie identic.");

        userRepository.deleteUser("testUsername");
    }

    @Test
    void getAllUsersTest() throws DAOException {
        User user1 = new User("username1", "password1", "type1");
        User user2 = new User("username2", "password2", "type2");
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        List<User> users = userRepository.getAllUsers();

        assertTrue(users.stream().anyMatch(user ->
                        "username1".equals(user.getUsername()) &&
                                "password1".equals(user.getPassword()) &&
                                "type1".equals(user.getUserType())),
                "Lista de utilizatori ar trebui să conțină utilizatorul 1");

        assertTrue(users.stream().anyMatch(user ->
                        "username2".equals(user.getUsername()) &&
                                "password2".equals(user.getPassword()) &&
                                "type2".equals(user.getUserType())),
                "Lista de utilizatori ar trebui să conțină utilizatorul 2");

        userRepository.deleteUser("username1");
        userRepository.deleteUser("username2");
    }


    @AfterEach
    void tearDown() throws DAOException {
        repository.deleteArtwork("Mona Lisa");
        repository.deleteArtwork("Noapte instelata");
    }


}
