package com.perpus.order.dto.out;

import java.util.Date;

public class OrderResult {
    private String orderNumber;

    public OrderResult(){

    }
    public OrderResult(String orderNumber, Date endDate) {
        this.orderNumber = orderNumber;
        EndDate = endDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    private Date EndDate;
}
