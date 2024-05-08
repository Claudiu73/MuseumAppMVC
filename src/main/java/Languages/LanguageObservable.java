package Languages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageObservable {
    private List<Observer> observers = new ArrayList<>();
    private String currentLanguage = "RO"; // Default language
    private Map<String, String> languageTextMap = new HashMap<>();

    public LanguageObservable() {
        loadLanguage(currentLanguage); // Load default language
    }

    public void setLanguage(String language) {
        if (!language.equals(currentLanguage)) { // Check if language really needs to be changed
            loadLanguage(language);
            notifyObservers();
        }
    }

    private void loadLanguage(String language) {
        String filePath = getLanguageFilePath(language);
        try {
            loadLanguageTextMap(filePath);
            currentLanguage = language; // Update the current language
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLanguageFilePath(String language) {
        return "src/main/java/Languages/files/" + language.toLowerCase() + ".txt";
    }


    private void loadLanguageTextMap(String filePath) throws IOException {
        languageTextMap.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    languageTextMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nu s-a putut găsi fișierul: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Eroare la citirea din fișierul: " + filePath);
            e.printStackTrace();
        }
    }



    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateLanguage();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public String getLanguageText(String key) {
        return languageTextMap.getOrDefault(key, "Text not found for key: " + key);
    }

}
