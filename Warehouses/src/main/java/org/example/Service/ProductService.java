package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.DTO.ProductDto;
import org.example.Entity.Product;
import org.example.Entity.Shop;
import org.example.Entity.Warehouse;
import org.example.Repository.ProductRepository;
import org.example.Repository.ShopRepository;
import org.example.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ShopRepository shopRepository;

    public Product save(ProductDto dto) {
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElseThrow();

        Shop shop = shopRepository.findById(dto.getShopId()).orElseThrow();

        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setWarehouse(warehouse);
        product.setShop(shop);

        return productRepository.save(product);
    }
}
