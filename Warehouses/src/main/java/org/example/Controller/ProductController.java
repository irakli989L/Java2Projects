package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.ProductDto;
import org.example.Entity.Product;
import org.example.Service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody ProductDto dto) {
        return productService.save(dto);
    }
}
