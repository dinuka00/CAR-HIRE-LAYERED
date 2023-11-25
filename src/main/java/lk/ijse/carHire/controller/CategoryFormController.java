package lk.ijse.carHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.CategoryBo;
import lk.ijse.carHire.business.custom.impl.CategoryBoImpl;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.dto.tm.CategoryTm;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryFormController {
    public AnchorPane rootNode;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CategoryTm> tableCategory;

    public  void initialize(){
        System.out.println("Category Form Just Loaded");

        setCellValueFactory();
        loadAllItems();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }

    private void loadAllItems() {


        try {
            List<CategoryDto> categoryList = categoryBoImpl.getAllCategories();
            ObservableList<CategoryTm> tableData = FXCollections.observableArrayList();

            for (CategoryDto dto : categoryList) {
                tableData.add(new CategoryTm(dto.getId(), dto.getCategoryName()));
            }
            tableCategory.setItems(tableData);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    private CategoryBo categoryBoImpl = BoFactory.getBo(BoType.CATEGORY);

    public void btnSaveOnAction(ActionEvent actionEvent)  {
       String id = txtId.getText();
       String categoryName = txtName.getText();

        CategoryDto categoryDto = new CategoryDto(id,categoryName);

       // CategoryBoImpl categoryBoImpl = new CategoryBoImpl();



        try {
           boolean isSaved = categoryBoImpl.saveCategory(categoryDto);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Category Saved").show();
                 clearFields();
                 loadAllItems();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }




    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {

        clearFields();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {


            String id = txtId.getText();


            try {
                CategoryDto dto = categoryBoImpl.searchCategory(id);
                if(dto != null){
                    this.txtName.setText(dto.getCategoryName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    private void setFields(String txtId, String txtName) {
        this.txtId.setText(txtId);
        this.txtName.setText(txtName);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String categoryName = txtName.getText();

       CategoryDto categoryDto = new CategoryDto(id,categoryName);

        try {
            boolean isSaved = categoryBoImpl.updateCategory(categoryDto);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Category Updated").show();
                clearFields();
                loadAllItems();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();


        try {
            boolean isDeleted = categoryBoImpl.deleteCategory(id);

            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Category Deleted").show();
                clearFields();
                loadAllItems();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Category Not Found").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



        }


    public void btnOnMouseCllicked(MouseEvent mouseEvent) {



       CategoryTm selectedCategory = tableCategory.getSelectionModel().getSelectedItem();

       String id = selectedCategory.getId();


        try {
            CategoryDto dto = categoryBoImpl.searchCategory(id);
            if(dto != null){
                this.txtId.setText(dto.getId());
                this.txtName.setText(dto.getCategoryName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }
}
