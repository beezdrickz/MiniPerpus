package com.perpus.order.dto.in;

public class BookRequest {

    private Long id;
    private int stock;

    public BookRequest() {
    }

    public BookRequest(Long id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "{\"id\":" + String.valueOf(id) + ", \"stock\":" + String.valueOf(stock) + "}";
    }
}
