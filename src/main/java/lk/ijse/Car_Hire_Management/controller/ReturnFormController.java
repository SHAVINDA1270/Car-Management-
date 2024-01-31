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
import lk.ijse.Car_Hire_Management.dto.OverDueRentsTableDto;
import lk.ijse.Car_Hire_Management.dto.RentDto;
import lk.ijse.Car_Hire_Management.dto.RentTableModelDto;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.CarService;
import lk.ijse.Car_Hire_Management.service.custom.RentService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnFormController implements Initializable {

    private RentService rentService;

    private CarService carService;

    @FXML
    private TableColumn<OverDueRentsTableDto, Double> BalanceColumn;

    @FXML
    private TableColumn<OverDueRentsTableDto, String> CarColumn;

    @FXML
    private TableColumn<OverDueRentsTableDto, Integer> CarIDColumn;

    @FXML
    private TableColumn<OverDueRentsTableDto, Integer> CustomerIDColumn;

    @FXML
    private TableColumn<OverDueRentsTableDto, String> CustomerNameColumn;

    @FXML
    private TableColumn<OverDueRentsTableDto, LocalDate> DueDateColun;

    @FXML
    private TableView<OverDueRentsTableDto> OverDueTable;

    @FXML
    private TableColumn<OverDueRentsTableDto, Integer> RentIDColumn;


    @FXML
    private TextField balanceText;

    @FXML
    private ComboBox<Integer> rentIdComboBox;

    @FXML
    private TextField chargesForExtraDaysText;

    @FXML
    private TextField extraDaysText;

    @FXML
    private TextField grandTotalText;

    @FXML
    private TextField isReturnText;

    @FXML
    private DatePicker returnDatePicker;

    String selectedRentId;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rentService = ServiceFactory.getInstance().getService(ServiceType.RENT);

        carService = ServiceFactory.getInstance().getService(ServiceType.CAR);

        rentIdComboBox.getItems().clear();
        ObservableList<Integer> Ids = FXCollections.observableArrayList();

        List<RentDto> RentIds = rentService.getAllRent();

        for (RentDto rentDto : RentIds) {
            Ids.add(Integer.valueOf(rentDto.getId()));
        }

        rentIdComboBox.setItems(Ids);

        System.out.println(Ids);

        //LoadAllOverDueRents
        RentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        CarIDColumn.setCellValueFactory(new PropertyValueFactory<>("CarId"));
        CarColumn.setCellValueFactory(new PropertyValueFactory<>("Car"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        CustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_name"));
        DueDateColun.setCellValueFactory(new PropertyValueFactory<>("to_date"));
        BalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));


        LoadAllOverDueRents();


    }

    private void LoadAllOverDueRents() {
        List<RentDto> rentDtoList = rentService.getAllOverdueRents();

        ObservableList<OverDueRentsTableDto> overDueRentTableModelDtoList = FXCollections.observableArrayList();

        for (RentDto rentDto : rentDtoList) {
            OverDueRentsTableDto overDueRentsTableDto = new OverDueRentsTableDto(

                    rentDto.getId(),
                    rentDto.getCarId(),
                    rentDto.getCustomerId(),
                    rentDto.getTo_date(),
                    rentDto.getBalance(),
                    rentDto.getCustomer_name(),
                    rentDto.getCar()








            );

            overDueRentTableModelDtoList.add(overDueRentsTableDto);
        }

        OverDueTable.setItems(overDueRentTableModelDtoList);
    }


    public void MainMenuButton(ActionEvent actionEvent) throws IOException {
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

    @FXML
    void getBalannceAcordingToRentId(ActionEvent event) {
        try {
            // Assuming you have a method to retrieve the balance based on rentId
            selectedRentId = String.valueOf(rentIdComboBox.getValue());

            RentDto selectedRent = null;
            List<RentDto> rentIds = rentService.getAllRent();
            for (RentDto rentDto : rentIds) {
                if (String.valueOf(rentDto.getId()).equals(selectedRentId)) {
                    selectedRent = rentDto;
                    break;
                }
            }

            if (selectedRent != null) {
                String balance = String.valueOf(selectedRent.getBalance());
                balanceText.setText(String.valueOf(balance));
            }

            // Assuming balanceText is a TextField or a similar control

        } catch (Exception e) {
            // Handle exceptions, such as if the selectedRentId is not valid
            e.printStackTrace();
        }
    }

    public void calculateExtraDatesOnReturnDate(ActionEvent actionEvent) {
        if(selectedRentId != null){
            System.out.println("selectedRentId "+selectedRentId);
            LocalDate ReturnDate = returnDatePicker.getValue();
            System.out.println(ReturnDate);

            Integer rentId = Integer.valueOf(selectedRentId);

            Date Todate = rentService.getToDateById(rentId);
            System.out.println(Todate);


            Double ExtraDays = rentService.calculateExtraDates(Todate,ReturnDate);
            if(ExtraDays != null) {
                extraDaysText.setText(String.valueOf(ExtraDays));

                Double ExtraCharges = rentService.calculateChargesForExtraDates(ExtraDays,rentId);
                chargesForExtraDaysText.setText(String.valueOf(ExtraCharges));

                Double GrandTotal = rentService.calculateGrandTotal(ExtraCharges,rentId);
                grandTotalText.setText(String.valueOf(GrandTotal));
            }else{
                extraDaysText.setText("0");
                chargesForExtraDaysText.setText("0");
            }
        }else{
            returnDatePicker.setValue(null);
            new Alert(Alert.AlertType.WARNING, "Please Select RentId Frist").show();
        }




    }

    @FXML
    void returnACarButton(ActionEvent event) {
        Integer rentId = Integer.valueOf(selectedRentId);


        String isReturn = "Yes";


        String availability = "Yes";

        boolean isSaved = rentService.updateIsReturnByRentId(rentId,isReturn);
        boolean ispdated = carService.updateAvailabilityByRentId(rentId,availability);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Return Succcesfully Added").show();
            if (ispdated) {
                System.out.println("************Availability Updated******");
            }else{
                new Alert(Alert.AlertType.ERROR, "Can't Update New Availability").show();
            }
        }
    }
}
