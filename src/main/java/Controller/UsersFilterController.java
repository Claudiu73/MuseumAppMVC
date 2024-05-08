package Controller;

import Model.User;
import Repo.DAOException;
import Repo.UserRepository;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class UsersFilterController {

    private UserRepository userRepository;
    private DefaultListModel<String> listModelUser = new DefaultListModel<>();
    private JList<String> listUser;
    private String username;
    private String password;
    private String userType;

    public UsersFilterController()
    {
        this.userRepository = new UserRepository();
    }

    public DefaultListModel<String> getListModelUser() {
        return listModelUser;
    }

    public void setListModelUser(DefaultListModel<String> listModelUser) {
        this.listModelUser = listModelUser;
    }

    public JList<String> getListUser() {
        return listUser;
    }

    public void setListUser(JList<String> listUser) {
        this.listUser = listUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void ListUsersToBeFiltered()
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

    public void ToFilterUsernameForAdmin()
    {
        try {
            List<User> allUsers = userRepository.getAllUsers();
            DefaultListModel<String> model = new DefaultListModel<>();

            String usernameFilter = getUsername().trim().toLowerCase();

            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(usernameFilter) || usernameFilter.isEmpty())
                    .collect(Collectors.toList());

            for (User user : filteredUsers) {
                model.addElement(user.toString());
            }

            setListModelUser(model);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea utilizatorilor.", "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void ToFilterPasswordForAdmin()
    {
        try {
            List<User> allUsers = userRepository.getAllUsers();
            DefaultListModel<String> model = new DefaultListModel<>();

            String passwordFilter = getPassword().trim().toLowerCase();

            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getPassword().toLowerCase().contains(passwordFilter) || passwordFilter.isEmpty())
                    .collect(Collectors.toList());

            for (User user : filteredUsers) {
                model.addElement(user.toString());
            }

            setListModelUser(model);
        } catch (DAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea dupa parola.", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ToFilterUserTypeForAdmin()
    {
        try {
            List<User> allUsers = userRepository.getAllUsers();
            DefaultListModel<String> model = new DefaultListModel<>();

            String userTypeFilter = getUserType().trim().toLowerCase();

            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getUserType().toLowerCase().contains(userTypeFilter) || userTypeFilter.isEmpty())
                    .collect(Collectors.toList());

            for (User user : filteredUsers) {
                model.addElement(user.toString());
            }

            setListModelUser(model);
        } catch (DAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la filtrarea utilizatorilor dupa tip.", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}
