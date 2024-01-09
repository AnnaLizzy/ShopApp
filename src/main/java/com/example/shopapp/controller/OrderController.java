package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.services.IOrderServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderServices orderServices;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                         BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderServices.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_id}")//Lấy danh sách order từ user_id
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> orders = orderServices.getAllOrders(userId);
            return  ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")//Lấy chi tiết 1 order từ order_id
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId){
        try {
            Order existingOrder = orderServices.getOrder(orderId);
            return  ResponseEntity.ok(existingOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // so shy
    @PutMapping("/{id}")
    public  ResponseEntity<?> upadateOrders(
        @Valid @PathVariable long id,
        @Valid @RequestBody OrderDTO orderDTO){
       try {
           Order order = orderServices.updateOrder(id,orderDTO);
           return  ResponseEntity.ok(order);
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Đã xảy ra lỗi : "+e.getMessage());
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrders(
            @Valid @PathVariable Long id
    ){
        // Xóa sẽ cập nhật trường active = false
        orderServices.deleteOrder(id);
        return  ResponseEntity.ok("Delete Success");
    }
}
