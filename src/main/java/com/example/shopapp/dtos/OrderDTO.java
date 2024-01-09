package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonProperty("user_id")
    @Min(value = 1,message = "User Id must be > 0")
    private Long userId;

    @JsonProperty("fullname")
    private  String fullName;

    private  String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number required.")

    private  String phoneNumber;

    private  String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 0,message = "Total money must be > 0")
    private  Float totalMoney;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
