package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.DTO.ShopDto;
import org.example.Entity.Shop;
import org.example.Service.ShopService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @PostMapping
    public Shop create(@RequestBody ShopDto dto) {
        return shopService.save(dto);
    }
}
