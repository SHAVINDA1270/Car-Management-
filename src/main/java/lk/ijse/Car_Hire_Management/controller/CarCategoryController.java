package lk.ijse.Car_Hire_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.Car_Hire_Management.dto.CarCategoryDto;
import lk.ijse.Car_Hire_Management.dto.CarCategoryTableModel;
import lk.ijse.Car_Hire_Management.dto.CarDto;
import lk.ijse.Car_Hire_Management.dto.UserDto;
import lk.ijse.Car_Hire_Management.service.ServiceFactory;
import lk.ijse.Car_Hire_Management.service.custom.CarCategoryService;
import lk.ijse.Car_Hire_Management.service.util.ServiceType;

import java.io.IOException;
import java.util.List;

public class CarCategoryController {

    private CarCategoryService carCategoryService;

    @FXML
    private TableView<CarCategoryTableModel> carCategoryTable;

    @FXML
    private TableColumn<CarCategoryTableModel, String> categoryID;

    @FXML
    private TextField categoryIdText;

    @FXML
    private TableColumn<CarCategoryTableModel, String> categoryName;

    @FXML
    private TextField categoryNameText;

    public void initialize() throws Exception {
        carCategoryService = ServiceFactory.getInstance().getService(ServiceType.CAR_CATEGORY);
        System.out.println("Car Category Form Loaded");

        categoryID.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadAllCarCategories();
    }

    @FXML
    void categoryTableMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            searchCarCategory();
        }
    }

    @FXML
    void registerNewCarCategory() {
        String newCarCategory = categoryNameText.getText().trim().toLowerCase();

        List<CarCategoryDto> carCategoryDtos = carCategoryService.getAllCategory();
        boolean categoryExists = false;

        for (CarCategoryDto carCategoryDto1 : carCategoryDtos) {
            if (carCategoryDto1.getName().trim().toLowerCase().equals(newCarCategory)) {
                categoryExists = true;
                break;
            }
        }

        if (categoryExists) {
            new Alert(Alert.AlertType.WARNING, "Car Category already exists!").show();
        } else {
            CarCategoryDto carCategoryDto = new CarCategoryDto();

            carCategoryDto.setId(Integer.valueOf(categoryIdText.getText()));
            carCategoryDto.setName(categoryNameText.getText());

            boolean isSaved = carCategoryService.saveCategory(carCategoryDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Car Category successfully!").show();
                clear();
                loadAllCarCategories();
            }
        }
    }

    private void searchCarCategory() {
        CarCategoryTableModel carCategoryTableModel = carCategoryTable.getSelectionModel().getSelectedItem();

        categoryIdText.setText(String.valueOf(Integer.valueOf(carCategoryTableModel.getId())));
        categoryNameText.setText(carCategoryTableModel.getName());
    }

    private void loadAllCarCategories() {
        List<CarCategoryDto> carCategoryDtoList = carCategoryService.getAllCategory();
        ObservableList<CarCategoryTableModel> carCategoryTableModelList = FXCollections.observableArrayList();

        for (CarCategoryDto carCategoryDto : carCategoryDtoList) {
            CarCategoryTableModel carCategoryTableModel = new CarCategoryTableModel(
                    carCategoryDto.getId(),
                    carCategoryDto.getName()
            );

            carCategoryTableModelList.add(carCategoryTableModel);
        }

        carCategoryTable.setItems(carCategoryTableModelList);
    }

    public void clear(){
        categoryIdText.setText("");
        categoryNameText.setText("");
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

    public void updateCarCategory(ActionEvent actionEvent) {
        String newCarCategory = categoryNameText.getText().trim().toLowerCase();

        List<CarCategoryDto> carCategoryDtos = carCategoryService.getAllCategory();
        boolean categoryExists = false;

        for (CarCategoryDto carCategoryDto1 : carCategoryDtos) {
            if (carCategoryDto1.getName().trim().toLowerCase().equals(newCarCategory)) {
                categoryExists = true;
                break;
            }
        }

        if (categoryExists) {
            new Alert(Alert.AlertType.WARNING, "Car Category already exists!").show();
        } else {
            CarCategoryDto carCategoryDto = new CarCategoryDto();

            carCategoryDto.setId(Integer.valueOf(categoryIdText.getText()));
            carCategoryDto.setName(categoryNameText.getText());

            boolean isSaved = carCategoryService.UpdateCAtegory(carCategoryDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Car Category successfully!").show();
                clear();
                loadAllCarCategories();
            }
        }
    }
}
