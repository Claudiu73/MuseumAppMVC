package Controller;

import Repo.UserRepository;
import View.ArtWorkListView;

public class MainScreenController {

    private LogInController logInController;
    private UserRepository userRepository;


    public MainScreenController(LogInController logInController, UserRepository userRepository) {

        this.logInController = logInController;
        this.userRepository = userRepository;
    }

    public void OpenVisitor()
    {
        ArtWorkListView artWorkListView = new ArtWorkListView(false);
        ArtWorkController artWorkListViewModel = new ArtWorkController();
    }

}
