package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.DTO.ShopDto;
import org.example.Entity.Shop;
import org.example.Entity.Warehouse;
import org.example.Repository.ShopRepository;
import org.example.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final WarehouseRepository warehouseRepository;

    public Shop save(ShopDto dto) {
        Shop shop = new Shop();
        shop.setName(dto.getName());

        List<Warehouse> warehouses = warehouseRepository.findAllById(dto.getWarehouseIds());

        shop.setWarehouses(warehouses);

        return shopRepository.save(shop);
    }
}
