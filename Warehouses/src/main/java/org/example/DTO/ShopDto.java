package org.example.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShopDto {
    private String name;

    private List<Long> warehouseIds;
}
