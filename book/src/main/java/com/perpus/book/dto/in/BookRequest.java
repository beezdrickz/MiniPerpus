package com.perpus.book.dto.in;

public class BookRequest {


    private String title;
    private String author;
    private int stock;

    public BookRequest(){

    }

    public BookRequest( String title, String author, int stock) {

        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
