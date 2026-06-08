package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.DTO.WarehouseDto;
import org.example.Entity.Warehouse;
import org.example.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Warehouse save(WarehouseDto dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());

        return warehouseRepository.save(warehouse);
    }
}
