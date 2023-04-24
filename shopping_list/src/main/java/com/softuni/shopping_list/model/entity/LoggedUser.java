package com.softuni.shopping_list.model.entity;


public class LoggedUser {

    private Long id;

    public LoggedUser() {
    }

    public Long getId() {
        return id;
    }

    public LoggedUser setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isEmpty(){
        return this.id == null;
    }

    public void logoutUser(){
        this.id = null;
    }



}
