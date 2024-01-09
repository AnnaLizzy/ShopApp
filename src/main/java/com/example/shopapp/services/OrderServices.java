package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderStatus;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServices implements IOrderServices{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //userid có tồn tại ko
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Không tìm thấy user với id : "+ orderDTO.getUserId()) );
        //convert orderDTO => Order & dùng thư viện Model Mapper
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        //check shipping date >= ngay hom nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if ( shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Ngày muộn nhất phải là hôm nay!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }


    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO)
            throws DataNotFoundException
    {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this order with id = "+id));
        User existtingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user with id = "+id));
        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper ->mapper.skip(Order::setId));

        modelMapper.map(orderDTO,order);
        order.setUser(existtingUser);
        return orderRepository.save(order);

    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        //no hard delete -> soft delete
        if (order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
