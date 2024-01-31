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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.*;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.CarCategoryService;
import lk.ijse.Car_Hire_Management.service.custom.CarService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class CarFormController implements Initializable {

    CarService carService;

    CarCategoryService carCategoryService;


    @FXML
    private TextField carBrandText;

    @FXML
    private ComboBox<String> carCategoryComboBox;

    @FXML
    private TextField carIdText;

    @FXML
    private TextField carModelText;

    @FXML
    private TextField carPricePerDayText;

    @FXML
    private TextField carYearText;

    @FXML
    private TextField vehicleNameText;

    @FXML
    private RadioButton availabilityNo;

    @FXML
    private RadioButton availabilityYes;

    @FXML
    private TextField refundableDepositText;

    @FXML
    private TableColumn<CarTableModelDto, Integer> carIdColumn;

    @FXML
    private TableColumn<CarTableModelDto, String> yearColumn;

    @FXML
    private TableColumn<CarTableModelDto, String> modelColumn;

    @FXML
    private TableColumn<CarTableModelDto, String> carNumberColumn;

    @FXML
    private TableColumn<CarTableModelDto, Double> pricePerDayColumn;

    @FXML
    private TableView<CarTableModelDto> carTable;

    @FXML
    private TableColumn<CarTableModelDto, String> brandColumn;

    @FXML
    private TableColumn<CarTableModelDto, String> RefundableDepositColumn;

    @FXML
    private TableColumn<CarTableModelDto, String> availabilityColumn;

    @FXML
    private TextField carNumberText;


    private ToggleGroup toggleGroup = new ToggleGroup();
    private MouseEvent event;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            carService = ServiceFactory.getInstance().getService(ServiceType.CAR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            carCategoryService = ServiceFactory.getInstance().getService(ServiceType.CAR_CATEGORY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        carCategoryComboBox.getItems().clear();
        ObservableList<String> carCategories = FXCollections.observableArrayList();

        List<CarCategoryDto> categories = carCategoryService.getAllCategory();

        for (CarCategoryDto category : categories) {
            carCategories.add(category.getName());
        }

        carCategoryComboBox.setItems(carCategories);

        System.out.println(carCategories);

        availabilityYes.setToggleGroup(toggleGroup);
        availabilityNo.setToggleGroup(toggleGroup);


        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        pricePerDayColumn.setCellValueFactory(new PropertyValueFactory<>("Price_per_day"));
        RefundableDepositColumn.setCellValueFactory(new PropertyValueFactory<>("refundable_Deposit"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carNumberColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle_number"));



        loadAllCars();
    }

    private void loadAllCars() throws SQLException {
        List<CarDto> carDtoList = carService.getAllCar();

        ObservableList<CarTableModelDto> customerTableModelList = FXCollections.observableArrayList();

        for (CarDto carDto : carDtoList) {
            CarTableModelDto carTableModel = new CarTableModelDto(
                    carDto.getId(),
                    carDto.getBrand(),
                    carDto.getModel(),
                    carDto.getYear(),
                    carDto.getCar_number(),
                    carDto.getPrice_per_day(),
                    carDto.getAvailability(),
                    carDto.getRefundable_Deposit()
            );

            customerTableModelList.add(carTableModel);
        }

        carTable.setItems(customerTableModelList);
    }


    @FXML
    void carTableOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            setCars();
        }
    }

    private void setCars() {
        CarTableModelDto  carTableModelDto = carTable.getSelectionModel().getSelectedItem();

        carIdText.setText(String.valueOf(carTableModelDto.getId()));
        carBrandText.setText(carTableModelDto.getBrand());
        carModelText.setText(carTableModelDto.getModel());
        carYearText.setText(carTableModelDto.getYear());
        carNumberText.setText(carTableModelDto.getVehicle_number());
        carPricePerDayText.setText(String.valueOf(carTableModelDto.getPrice_per_day()));
        refundableDepositText.setText(String.valueOf(carTableModelDto.getRefundable_Deposit()));
    }

    void addCar(Integer categoryId) {
        // Implement your logic to add the car with the categoryId
        System.out.println("add Car m Adding car with categoryId: " + categoryId);
        // Add your implementation here
    }
//        Integer Id = null;
//        System.out.println("Car Form Controller: Add Car method is running");
//
//        RadioButton selectRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
//        String setAvailability = selectRadioButton.getText();
//
//        try {
//            CarDto carDto = new CarDto();
//
//            carDto.setBrand(carBrandText.getText());
//            carDto.setModel(carModelText.getText());
//            carDto.setYear(carYearText.getText());
//            carDto.setVehicle_number(vehicleNameText.getText());
//            carDto.setPrice_per_day(Double.valueOf(carPricePerDayText.getText()));
//            carDto.setAvailability(setAvailability);
//
//
//            String selectedCategory = carCategoryComboBox.getValue();
//            Integer selectedCarCategoryId = null;
//            if (selectedCategory != null) {
//            }
//
//            boolean isSaved = carService.saveCar(Id, carDto);
//            if (isSaved) {
//                new Alert(Alert.AlertType.INFORMATION, "Saved car successfully!").show();
//                resetFields();
//
//            }
//        } catch (Exception exception) {
//            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
//            System.out.println(exception.getMessage());
//
//        }






    private void resetFields() {
        carIdText.clear();
        carBrandText.clear();
        carModelText.clear();
        carYearText.clear();
        vehicleNameText.clear();
        carPricePerDayText.clear();

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

    public void carCategoryOnAction(ActionEvent actionEvent) {
        String selectedCarCategory = carCategoryComboBox.getValue();
        vehicleNameText.setText(selectedCarCategory);

        if (selectedCarCategory != null) {
            System.out.println("Selected Car Category: " + selectedCarCategory);

            CarCategoryDto selectedCategoryDto = carCategoryService.getCarCategoryByName(selectedCarCategory);
            if (selectedCategoryDto != null) {
                Integer categoryId = selectedCategoryDto.getId();
                System.out.println("Selected Car Category ID: " + categoryId);


            } else {
                System.out.println("Error: Selected Car Category not found");
            }
        } else {
            System.out.println("No Car Category selected");
        }


    }

    public void addCar() throws SQLException {

        String selectedCarCategory = carCategoryComboBox.getValue();

        if (selectedCarCategory != null) {
            System.out.println("Selected Car Category: " + selectedCarCategory);


            CarCategoryDto selectedCategoryDto = carCategoryService.getCarCategoryByName(selectedCarCategory);
            if (selectedCategoryDto != null) {
                Integer categoryId = selectedCategoryDto.getId();
                System.out.println("Selected Car Category ID: " + categoryId);

                try{
                    RadioButton selectedRadioBtn = (RadioButton) toggleGroup.getSelectedToggle();
                    String selectedAvailability = selectedRadioBtn.getText();

                    CarDto carDto = new CarDto();
                    carDto.setId(Integer.valueOf(carIdText.getText()));
                    carDto.setBrand(carBrandText.getText());
                    carDto.setModel(carModelText.getText());
                    carDto.setYear(carYearText.getText());
                    carDto.setVehicle_category(vehicleNameText.getText());
                    carDto.setCar_number(carNumberText.getText());
                    carDto.setPrice_per_day(Double.valueOf(carPricePerDayText.getText()));
                    carDto.setAvailability(selectedAvailability);
                    carDto.setCarCategoryId(categoryId);
                    carDto.setRefundable_Deposit(Double.valueOf(refundableDepositText.getText()));

                    boolean isSaved = carService.saveCar(carDto);
                    if (isSaved) {
                        loadAllCars();
                        resetFields();
                        new Alert(Alert.AlertType.INFORMATION, "Saved Car successfully!").show();

                    }

                }
                catch (Exception exception){
                    throw exception;
                }

            } else {
                System.out.println("Error: Selected Car Category not found");
            }
        } else {
            System.out.println("No Car Category selected");
        }

    }


    public void updateCar(ActionEvent actionEvent) {

        String selectedCarCategory = carCategoryComboBox.getValue();

        String selectedCarId = carIdText.getText();

        if (selectedCarCategory != null && selectedCarId != null) {
            System.out.println("Selected Car Category: " + selectedCarCategory );


            CarCategoryDto selectedCategoryDto = carCategoryService.getCarCategoryByName(selectedCarCategory);
            if (selectedCategoryDto != null) {
                Integer categoryId = selectedCategoryDto.getId();
                System.out.println("Selected Car Category ID: " + categoryId+ "    \nSelected Car Id:"+selectedCarId);

                try{
                    RadioButton selectedRadioBtn = (RadioButton) toggleGroup.getSelectedToggle();
                    String selectedAvailability = selectedRadioBtn.getText();

                    CarDto carDto = new CarDto();
                    carDto.setId(Integer.valueOf(carIdText.getText()));;
                    carDto.setBrand(carBrandText.getText());
                    carDto.setModel(carModelText.getText());
                    carDto.setYear(carYearText.getText());
                    carDto.setVehicle_category(vehicleNameText.getText());
                    carDto.setCar_number(carNumberText.getText());
                    carDto.setPrice_per_day(Double.valueOf(carPricePerDayText.getText()));
                    carDto.setAvailability(selectedAvailability);
                    carDto.setCarCategoryId(categoryId);
                    carDto.setRefundable_Deposit(Double.valueOf(refundableDepositText.getText()));

                    if(carDto != null){
                        boolean isUpdate = carService.updateCar(carDto);
                        if (isUpdate) {
                            loadAllCars();
                            resetFields();
                            new Alert(Alert.AlertType.INFORMATION, "Updated Car successfully!").show();

                        }else{
                            new Alert(Alert.AlertType.WARNING, "Can't Update").show();
                        }

                    }else{
                        new Alert(Alert.AlertType.WARNING, "Carcategory Dto null").show();
                    }


                }
                catch (Exception exception){

                }

            } else {
                System.out.println("Error: Selected Car Category not found");
            }
        } else {
            System.out.println("No Car Category selected");
        }
    }

    public void deleteCar() {
        try {
            Integer carId = Integer.valueOf(carIdText.getText());
            System.out.println("carId Delete Controller "+carId);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this Car?");
            alert.setContentText("This action cannot be undone.");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonTypeYes) {
                boolean isDeleted = carService.deleteCar(carId);
                if(isDeleted){
                    resetFields();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Car Successfully!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Delete this car\n" +
                            "If it is done, it cannot be deleted because the records in\n the related rent table will be deleted.").show();

                }


                loadAllCars();}

        } catch (Exception exception) {
            new Alert(Alert.AlertType.ERROR, "Delete this car\n" +
                    "If it is done, it cannot be deleted because the records in\n the related rent table will be deleted.").show();

        }
    }
}