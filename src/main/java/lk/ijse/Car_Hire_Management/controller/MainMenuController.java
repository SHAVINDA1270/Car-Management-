package lk.ijse.Car_Hire_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private TextField userNameText;
    public void customerRegisterButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
        Scene scene = new Scene(root);


        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Customer Registration Form");
        newStage.centerOnScreen();


        currentStage.close();

        newStage.show();
    }
    public void carRegisterButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Car.fxml"));
        Scene scene = new Scene(root);

        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Car Registration Form");
        newStage.centerOnScreen();


        currentStage.close();

        newStage.show();
    }

    public void addRentButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/RentForm.fxml"));
        Scene scene = new Scene(root);


        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Rent Form");
        newStage.centerOnScreen();


        currentStage.close();

        newStage.show();
    }

    public void returnCarButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReturnForm.fxml"));
        Scene scene = new Scene(root);


        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Return a Car Form");
        newStage.centerOnScreen();


        currentStage.close();

        newStage.show();
    }

    public void logoutButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);


        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Login Form");
        newStage.centerOnScreen();


        currentStage.close();

        newStage.show();
    }

    public void addCarCategoryButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CarCategory.fxml"));
        Scene scene = new Scene(root);


        Node source = (Node) actionEvent.getSource();


        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Add New Car Category Form");
        newStage.centerOnScreen();

        currentStage.close();

        newStage.show();
    }
}
