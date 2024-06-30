package com.decagon.karrigobe.payload.response;

import com.decagon.karrigobe.entities.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderResponse {
    private Long orderId;
    private String receiver;
    private String dropOffLocation;
    private String pickUpLocation;
    private String sender;
    private String date;
    private OrderStatus status;
    private String amount;
    private String itemName;
    private String trackingNum;
}
