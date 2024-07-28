package com.perpus.order.dto.in;

import java.util.Date;
import java.util.List;

public class OrderRequest {

    private String orderNumber;
    private List<OrderDetailsRequest> orderDetails;
    private Long idUser;
    private Date endDate;

    public OrderRequest(){

    }

    public List<OrderDetailsRequest> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsRequest> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
