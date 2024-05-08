package Controller;

import Model.User;
import Repo.DAOException;
import Repo.UserRepository;
import View.AdminUI;
import View.EmployeeUI;

import javax.swing.*;


public class LogInController {
    private UserRepository userRepository;
    private String username;
    private String password;
    private String userType;
    public LogInController()
    {
        this.userRepository = new UserRepository();
    }
    public void logInButtonClicked()
    {
        String username = getUsername();
        String password = getPassword();
        String userType = getUserType();

        System.out.println("Attempt to login with: " + username + ", " + password + ", " + userType);

        try {
            User user = userRepository.getUserByUsername(username.trim());
            if (user != null && user.getPassword().equals(password.trim()) && user.getUserType().equalsIgnoreCase(userType)) {
                SwingUtilities.invokeLater(() -> {
                    switch (userType.toLowerCase()) {
                        case "angajat":
                            new EmployeeUI().setVisible(true);
                            break;
                        case "admin":
                            new AdminUI().setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Tip de utilizator necunoscut.", "Eroare", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                });
            } else {
                JOptionPane.showMessageDialog(null, "Autentificare eșuată.", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la autentificare: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void initiateLogin() {
        if (!getUsername().isEmpty() && !getPassword().isEmpty()) {
            logInButtonClicked();
        } else {
            JOptionPane.showMessageDialog(null, "Vă rugăm să introduceți numele de utilizator și parola.", "Date lipsă", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }
}