package lk.ijse.Car_Hire_Management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent rootNode= FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));


        Scene scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
