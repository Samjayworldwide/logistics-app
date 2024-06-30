package com.decagon.karrigobe.controllers.driver_controller;

import com.decagon.karrigobe.commons.PageConstant;
import com.decagon.karrigobe.payload.response.ApiResponse;
import com.decagon.karrigobe.payload.response.UserOrderPage;
import com.decagon.karrigobe.services.driver_service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverOrderController {
    private final DriverService driverService;

    @GetMapping("/orders")
    private ResponseEntity<ApiResponse<UserOrderPage>> getAllOrdersByAdmin(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ){
        return ResponseEntity.ok().body(new ApiResponse<>("Success", driverService.pageOrders(pageNo, pageSize)));
    }
}
