    package Controller;

    import Model.ArtWork;
    import Model.User;
    import Repo.ArtWorkRepository;
    import Repo.DAOException;
    import Repo.UserRepository;
    import View.ArtWorkListView;
    import View.UsersFilterUI;

    import javax.swing.*;
    import java.util.List;

    public class AdminController {
        private ArtWorkRepository artWorkRepository;
        private UserRepository userRepository;
        private UsersFilterUI usersFilterUI;
        private String title;
        private String author;
        private Integer year;
        private String type;
        private String username;
        private String password;
        private String userType;
        private DefaultListModel<String> listModelArt = new DefaultListModel<>();
        private JList<String> listArt;
        private DefaultListModel<String> listModelUser = new DefaultListModel<>();
        private JList<String> listUser;


        public AdminController() {

            this.userRepository = new UserRepository();
            this.artWorkRepository = new ArtWorkRepository();
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

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getUserType() {
            return userType;
        }

        public DefaultListModel<String> getListModelArt() {
            return listModelArt;
        }

        public JList<String> getListArt() {
            return listArt;
        }

        public DefaultListModel<String> getListModelUser() {
            return listModelUser;
        }

        public JList<String> getListUser() {
            return listUser;
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

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public void setListModelArt(DefaultListModel<String> listModelArt) {
            this.listModelArt = listModelArt;
        }

        public void setListArt(JList<String> listArt) {
            this.listArt = listArt;
        }

        public void setListModelUser(DefaultListModel<String> listModelUser) {
            this.listModelUser = listModelUser;
        }

        public void setListUser(JList<String> listUser) {
            this.listUser = listUser;
        }

        public void AddUser()
        {
            String username = getUsername().trim();
            String password = getPassword().trim();
            String usertype = getUserType().trim();

            if (username.isEmpty() || password.isEmpty() || usertype.isEmpty()) {
                setUsername("Trebuie completat username-ul!");
                setPassword("Trebuie completata parola!");
                setUserType("Trebuie completat tipul de utilizator!");
                JOptionPane.showMessageDialog(null, "Trebuie completate toate spatiile.", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(getUsername(), getPassword(), getUserType());

            try {
                userRepository.addUser(newUser);
                JOptionPane.showMessageDialog(null, "Utilizator adaugat cu succes", "Succes", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Adaugarea a avut succes.");
                ListUsers();
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "Eroare la adaugarea utilizatorului", "Eroare", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        public void DeleteUser()
        {
            String username = getUsername().trim();

            if(username.isEmpty()) {
                //showMessageError("Vă rugăm introduceți username-ul pentru ștergere.");
                return;
            }

            UserRepository userRepository = new UserRepository();
            try {
                User user = userRepository.getUserByUsername(username);
                if (user == null) {
                    JOptionPane.showMessageDialog(null, "Utilizatorul nu exista.", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null, "Sunteți sigur că doriți să ștergeți utilizatorul: '" + username + "'?", "Confirmare stergere utilizator", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    userRepository.deleteUser(username);
                    JOptionPane.showMessageDialog(null, "Utilizator sters cu succes.", "Eroare", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Stergerea a avut succes");
                }
                ListUsers();
            }
            catch (DAOException e) {
                JOptionPane.showMessageDialog(null, "Eroare la stergerea utilizatorului.", "Eroare", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }

        public void SearchUser()
        {
            String username = getUsername().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduceti un utilizator.", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                User user = userRepository.getUserByUsername(username);

                if (user != null) {
                    System.out.println("S-a gasit user-ul:");
                    String details = "Username: " + user.getUsername() +
                            "\nPassword: " +user.getPassword() +
                            "\nTip: " + user.getUserType();
                    System.out.println(details);
                    JOptionPane.showMessageDialog(null, details, "Detalii User", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Eroare la cautarea acestui utilizator.", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "Eroare la cautarea acestui utilizator.", "Eroare", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        public void UpdateUser()
        {
            String username = getUsername().trim();
            String password = getPassword().trim();
            String type = getUserType().trim();

            User userToBeUpdated;
            try{
                userToBeUpdated = userRepository.getUserByUsername(username);
            }
            catch(DAOException e)
            {
                JOptionPane.showMessageDialog(null, "Eroare la cautarea username-ului.", "Eroare", JOptionPane.ERROR_MESSAGE);

                return;
            }

            if(userToBeUpdated != null)
            {
                userToBeUpdated.setPassword(password);
                userToBeUpdated.setUserType(type);

                try{
                    userRepository.updateUser(userToBeUpdated);
                    System.out.println("User Actualizat.");
                    JOptionPane.showMessageDialog(null, "Userul actualizat cu succes.", "Eroare", JOptionPane.INFORMATION_MESSAGE);
                    ListUsers();
                }
                catch(DAOException e) {
                    JOptionPane.showMessageDialog(null, "Actualizarea a esuat", "Eroare", JOptionPane.ERROR_MESSAGE);

                    e.printStackTrace();
                }
            }else
            {
                JOptionPane.showMessageDialog(null, "User-ul nu a fost gasit.", "Eroare", JOptionPane.ERROR_MESSAGE);

            }
        }

        public void ListArtWorks()
        {
            try {
                List<ArtWork> artworks = artWorkRepository.getAllArtworks();
                SwingUtilities.invokeLater(() -> {
                    DefaultListModel<String> listModel = getListModelArt();
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

        public void ListUsers()
        {
            try {
                List<User> users = userRepository.getAllUsers();
                SwingUtilities.invokeLater(()-> {
                    DefaultListModel<String> userModel = getListModelUser();
                    userModel.clear();
                    for (User user : users) {
                        userModel.addElement(user.toString());
                        System.out.println(user);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void OpenToFilterUsers()
        {
            usersFilterUI = new UsersFilterUI();
            usersFilterUI.setVisible(true);
        }

        public void OpenToFilterListOfArtWorks()
        {
            ArtWorkListView artWorkListView = new ArtWorkListView(true);
        }

    }
