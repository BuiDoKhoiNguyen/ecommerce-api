package com.rs.ecommerceapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentInformation {
    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "cvv")
    private String cvv;

    public PaymentInformation() {
    }

    public PaymentInformation(String cardholderName, String cardNumber, String expiryDate, String cvv) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
