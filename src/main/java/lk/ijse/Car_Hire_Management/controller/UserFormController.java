package lk.ijse.Car_Hire_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.UserDto;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.UserService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    private UserService userService;

    @FXML
    private TextField emailText;

    @FXML
    private AnchorPane idText;

    @FXML
    private TextField mobileText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField pwd;

    @FXML
    private TextField userIdText;

    @FXML
    private TextField userNameText;



    @FXML
    void registerUser(ActionEvent event) {
        UserDto userDto = new UserDto();

        userDto.setId(Integer.valueOf((userIdText.getText())));
        userDto.setName(nameText.getText());
        userDto.setEmail(emailText.getText());
        userDto.setMobile(mobileText.getText());
        userDto.setUsername(userNameText.getText());
        userDto.setPassword(passwordText.getText());

        boolean saveUser = userService.saveUser(userDto);
        new Alert(Alert.AlertType.INFORMATION,"Saved user successfully!").show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // loadAllUsers();
    }


    @FXML
    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);

        // Get the current button that triggered the event
        Node source = (Node) actionEvent.getSource();

        // Get the current stage (window)
        Stage currentStage = (Stage) source.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Login Form");
        newStage.centerOnScreen();

        // Close the current window before showing the new one
        currentStage.close();

        newStage.show();
    }
}
