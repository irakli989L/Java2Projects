package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.WarehouseDto;
import org.example.Entity.Warehouse;
import org.example.Service.WarehouseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public Warehouse create(@RequestBody WarehouseDto dto) {
        return warehouseService.save(dto);
    }
}
