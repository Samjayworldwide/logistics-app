package com.decagon.karrigobe.services.admin_service.serviceImplementation;

import com.decagon.karrigobe.entities.enums.RecordStatusConstant;
import com.decagon.karrigobe.entities.model.OrderEntity;
import com.decagon.karrigobe.exceptions.OrderNotFoundException;
import com.decagon.karrigobe.payload.response.AllOrderResponse;
import com.decagon.karrigobe.payload.response.DriverResponses;
import com.decagon.karrigobe.payload.response.GeneralOrderResponse;
import com.decagon.karrigobe.payload.response.UserResponse;
import com.decagon.karrigobe.repositories.OrderRepository;
import com.decagon.karrigobe.services.admin_service.OrderViewService;
import com.decagon.karrigobe.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderViewImpl implements OrderViewService {
    private final OrderRepository orderRepo;
    private final ModelMapper mapper = new ModelMapper();


    @Override
    public GeneralOrderResponse getAllOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<OrderEntity> orders = orderRepo.findOrderEntitiesByRecordStatusOrderByDateCreatedDesc(RecordStatusConstant.ACTIVE, pageable);

        List<OrderEntity> listOfOrders = orders.getContent();

        List<AllOrderResponse> responses = new ArrayList<>();
        for (OrderEntity order : listOfOrders) {
            DriverResponses.DriverResponsesBuilder driverResponseBuilder = createDriverResponseBuilder(order);

            AllOrderResponse allOrderResponse = AllOrderResponse.builder()
                    .trackingNum(order.getTrackingNum())
                    .orderDate(DateUtils.toDateString(order.getDateCreated()))
                    .status(order.getStatus())
                    .pickUpLocation(order.getOrderDescriptionEntity().getPickUpLocation())
                    .dropOffLocation(order.getOrderDescriptionEntity().getDropOffLocation())
                    .user(mapper.map(order.getUserEntity(), UserResponse.class))
                    .driver(driverResponseBuilder.build())
                    .build();
            responses.add(allOrderResponse);
        }

        return GeneralOrderResponse.builder()
                .pageNo(orders.getNumber())
                .pageSize(orders.getSize())
                .totalPage(orders.getTotalPages())
                .lastPage(orders.isLast())
                .generalOrders(responses)
                .build();
    }

    @Override
    public List<AllOrderResponse> searchByEmail(String email, int pageNo, int pageSize) {
        if (email == null || email.trim().isEmpty()) {
            log.error("Invalid email input: {}", email);
            throw new OrderNotFoundException("No order with email address " + email);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<OrderEntity> result = orderRepo.findAllByUserEntity_EmailOrderByDateCreated(email, pageable);

        return result.isEmpty() ? new ArrayList<>()
                : result.stream().map(this::createAllOrderResponse).toList();
    }

    private AllOrderResponse createAllOrderResponse(OrderEntity order) {
        DriverResponses.DriverResponsesBuilder driverResponseBuilder = createDriverResponseBuilder(order);

        return AllOrderResponse.builder()
                .trackingNum(order.getTrackingNum())
                .orderDate(DateUtils.toDateString(order.getDateCreated()))
                .status(order.getStatus())
                .pickUpLocation(order.getOrderDescriptionEntity().getPickUpLocation())
                .dropOffLocation(order.getOrderDescriptionEntity().getDropOffLocation())
                .user(mapper.map(order.getUserEntity(), UserResponse.class))
                .driver(driverResponseBuilder.build())
                .build();
    }

    @Override
    public List<AllOrderResponse> searchByTrackingNum(String trackingNum) {
        System.out.println("Debugging");
        OrderEntity order = orderRepo.findByTrackingNum(trackingNum)
                .orElseThrow(() -> new OrderNotFoundException("No order with tracking " + trackingNum));

        System.out.println("Debugging 11");

        DriverResponses.DriverResponsesBuilder driverResponseBuilder = createDriverResponseBuilder(order);
//
//        return AllOrderResponse.builder()
//                .trackingNum(order.getTrackingNum())
//                .orderDate(DateUtils.toDateString(order.getDateCreated()))
//                .status(order.getStatus())
//                .pickUpLocation(order.getOrderDescriptionEntity().getPickUpLocation())
//                .dropOffLocation(order.getOrderDescriptionEntity().getDropOffLocation())
//                .user(mapper.map(order.getUserEntity(), UserResponse.class))
//                .driver(driverResponseBuilder.build())
//                .build();

        return new ArrayList<>(List.of(AllOrderResponse.builder()
        .trackingNum(order.getTrackingNum())
        .orderDate(DateUtils.toDateString(order.getDateCreated()))
        .status(order.getStatus())
        .pickUpLocation(order.getOrderDescriptionEntity().getPickUpLocation())
        .dropOffLocation(order.getOrderDescriptionEntity().getDropOffLocation())
        .user(mapper.map(order.getUserEntity(), UserResponse.class))
        .driver(driverResponseBuilder.build())
        .build()));
    }

    private DriverResponses.DriverResponsesBuilder createDriverResponseBuilder(OrderEntity order) {
        DriverResponses.DriverResponsesBuilder driverResponseBuilder = DriverResponses.builder();

        if (order.getDriverTaskEntity() != null && order.getDriverTaskEntity().getDriverEntity() != null) {
            driverResponseBuilder.id(order.getDriverTaskEntity().getDriverEntity().getId())
                    .firstName(order.getDriverTaskEntity().getDriverEntity().getFirstName())
                    .lastName(order.getDriverTaskEntity().getDriverEntity().getLastName());
        }

        return driverResponseBuilder;
    }

}
