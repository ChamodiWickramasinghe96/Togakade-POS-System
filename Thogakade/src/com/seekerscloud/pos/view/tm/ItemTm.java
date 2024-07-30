package com.seekerscloud.pos.view.tm;

import javafx.scene.control.Button;

public class ItemTm {
    private String code;
    private String Description;
    private double unitPrice;
    private int qtyOnHand;

    private Button btn;


    public ItemTm() {
    }

    public ItemTm(String code, String description, double unitPrice, int qtyOnHand,Button btn) {
        this.code = code;
        Description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }



}
