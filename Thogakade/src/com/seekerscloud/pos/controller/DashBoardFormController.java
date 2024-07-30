package com.seekerscloud.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardFormController {
    public Label lblTime;
    public AnchorPane dashboardContext;
    public Label lblDate;

    public void initialize(){
        setDateAndTime(lblTime);
    }

    private void setDateAndTime(Label label){
        //setTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            label.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    public void openItemManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ItemForm");
    }

    public void openOderDetailsOnAction(ActionEvent actionEvent) {
    }

    public void openPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void openCustomerManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/seekerscloud/pos/view/"+ui+".fxml"))));
        stage.centerOnScreen();
    }

}
