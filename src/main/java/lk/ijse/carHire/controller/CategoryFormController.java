package lk.ijse.carHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.CategoryBo;
import lk.ijse.carHire.business.custom.impl.CategoryBoImpl;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.dto.tm.CategoryTm;

import java.sql.*;

public class CategoryFormController {
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
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Category Not Found").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



        }



}
