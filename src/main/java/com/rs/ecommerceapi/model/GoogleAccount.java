package com.rs.ecommerceapi.model;

public class GoogleAccount {
    private Long id;
    private String email;
    private String name;
    private String firstName;
    private String lastName;

    public GoogleAccount() {
    }

    public GoogleAccount(Long id, String email, String name, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
