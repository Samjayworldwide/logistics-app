package com.decagon.karrigobe.controllers.admin_controller;

import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.payload.response.*;
import com.decagon.karrigobe.services.admin_service.AdminService;
import com.decagon.karrigobe.commons.PageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminUserController {

   private final AdminService adminService;

    @GetMapping("/user-page")
    private ResponseEntity<UserPageDTO> getAllUsersByAdmin(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        UserPageDTO userPageDTO = adminService.getAllUsers(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(userPageDTO);
    }

//    @GetMapping("/get_all_drivers")
//    public List<UserEntity> getAllDrivers(){
//        return adminService.getAllDrivers();
//    }

    @PostMapping("/driver_record_status/{driverId}")
    public ResponseEntity<ApiResponse<String>> adminToDisableADriver(@PathVariable("driverId") Long driverId){
        return ResponseEntity.ok(new ApiResponse<>(adminService.disableADriversAccount(driverId)));
    }

    @GetMapping("/drivers_page")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> pageDrivers(@RequestParam("role") String role,
                                                                       @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                       @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                       @RequestParam(value = "sortBy", defaultValue = "firstName") String sortBy){
        PaginatedResponse<UserResponse> response = adminService.pageView(role,pageNo,pageSize,sortBy);
        return ResponseEntity.ok().body(new ApiResponse<>("Success", response));
    }
    @GetMapping("/tasks_page")
    public ResponseEntity<ApiResponse<PaginatedResponse<DriverTaskResponse>>> getTasks(@RequestParam("driverId") Long driverId,
                                                                          @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                          @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                          @RequestParam(value = "sortBy", defaultValue = "dateCreated") String sortBy) {
        PaginatedResponse<DriverTaskResponse> response = adminService.pageTasks(driverId, pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(new ApiResponse<>("Success", response));
    }
//    @GetMapping("/page_users/{page_no}/{page_size}")
//    public ResponseEntity<ApiResponse<List<UserResponse>>> pageDrivers(@RequestParam("role") String roles,
//                                                                       @PathVariable Integer page_no,
//                                                                       @PathVariable Integer page_size){
//        return ResponseEntity.ok().body(new ApiResponse<>("Successful", adminService.pageView(roles, page_no, page_size)));
//    }
//    @GetMapping("/page_tasks/{driverId}/{page_no}/{page_size}")
//    public ResponseEntity<ApiResponse<List<DriverTaskResponse>>> pagingTasks(@PathVariable Long driverId,
//                                                                             @PathVariable Integer page_no,
//                                                                             @PathVariable Integer page_size){
//        return ResponseEntity.ok().body(new ApiResponse<>("Successful", adminService.pageTasks(driverId, page_no, page_size)));
//    }

}
