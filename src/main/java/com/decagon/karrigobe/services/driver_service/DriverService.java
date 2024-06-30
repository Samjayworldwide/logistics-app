package com.decagon.karrigobe.services.driver_service;

import com.decagon.karrigobe.entities.model.DriverTaskEntity;
import com.decagon.karrigobe.entities.model.OrderEntity;
import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.payload.response.UserOrderPage;

import java.util.List;

public interface DriverService {
    void generateRandomOrder();
    void assignTaskToDriver(List<UserEntity> driverList, OrderEntity order);
    List<DriverTaskEntity> viewAllOrdersInDriversTask();
    UserOrderPage pageOrders(Integer pageNo, Integer pageSize);
}
