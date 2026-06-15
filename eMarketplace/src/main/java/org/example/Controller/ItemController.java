package org.example.Controller;

import lombok.RequiredArgsConstructor;
import org.example.Dto.CreateItemRequest;
import org.example.Dto.ItemResponse;
import org.example.Service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Page<ItemResponse> index(@RequestParam(defaultValue = "0") int page) {
        return itemService.getAll(page);
    }

    @GetMapping("/{id}")
    public ItemResponse itemPage(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @PostMapping
    public ItemResponse create(@RequestBody CreateItemRequest request) {
        return itemService.create(request);
    }
}
