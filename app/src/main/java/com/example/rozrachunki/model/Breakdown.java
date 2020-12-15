package com.example.rozrachunki.model;

public class Breakdown {
    private Integer id;

    private Integer idBorrower;

    private Integer amount;

    private Integer idPayment;

    private boolean settled;

    public Breakdown(Integer id, Integer idBorrower, Integer amount, Integer idPayment, boolean settled) {
        this.id = id;
        this.idBorrower = idBorrower;
        this.amount = amount;
        this.idPayment = idPayment;
        this.settled = settled;
    }

    public Breakdown() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdBorrower() {
        return idBorrower;
    }

    public void setIdBorrower(Integer idBorrower) {
        this.idBorrower = idBorrower;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
