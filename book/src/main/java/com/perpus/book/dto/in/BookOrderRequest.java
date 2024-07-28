package com.perpus.book.dto.in;

public class BookOrderRequest {

    private Long id;
    private int stock;

    public BookOrderRequest() {
    }

    public BookOrderRequest(Long id, int stock) {
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
}
