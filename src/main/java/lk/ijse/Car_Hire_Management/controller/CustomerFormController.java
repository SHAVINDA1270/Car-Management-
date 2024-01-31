package lk.ijse.Car_Hire_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.CustomerDto;
import lk.ijse.Car_Hire_Management.dto.CustomerTableModel;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.CustomerService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {

    CustomerService customerService;

    @FXML
    private TextArea cAddressText;

    @FXML
    private TextField cIdText;

    @FXML
    private TextField cMobileText;

    @FXML
    private TextField cNameText;

    @FXML
    private TextField cNicText;

    @FXML
    private TableColumn<CustomerTableModel, String> columnAddress;

    @FXML
    private TableColumn<CustomerTableModel, Integer> columnId;

    @FXML
    private TableColumn<CustomerTableModel, String> columnMobile;

    @FXML
    private TableColumn<CustomerTableModel, String> columnName;

    @FXML
    private TableColumn<CustomerTableModel, String> columnNic;

    @FXML
    private TableView<CustomerTableModel> customerTable;

    public void initialize() throws Exception {
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        System.out.println("Customer Form Loaded");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        loadAllCustomers();
    }

    @FXML
    void AddCustomerButton(ActionEvent event) {
        saveCustomer();
    }

    public void DeleteCustomerButton(ActionEvent actionEvent) {
        deleteCustomer();
    }

    public void UpdateCustomerButton(ActionEvent actionEvent) {
        updateCustomer();
    }

    public void customerTableMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            searchCustomer();
        }
    }

    private void saveCustomer() {
        try {
            if (cIdText.getText().isEmpty()) {
                cIdText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Empty Field").show();
            } else if (!cNameText.getText().matches("^([A-Za-z]+\\s?)+$") && cNameText.getText().isEmpty()) {
                cNameText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Invalid customer name or empty").show();
            } else if (cAddressText.getText().isEmpty()) {
                cAddressText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Address field is empty").show();
            } else if (cNicText.getText().isEmpty()) {
                cNicText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "NIC field is empty").show();
            } else if (!cMobileText.getText().matches("^[0-9]{10}$") && cMobileText.getText().isEmpty()) {
                cMobileText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Contact number field is invalid or empty").show();
            } else {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(Integer.valueOf(cIdText.getText()));
                customerDto.setName(cNameText.getText());
                customerDto.setNic(cNicText.getText());
                customerDto.setAddress(cAddressText.getText());
                customerDto.setMobile(cMobileText.getText());

                boolean isSaved = customerService.saveCus(customerDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Saved customer successfully!").show();
                    clear();
                    loadAllCustomers();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void updateCustomer() {
        try {
            if (cIdText.getText().isEmpty()) {
                cIdText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Empty Field").show();
            } else if (!cNameText.getText().matches("^([A-Za-z]+\\s?)+$") && cNameText.getText().isEmpty()) {
                cNameText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Invalid customer name or empty").show();
            } else if (cAddressText.getText().isEmpty()) {
                cAddressText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Address field is empty").show();
            } else if (cNicText.getText().isEmpty()) {
                cNicText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "NIC field is empty").show();
            } else if (!cMobileText.getText().matches("^[0-9]{10}$") && cMobileText.getText().isEmpty()) {
                cMobileText.requestFocus();
                new Alert(Alert.AlertType.ERROR, "Contact number field is invalid or empty").show();
            } else {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(Integer.valueOf(cIdText.getText()));
                customerDto.setName(cNameText.getText());
                customerDto.setNic(cNicText.getText());
                customerDto.setAddress(cAddressText.getText());
                customerDto.setMobile(cMobileText.getText());

                boolean isUpdated = customerService.updateCus(customerDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Successfully Updated").show();
                    clear();
                    loadAllCustomers();
                }
            }
        } catch (Exception exception) {
            new Alert(Alert.AlertType.ERROR, "Delete this Customer\n" +
                    "If it is done, it cannot be deleted because the records in\n the related rent table will be deleted.").show();

        }
    }

    private void deleteCustomer() {
        try {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this customer?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonTypeYes) {
                boolean isDeleted = customerService.deleteCus(Integer.valueOf(cIdText.getText()));
                if(isDeleted){
                    clear();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Customer Successfully!").show();
                }


                loadAllCustomers();
            }

        } catch (Exception exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }

    private void searchCustomer() {
        CustomerTableModel customerTableModel = customerTable.getSelectionModel().getSelectedItem();

        cIdText.setText(String.valueOf(customerTableModel.getId()));
        cNicText.setText(customerTableModel.getNic());
        cNameText.setText(customerTableModel.getName());
        cMobileText.setText(customerTableModel.getMobile());
        cAddressText.setText(customerTableModel.getAddress());
    }

    private void loadAllCustomers() throws SQLException {
        List<CustomerDto> customerDtoList = customerService.getAllCus();

        ObservableList<CustomerTableModel> customerTableModelList = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtoList) {
            CustomerTableModel customerTableModel = new CustomerTableModel(
                    String.valueOf(customerDto.getId()),
                    customerDto.getName(),
                    customerDto.getNic(),
                    customerDto.getAddress(),
                    customerDto.getMobile()
            );

            customerTableModelList.add(customerTableModel);
        }

        customerTable.setItems(customerTableModelList);
    }

    private void clear() {
        cIdText.setText("");
        cNameText.setText("");
        cNicText.setText("");
        cAddressText.setText("");
        cMobileText.setText("");
    }

    public void mainMenuButton(ActionEvent actionEvent) throws IOException {
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
    }
}
