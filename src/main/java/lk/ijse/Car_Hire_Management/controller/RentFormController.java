package lk.ijse.Car_Hire_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.*;
import lk.ijse.Car_Hire_Management.entity.CustomerEntity;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.CarService;
import lk.ijse.Car_Hire_Management.service.custom.CustomerService;
import lk.ijse.Car_Hire_Management.service.custom.RentService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;



public class RentFormController implements Initializable {

    private RentService rentService;
    private CarService carService;

    private CustomerService customerService;

    @FXML
    private DatePicker ToDate;

    @FXML
    private TextField advancedPaymentText;

    @FXML
    private TextField availabilityText;

    @FXML
    private TextField balanceText;

    @FXML
    private DatePicker fromDate;

    @FXML
    private TextField perDayRentText;

    @FXML
    private TextField rentIdText;

    @FXML
    private TextField totalText;

    @FXML
    private ComboBox<Integer> carIdComboBox;

    @FXML
    private ComboBox<Integer> customerIdComboBox;

    @FXML
    private TextField carText;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField refundableDepositText;

    @FXML
    private TableColumn<RentTableModelDto, Double> balanceColumn;

    @FXML
    private TableColumn<RentTableModelDto, String> customeNameColumn;

    @FXML
    private TableColumn<RentTableModelDto, Integer> rentIdColumn;

    @FXML
    private TableColumn<RentTableModelDto, String> rentedCarColumn;

    @FXML
    private TableColumn<RentTableModelDto, LocalDate> rentedDateColumn;

    @FXML
    private TableColumn<RentTableModelDto, LocalDate> returnDateColumn;

    @FXML
    private TableColumn<RentTableModelDto, String> returnStatusColumn;

    @FXML
    private TableView<RentTableModelDto> rentTable;

    private Double refundableDeposit;

    private String car;

    Integer selectedCarId;

    //private String selectedCustomer;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carService = ServiceFactory.getInstance().getService(ServiceType.CAR);
        rentService = ServiceFactory.getInstance().getService(ServiceType.RENT);
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);

        carIdComboBox.getItems().clear();
        ObservableList<Integer> Ids = FXCollections.observableArrayList();

        List<CarDto> carIds = carService.getAllCar();

        for (CarDto carDto : carIds) {
            Ids.add(carDto.getId());
        }

        carIdComboBox.setItems(Ids);

        System.out.println(Ids);

        //Load Customer Ids
        customerIdComboBox.getItems().clear();
        ObservableList<Integer> CIds = FXCollections.observableArrayList();

        List<CustomerDto> custmerIds = customerService.getAllCus();

        for (CustomerDto customerDto : custmerIds) {
            CIds.add(customerDto.getId());
        }

        customerIdComboBox.setItems(CIds);

        System.out.println(CIds);

        //LoadAllRents
        rentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        rentedCarColumn.setCellValueFactory(new PropertyValueFactory<>("Car"));
        customeNameColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_name"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        rentedDateColumn.setCellValueFactory(new PropertyValueFactory<>("from_date"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("to_date"));
        returnStatusColumn.setCellValueFactory(new PropertyValueFactory<>("is_return"));


        loadAllRents();

    }

    @FXML
    void rentTableMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            searchRent();
        }
    }

    public void DeleteRentButton() {

            Integer GetRentId = Integer.valueOf(rentIdText.getText());
            System.out.println("Deleted rent Id  "+GetRentId);

        boolean isDeleted = rentService.deleteRent(GetRentId);


        if(GetRentId != null) {
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Rent details deleted successfully!").show();
                clearFields(); // Optional: Clear input fields or update UI as needed
                loadAllRents(); // Optional: Refresh the rent table or update UI as needed
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete rent details.").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Didn't Select Rent Id.").show();
        }
    }

    private void searchRent() {
        perDayRentText.setDisable(false);
        fromDate.setDisable(false);
        ToDate.setDisable(false);
        totalText.setDisable(false);
        advancedPaymentText.setDisable(false);
        balanceText.setDisable(false);

        RentTableModelDto rentTableModelDto = rentTable.getSelectionModel().getSelectedItem();
        if(rentTableModelDto != null){rentIdText.setEditable(false);
            rentIdText.setText(String.valueOf(rentTableModelDto.getId()));
            customerNameText.setText(rentTableModelDto.getCustomer_name());
            carText.setText(rentTableModelDto.getCar());
        }else{
            new Alert(Alert.AlertType.WARNING, "Rent details not Found!").show();
        }


    }

    private void loadAllRents()  {
        List<RentDto> rentDtoList = rentService.getAllRent();

        ObservableList<RentTableModelDto> rentTableModelDtoList = FXCollections.observableArrayList();

        for (RentDto rentDto : rentDtoList) {
            RentTableModelDto rentTableModelDto = new RentTableModelDto(

                    String.valueOf(rentDto.getId()),
                    rentDto.getCar(),
                    rentDto.getCustomer_name(),
                    rentDto.getBalance(),
                    rentDto.getFrom_date(),
                    rentDto.getTo_date(),
                    rentDto.getIs_return()
            );

            rentTableModelDtoList.add(rentTableModelDto);
        }

        rentTable.setItems(rentTableModelDtoList);
    }

    @FXML
    void carIdOnAction(ActionEvent event) {
        String selectedCarId = String.valueOf(carIdComboBox.getValue());

        CarDto selectedCar = null;
        List<CarDto> carIds = carService.getAllCar();
        for (CarDto carDto : carIds) {
            if (String.valueOf(carDto.getId()).equals(selectedCarId)) {
                selectedCar = carDto;
                break;
            }
        }

        if (selectedCar != null) {
            String availability = selectedCar.getAvailability();

            car = selectedCar.getBrand() + " "+ selectedCar.getModel();
            carText.setText(car);

            // Check availability and disable or enable text fields accordingly
            if ("No".equalsIgnoreCase(availability)) {
                Double perDayRent = carService.getPerdayRentByCarId(Integer.valueOf(selectedCarId));
                perDayRentText.setText(String.valueOf(perDayRent));

                refundableDeposit = carService.getRefundableDepositByCarId(Integer.valueOf(selectedCarId));
                refundableDepositText.setText(String.valueOf(refundableDeposit));
                new Alert(Alert.AlertType.WARNING, "The Car is in a Hire").show();
            } else {
                Double perDayRent = carService.getPerdayRentByCarId(Integer.valueOf(selectedCarId));
                perDayRentText.setText(String.valueOf(perDayRent));

                refundableDeposit = carService.getRefundableDepositByCarId(Integer.valueOf(selectedCarId));
                refundableDepositText.setText(String.valueOf(refundableDeposit));
                enableFields();
            }

            // Set the availability in the availabilityText TextField
            availabilityText.setText(availability);
        } else {
            // Car not found, clear and disable text fields
            clearAndDisableFields();
            System.out.println("Car not found for ID: " + selectedCarId);
        }
    }

    @FXML
    public void customerIdOnAction(ActionEvent actionEvent) {
        Integer selectedCustomerId = customerIdComboBox.getValue();

        if (selectedCustomerId != null) {
            System.out.println("Selected Customer Id: " + selectedCustomerId);

            CustomerEntity selectedCustomer = customerService.getCustomerById(selectedCustomerId);
            if (selectedCustomer != null) {

                System.out.println("Selected Customer Name: " + selectedCustomer.getName());
                customerNameText.setText(selectedCustomer.getName());
            } else {
                System.out.println("Error: Selected Customer not found");
            }

            String lastRentIsReturn = rentService.getLastRentIsReturnByCustomerId(selectedCustomerId);

            System.out.println("Value"+lastRentIsReturn);

            if (lastRentIsReturn == null || "Yes".equals(lastRentIsReturn)) {
                enableFields();
            } else if ("No".equals(lastRentIsReturn)) {
                new Alert(Alert.AlertType.WARNING, "This customer has already rented a car").show();
                disableFields();
            }

        }
    }

    @FXML
    void toDateOnAction(ActionEvent event) {
        LocalDate startDate = fromDate.getValue();
        LocalDate endDate = ToDate.getValue();


        boolean isWithinLimit = rentService.rentalDeurationLimit(startDate, endDate);

        if (isWithinLimit) {
            // Actions for rentals within the limit
            enableFieldstoDateonAction(); // or any other actions

            if (fromDate.getValue() != null && ToDate.getValue() != null) {
                try {

                    Double perDayRent = Double.valueOf(perDayRentText.getText());
                    Double total = rentService.calculateTotal(perDayRent, startDate, endDate,refundableDeposit);
                    totalText.setText(String.valueOf(total));
                } catch (NumberFormatException e) {
                    // Handle the case where perDayRentText is not a valid Double
                    System.out.println("Error: Invalid per day rent value");
                    totalText.clear(); // Clear the totalText field

                }
            } else {
                // Handle the case where either fromDate or ToDate is null
                System.out.println("Error: Please select both start and end dates.");
                totalText.clear(); // Clear the totalText field
            }

        } else {
            // Actions for rentals outside the limit
            disableFieldstoDateonAction(); // or any other actions
            System.out.println("Rental duration exceeds the allowed limit.");
        }

    }

    @FXML
    void calculateBalanceinAdvPayment(ActionEvent event) {
        Double advancedPayment = Double.valueOf(advancedPaymentText.getText());
        Double total = Double.valueOf(totalText.getText());

        Double balance = rentService.calculateBalance(total, advancedPayment);
        balanceText.setText(String.valueOf(balance));
    }

    @FXML
    void AddRentButton(ActionEvent event) {

        Integer selectedCarId = carIdComboBox.getValue();
        if(selectedCarId != null){
            System.out.println("Selected Car : " + selectedCarId);

            Integer selectedCustomerId = customerIdComboBox.getValue();
            if(selectedCustomerId != null){
                System.out.println("Selected Car : " + selectedCarId);

                RentDto rentDto = new RentDto();
                rentDto.setId(rentIdText.getText());
                rentDto.setFrom_date(java.sql.Date.valueOf(fromDate.getValue()));
                rentDto.setTo_date(java.sql.Date.valueOf(ToDate.getValue()));
                rentDto.setPerDay_rent(Double.valueOf(perDayRentText.getText()));
                rentDto.setTotal(Double.valueOf(totalText.getText()));
                rentDto.setAdvanced_payment(Double.valueOf(advancedPaymentText.getText()));
                rentDto.setBalance(Double.valueOf(balanceText.getText()));
                rentDto.setCarId(selectedCarId);
                rentDto.setCustomerId(selectedCustomerId);
                rentDto.setRefundable_Deposit(refundableDeposit);
                rentDto.setCustomer_name(customerNameText.getText());
                System.out.println("Model + Brand"+carText.getText());
                rentDto.setCar(carText.getText());
                System.out.println("Customer name"+customerNameText.getText());


                String isReturn = "No";
                rentDto.setIs_return(isReturn);


                String availability = "No";

                boolean isSaved = rentService.saveRent(rentDto);
                boolean ispdated = carService.updateAvailabilityById(selectedCarId,availability);

                if (isSaved) {
                    loadAllRents();
                    new Alert(Alert.AlertType.INFORMATION, "Rent Succcesfully Added").show();
                    if (ispdated) {
                        System.out.println("************************************Availability Updated*******************************************************");
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Can't Update New Availability").show();
                    }

                }else{
                    new Alert(Alert.AlertType.ERROR, "Can't Add Rent").show();
                }

            }else{
                System.out.println("Customer Id not found");
            }
        }else{
            System.out.println("Car Id not found");
        }

        clearInputFields();
    }

    @FXML
    public void UpdateButton(ActionEvent actionEvent) {

        Integer selectedCarId = carIdComboBox.getValue();
        if(selectedCarId != null){
            System.out.println("Selected Car : " + selectedCarId);

            Integer selectedCustomerId = customerIdComboBox.getValue();
            if(selectedCustomerId != null){
                System.out.println("Selected Car : " + selectedCarId);

                RentDto rentDto = new RentDto();
                rentDto.setId(rentIdText.getText());
                rentDto.setFrom_date(java.sql.Date.valueOf(fromDate.getValue()));
                rentDto.setTo_date(java.sql.Date.valueOf(ToDate.getValue()));
                rentDto.setPerDay_rent(Double.valueOf(perDayRentText.getText()));
                rentDto.setTotal(Double.valueOf(totalText.getText()));
                rentDto.setAdvanced_payment(Double.valueOf(advancedPaymentText.getText()));
                rentDto.setBalance(Double.valueOf(balanceText.getText()));
                rentDto.setCarId(selectedCarId);
                rentDto.setCustomerId(selectedCustomerId);
                rentDto.setRefundable_Deposit(refundableDeposit);
                rentDto.setCustomer_name(customerNameText.getText());
                System.out.println("Model + Brand"+carText.getText());
                rentDto.setCar(carText.getText());
                System.out.println("Customer name"+customerNameText.getText());


                String isReturn = "No";
                rentDto.setIs_return(isReturn);


                String availability = "No";

                boolean isSaved = rentService.updateRent(rentDto);
                boolean ispdated = carService.updateAvailabilityById(selectedCarId,availability);

                if (isSaved) {
                    loadAllRents();
                    new Alert(Alert.AlertType.INFORMATION, "Rent Succcesfully Updated").show();
                    if (ispdated) {
                        System.out.println("************************************Availability Updated*******************************************************");
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Can't Update New Availability").show();
                    }

                }else{
                    new Alert(Alert.AlertType.ERROR, "Can't Update Rent").show();
                }

            }else{
                System.out.println("Customer Id not found");
            }
        }else{
            System.out.println("Car Id not found");
        }

        clearInputFields();
    }

    // Method to clear input fields
    private void clearInputFields() {
        fromDate.setValue(null);
        ToDate.setValue(null);
        perDayRentText.clear();
        advancedPaymentText.clear();
        availabilityText.clear();
        balanceText.clear();
        rentIdText.clear();
        totalText.clear();
        carIdComboBox.getSelectionModel().clearSelection();
    }


    @FXML
    void MainMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);

        // Get the current button that triggered the event
        Node source = (Node) event.getSource();

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


    // Method to disable all text fields
    private void disableFields() {
        balanceText.setDisable(true);
        fromDate.setDisable(true);
        perDayRentText.setDisable(true);
        rentIdText.setDisable(true);
        totalText.setDisable(true);
        ToDate.setDisable(true);
        advancedPaymentText.setDisable(true);
    }

    // Method to enable all text fields
    private void enableFields() {
        balanceText.setDisable(false);
        fromDate.setDisable(false);
        perDayRentText.setDisable(false);
        rentIdText.setDisable(false);
        totalText.setDisable(false);
        ToDate.setDisable(false);
        advancedPaymentText.setDisable(false);
    }

    // Method to clear and disable all text fields
    private void clearAndDisableFields() {
        disableFields();
        availabilityText.clear();
    }




    private void enableFieldstoDateonAction() {
        balanceText.setDisable(false);
        fromDate.setDisable(false);
        perDayRentText.setDisable(false);
        rentIdText.setDisable(false);
        totalText.setDisable(false);
        ToDate.setDisable(false);
        advancedPaymentText.setDisable(false);
    }

    private void disableFieldstoDateonAction() {
        balanceText.setDisable(true);
        perDayRentText.setDisable(true);
        rentIdText.setDisable(true);
        totalText.setDisable(true);
        advancedPaymentText.setDisable(true);
    }

    @FXML
    private void clearFields() {
        ToDate.setValue(null);
        advancedPaymentText.clear();
        availabilityText.clear();
        balanceText.clear();
        fromDate.setValue(null);
        perDayRentText.clear();
        rentIdText.clear();
        totalText.clear();
        carIdComboBox.getSelectionModel().clearSelection();
        customerIdComboBox.getSelectionModel().clearSelection();
        carText.clear();
        customerNameText.clear();
        refundableDepositText.clear();

        enableFields();
    }

}
