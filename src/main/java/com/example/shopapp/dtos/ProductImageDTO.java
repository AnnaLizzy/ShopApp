package com.example.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1,message = "Product Id must be greater than zero")
    private Long productId;

    @Size(min = 5,max = 200,message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
