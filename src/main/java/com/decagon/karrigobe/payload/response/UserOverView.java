package com.decagon.karrigobe.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOverView {
    private String senderAddress;
    private String senderName;
    private String receiverAddress;
    private String receiverName;
    private DriverInfo driverInfo;
    private DeliveryStatus deliveryStatus;
}
