package com.example.shopapp.responses;

import com.example.shopapp.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("category")
    private Category category;
}
