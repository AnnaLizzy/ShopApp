package com.example.shopapp.responses;

import com.example.shopapp.models.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
