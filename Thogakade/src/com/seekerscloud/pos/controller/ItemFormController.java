package com.seekerscloud.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.seekerscloud.pos.db.DataBase;
import com.seekerscloud.pos.view.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modal.Item;

import java.io.IOException;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane itemFormContext;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public JFXButton btnSaveItem;
    public TextField txtSearch;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOptions;
    public TableView tblItem;
    String searchCode="";

    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));


        searchItem(searchCode);

        tblItem.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newVAlue) -> {
                    if (null != newVAlue) {
                        setData((ItemTm) newVAlue);
                    }

                });

        txtSearch.textProperty()
                .addListener((observable, oldValue, newVAlue) -> {
                    searchCode = newVAlue;
                    searchItem(searchCode);
                });
    }

    private void setData(ItemTm tm) {
        txtCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(String.valueOf(tm.getQtyOnHand())));
        btnSaveItem.setText("Update Customer");
    }

    private void searchItem(String text) {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        for (Item i : DataBase.itemTable
        ) {
            if (i.getCode().toLowerCase().contains(text.toLowerCase()) ||
                    i.getDescription().toLowerCase().contains(text.toLowerCase())) {
                Button btn = new Button("Delete");
                ItemTm tm = new ItemTm(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQtyOnHand(), btn);
                tmList.add(tm);

                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure whether do you want to delete this Item?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        boolean isDeleted = DataBase.itemTable.remove(i);
                        if (isDeleted) {
                            searchItem(searchCode);
                            new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    }

                });
            }
        }
        tblItem.setItems(tmList);
    }

    public void saveItemOnAction(ActionEvent actionEvent) {
        Item itm = new Item(txtCode.getText(), txtDescription.getText(), Double.parseDouble(txtQtyOnHand.getText()), Integer.parseInt(txtUnitPrice.getText()));

        if (btnSaveItem.getText().equalsIgnoreCase("Save Item")) {
            boolean isSaved = DataBase.itemTable.add(itm);
            if (isSaved) {
                searchItem(searchCode);
                clearField();
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } else {
            for (int i = 0; i < DataBase.itemTable.size(); i++) {
                if (txtCode.getText().equalsIgnoreCase(DataBase.itemTable.get(i).getCode())) {
                    DataBase.itemTable.get(i).setDescription(txtDescription.getText());
                    DataBase.itemTable.get(i).setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                    DataBase.itemTable.get(i).setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    searchItem(searchCode);
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                    clearField();
                }
            }
        }
    }

    private void clearField() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnSaveItem.setText("Save Item");
        clearField();
    }
}
