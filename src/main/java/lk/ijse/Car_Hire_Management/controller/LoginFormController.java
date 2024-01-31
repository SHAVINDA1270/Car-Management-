package lk.ijse.Car_Hire_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.UserDto;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.UserService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;

import java.io.IOException;

public class LoginFormController {
    private static String loggedInUserName;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField userNameText;

    private UserService userService;



    @FXML
    void userLoginButton(ActionEvent actionEvent) throws Exception {
        userService = ServiceFactory.getInstance().getService(ServiceType.USER);

        try {
            UserDto dto = userService.getUser(userNameText.getText());

            if (dto != null) {
                if (dto.getPassword().equals(passwordText.getText())) {
                    // Password is correct, print message
                    System.out.println("You can log in!");

                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Scene scene = new Scene(root);

                    // Get the current button that triggered the event
                    Node source = (Node) actionEvent.getSource();

                    // Get the current stage (window)
                    Stage currentStage = (Stage) source.getScene().getWindow();

                    Stage newStage = new Stage();
                    newStage.setScene(scene);
                    newStage.setTitle("Main Menu Form");
                    newStage.centerOnScreen();

                    // Close the current window before showing the new one
                    currentStage.close();

                    newStage.show();

                    // You can add further logic or navigate to another scene if needed
                } else {
                    new Alert(Alert.AlertType.WARNING, "Incorrect Password!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "User not found!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void newUserRegisterHyperlink(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserForm.fxml"));
        Scene scene = new Scene(root);

        // Get the current button that triggered the event
        Node source = (Node) actionEvent.getSource();

        // Get the current stage (window)
        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("User Registration Form");
        newStage.centerOnScreen();

        // Close the current window before showing the new one
        currentStage.close();

        newStage.show();
    }
}
