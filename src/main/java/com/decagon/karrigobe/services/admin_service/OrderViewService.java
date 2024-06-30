package com.decagon.karrigobe.services.admin_service;

import com.decagon.karrigobe.payload.response.AllOrderResponse;
import com.decagon.karrigobe.payload.response.GeneralOrderResponse;

import java.util.List;

public interface OrderViewService {
    List<AllOrderResponse> searchByEmail(String searchMethod, int pageNo, int pageSize);
    List<AllOrderResponse> searchByTrackingNum(String trackingNum);
    GeneralOrderResponse getAllOrders(int pageNo, int pageSize);
}
