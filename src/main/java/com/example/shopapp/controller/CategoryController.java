package com.example.shopapp.controller;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;
import com.example.shopapp.responses.UpdateCategoryResponse;
import com.example.shopapp.services.CategoryServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
//DI
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServices categoryServices;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryServices.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert category success :" + categoryDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
         List<Category> categories = categoryServices.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable Long id
            , @Valid @RequestBody CategoryDTO categoryDTO,
             HttpServletRequest request

    ) {
        categoryServices.updateCategory(id, categoryDTO);
        Locale locale = localeResolver.resolveLocale(request);
        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                        .message(messageSource.getMessage("category.update_category.update_successfully",null,locale))
                .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id) {
        categoryServices.deleteCategory(id);
        return ResponseEntity.ok("Delete category Id :" + id + " success.");
    }

}
//hope after day will be nice
//why im I feel bad to day.
// I want to sleep. sleep all day
// I don't know why I hate my mother-in-law. I'm going to hate and hate her.