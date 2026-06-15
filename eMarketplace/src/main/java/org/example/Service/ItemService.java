package org.example.Service;

import org.example.Dto.CreateItemRequest;
import org.example.Dto.ItemResponse;
import org.springframework.data.domain.Page;

public interface ItemService {
    ItemResponse create(CreateItemRequest request);
    ItemResponse getById(Long id);
    Page<ItemResponse> getAll(int page);
}
