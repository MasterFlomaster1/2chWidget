package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("2chWidget");
        primaryStage.setScene(new Scene(root, 400, 200, Color.TRANSPARENT));
        primaryStage.show();
//        ThreadsParser.parseThreads();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
