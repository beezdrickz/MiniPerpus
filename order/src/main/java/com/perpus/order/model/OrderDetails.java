package com.perpus.order.model;


import jakarta.persistence.*;


@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderBook orderBook;

    @Column(name="id_book")
    private Long idBook;

    private int amount;

    public OrderDetails(){

    }

    public OrderDetails(Long id, OrderBook orderBook, Long idBook,int amount){
        this.id=id;
        this.orderBook=orderBook;
        this.id=idBook;
        this.amount=amount;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public Long getIdbook() {
        return idBook;
    }

    public void setIdbook(Long idBook) {
        this.idBook = idBook;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
