package com.example.rozrachunki.model;

import java.util.Date;

public class Payment {
    private Integer id;

    private Integer idGroup;

    private Integer paidBy;

    private Integer amount;

    private String description;

    private Date date;

    private byte[] image;

    private String note;

    private boolean settled = false;

    private Integer payment_option;

    public Payment(Integer id, Integer idGroup, Integer paidBy, Integer amount, String description, Date date, byte[] image, String note, boolean settled, Integer payment_option) {
        this.id = id;
        this.idGroup = idGroup;
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.image = image;
        this.note = note;
        this.settled = settled;
        this.payment_option = payment_option;
    }

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public Integer getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Integer paidBy) {
        this.paidBy = paidBy;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Integer getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(Integer payment_option) {
        this.payment_option = payment_option;
    }
}
