import java.io.IOException;

import chia.ui.Chia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Chia chia = new Chia();

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Chia");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setChia(chia);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
