package com.seekerscloud.pos.controller;

import com.jfoenix.bindings.base.IPropertyConverter;
import com.jfoenix.controls.JFXButton;
import com.seekerscloud.pos.db.DataBase;
import com.seekerscloud.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import modal.Customer;

import java.io.IOException;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public JFXButton btnSaveCustomer;
    public AnchorPane customerFormContext;
    public TextField txtSearch;

    private String searchText="";

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        searchCustomer(searchText);

        tblCustomer.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newVAlue) -> {
                    if (null!=newVAlue) {
                        setData((CustomerTm) newVAlue);
                    }

                });

        txtSearch.textProperty()
                .addListener((observable, oldValue, newVAlue) -> {
                    searchText=newVAlue;
                    searchCustomer(searchText);
            });
    }

    private void setData(CustomerTm tm){
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtSalary.setText(String.valueOf(tm.getSalary()));
        btnSaveCustomer.setText("Update Customer");
    }

    private void searchCustomer(String text){
        ObservableList<CustomerTm> tmList= FXCollections.observableArrayList();
            for (Customer c : DataBase.customerTable
            ) {
                if (c.getName().toLowerCase().contains(text.toLowerCase()) ||
                        c.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    Button btn = new Button("Delete");
                    CustomerTm tm = new CustomerTm(c.getId(), c.getName(), c.getAddress(), c.getSalary(), btn);
                    tmList.add(tm);

                    btn.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure whether do you want to delete this customer?",
                                ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.YES) {
                            boolean isDeleted = DataBase.customerTable.remove(c);
                            if (isDeleted) {
                                searchCustomer(searchText);
                                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                            }
                        }

                    });
                }
            }
            tblCustomer.setItems(tmList);
    }

    public void saveCustomersOnAction(ActionEvent actionEvent) {
        Customer c1= new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSalary.getText()));

        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")){
            boolean isSaved = DataBase.customerTable.add(c1);
            if (isSaved){
                searchCustomer(searchText);
                clearField();
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }
        }else {
            for (int i = 0; i < DataBase.customerTable.size(); i++ ){
                if (txtId.getText().equalsIgnoreCase(DataBase.customerTable.get(i).getId())){
                    DataBase.customerTable.get(i).setName(txtName.getText());
                    DataBase.customerTable.get(i).setAddress(txtAddress.getText());
                    DataBase.customerTable.get(i).setSalary(Double.parseDouble(txtSalary.getText()));
                    searchCustomer(searchText);
                    new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                    clearField();
                }
            }
        }
    }

    private void clearField(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
        clearField();
    }
}
