package com.decagon.karrigobe.services.admin_service;

import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.payload.response.DriverTaskResponse;
import com.decagon.karrigobe.payload.response.PaginatedResponse;
import com.decagon.karrigobe.payload.response.UserPageDTO;
import com.decagon.karrigobe.payload.response.UserResponse;

import java.util.List;

public interface AdminService {

    UserPageDTO getAllUsers(int pageNO, int pageSize, String sortBy, String sortDir);

    //PaginatedResponse<UserResponse> pageView(String role, Integer pageNo, Integer pageSize);

    String disableADriversAccount(Long driverId);
//    List<UserEntity> getAllDrivers();
    PaginatedResponse<UserResponse> pageView(String role, int pageNo, int pageSize, String sortBy);
    PaginatedResponse<DriverTaskResponse> pageTasks(Long driverId, int pageNo, int pageSize, String sortBy);
}
