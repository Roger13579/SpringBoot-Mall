package com.rogerli.springmall.model;

import lombok.Data;

@Data
public class OrderItemView {

    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
    private String productName;
    private String imageUrl;

}
