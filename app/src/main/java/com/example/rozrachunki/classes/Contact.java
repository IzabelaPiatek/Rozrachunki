package com.example.rozrachunki.classes;

public class Contact {

    private String name;
    private String phone;
    private String photo;

    public Contact(){

    }
    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
