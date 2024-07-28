package com.perpus.order.dto.in;

public class    OrderDetailsRequest {

    private Long idBook;
    private int amount;

    public OrderDetailsRequest(){

    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
