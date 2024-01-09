package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.responses.OrderDetailResponse;
import com.example.shopapp.services.OrderDetailServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@Validated
@RequiredArgsConstructor
public class OderDetailController  {
    private final OrderDetailServices orderDetailServices;
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO )
    {
        try {
            OrderDetail newOrderDetail = orderDetailServices.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public  ResponseEntity<?> getOrderDetail (@Valid @PathVariable("id")  Long id)
            throws DataNotFoundException
    {
         OrderDetail orderDetail = orderDetailServices.getOrderDetail(id);
        return ResponseEntity.ok(orderDetail);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId)
    {
         List<OrderDetail> orderDetails = orderDetailServices.findByOrderId(orderId);
         List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                 .map(OrderDetailResponse::fromOrderDetail)
                 .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateOrderDetails(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ){
        try {
             OrderDetail orderDetail = orderDetailServices.updateOrderDetail(id,orderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteOrderDetails(@Valid @PathVariable("id") Long id){
        orderDetailServices.deleteById(id);
        return ResponseEntity.ok().body("Delete Order detail with id: "+ id+" success.");
    }
}
