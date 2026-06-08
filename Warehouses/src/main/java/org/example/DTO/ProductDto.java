package org.example.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private BigDecimal price;
    private Integer quantity;

    private Long warehouseId;
    private Long shopId;
}
