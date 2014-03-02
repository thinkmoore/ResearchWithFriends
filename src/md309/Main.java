package md309;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        primaryStage.setTitle("Research with Friends");
        primaryStage.setScene(new Scene(root));
        Controller ctrl = fxmlLoader.getController();
        ctrl.moreSetup();


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
