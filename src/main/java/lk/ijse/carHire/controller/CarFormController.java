package lk.ijse.carHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.CarBo;
import lk.ijse.carHire.business.custom.impl.CarBoImpl;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.CarDto;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.dto.tm.CarTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CarFormController {
    public ComboBox cmbCategoryId;
    public Label labelCategoryName;
    public TableView<CarTm> tableCar;


    @FXML
    private ComboBox<String> comboAvialablity;

    @FXML
    private ComboBox<String> comboCategory;



    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMake;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPlate;

    @FXML
    private TextField txtRental;

    @FXML
    private TextField txtYear;

    @FXML
    private TableColumn<?, ?> colAvailable;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMake;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colRental;

    @FXML
    private TableColumn<?, ?> colYear;

    public void initialize(){

        System.out.println("Car Form Just Loaded");

        List<CategoryDto> categoryDtos =  loadCategoryIds();
        setCategoryIds(categoryDtos);

        comboAvialablity.setItems(FXCollections.observableArrayList("Yes","No"));

        setCellValueFactory();
        loadAllCars();


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMake.setCellValueFactory(new PropertyValueFactory<>("make"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colRental .setCellValueFactory(new PropertyValueFactory<>("dailyRental"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("Availability"));
    }

    private void loadAllCars() {

        try {
            List<CarDto> carDtoList = carBoImpl.getAllCars();
            ObservableList<CarTm> tableData = FXCollections.observableArrayList();

            for(CarDto dto : carDtoList){
                tableData.add(new CarTm(
                        dto.getCarId(),
                        dto.getMake(),
                        dto.getModel(),
                        dto.getYear(),
                        dto.getDailyRentalRate(),
                        dto.getAvailable()


                ));
                System.out.println(dto);
            }
            tableCar.setItems(tableData);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void setCategoryIds(List<CategoryDto> categoryDtos) {

        ObservableList<String> obList = FXCollections.observableArrayList();

        for(CategoryDto categoryDto : categoryDtos){
            obList.add(categoryDto.getId());
        }

        cmbCategoryId.setItems(obList);
    }

    private List<CategoryDto> loadCategoryIds() {

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        try {
            Connection con = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM carcategories";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rst =  pstm.executeQuery();



            while (rst.next()){
             categoryDtoList.add(new CategoryDto(
                      rst.getString(1),
                      rst.getString(2)
              ));
            }

            return categoryDtoList;


        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        return null;

    }



    public void txtIdOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();


        try {
          var  dto = carBoImpl.searchCar(id);
            if(dto != null){
                this.txtId.setText(dto.getCarId());
                this.cmbCategoryId.setValue(dto.getCategoryId());
                this.txtPlate.setText(dto.getLicensePlate());
                this.txtYear.setText(String.valueOf(dto.getYear()));
                this.txtMake.setText(dto.getMake());
                this.txtModel.setText(dto.getModel());
                this.txtRental.setText(String.valueOf(dto.getDailyRentalRate()));
                this.comboAvialablity.setValue(dto.getAvailable());



            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }






    }

    private CarBo carBoImpl = BoFactory.getBo(BoType.CAR);
    public void btnSaveOnAction(ActionEvent actionEvent) {

        String carId = txtId.getText();
        String licensePlate = txtPlate.getText();
        String make = txtMake.getText();
        String model = txtModel.getText();
        Integer year = Integer.parseInt(txtYear.getText());
        String categoryId = String.valueOf(cmbCategoryId.getValue());
        Double dailyRental = Double.parseDouble(txtRental.getText());
        String  available = comboAvialablity.getValue();

        var carDto = new CarDto(carId,
                licensePlate, make, model, year, categoryId, dailyRental, available );

        try {
            boolean isSaved = carBoImpl.saveCar(carDto);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Saved").show();
                clearFields();
               loadAllCars();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtId.setText("");
        txtPlate.setText("");
        txtMake.setText("");
        txtModel.setText("");
        txtYear.setText("");
        cmbCategoryId.getSelectionModel().clearSelection();
        cmbCategoryId.setPromptText("Category Id");
        txtRental.setText("");
        comboAvialablity.getSelectionModel().clearSelection();
        comboAvialablity.setPromptText("Availability");
        labelCategoryName.setText("");



    }




    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String carId = txtId.getText();
        String licensePlate = txtPlate.getText();
        String make = txtMake.getText();
        String model = txtModel.getText();
        Integer year = Integer.parseInt(txtYear.getText());
        String categoryId = String.valueOf(cmbCategoryId.getValue());
        Double dailyRental = Double.parseDouble(txtRental.getText());
        String  available = comboAvialablity.getValue();

        var carDto = new CarDto(carId, licensePlate, make, model,
                year, categoryId, dailyRental, available );

        try {
            boolean isUpdated = carBoImpl.updateCar(carDto);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Updated").show();
                clearFields();
                loadAllCars();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.CONFIRMATION,e.getMessage()).show();
        }


    }

    public void btnClearOnAction(ActionEvent actionEvent) {

        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        try {
            boolean isDeleted = carBoImpl.deleteCar(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Deleted").show();
                clearFields();
                loadAllCars();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {
        CarTm selectedCar = tableCar.getSelectionModel().getSelectedItem();

        String id = selectedCar.getId();


        try {
            CarDto dto = carBoImpl.searchCar(id);

            if(dto != null){
                this.txtId.setText(dto.getCarId());
                this.cmbCategoryId.setValue(dto.getCategoryId());
                this.txtPlate.setText(dto.getLicensePlate());
                this.txtYear.setText(String.valueOf(dto.getYear()));
                this.txtMake.setText(dto.getMake());
                this.txtModel.setText(dto.getModel());
                this.txtRental.setText(String.valueOf(dto.getDailyRentalRate()));
                this.comboAvialablity.setValue(dto.getAvailable());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void cmbSuppllierIdOnAction(ActionEvent actionEvent) {
       String categoryId = String.valueOf(cmbCategoryId.getValue());

        try {
            Connection con = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM carcategories WHERE CategoryID=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,categoryId);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
              var categoryDto =  new CategoryDto(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );

              fillCategoryLabel(categoryDto);

            } else{
              //  new Alert(Alert.AlertType.INFORMATION, "Category Didnt Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
        }

    }

    private void fillCategoryLabel(CategoryDto categoryDto) {
        labelCategoryName.setText(categoryDto.getCategoryName());
    }
}
