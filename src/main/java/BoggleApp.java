import com.bamboggled.model.model.BoggleModel;

import com.bamboggled.views.WelcomeView.WelcomeView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BoggleApp extends Application {

    BoggleModel model;

    WelcomeView welcomeView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage beginStage) throws IOException {
        this.model = new BoggleModel();
        this.welcomeView = new WelcomeView(model, beginStage);
    }


}
