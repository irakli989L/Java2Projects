package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.Dto.CreateItemRequest;
import org.example.Dto.ItemResponse;
import org.example.Entity.Item;
import org.example.Repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;

    @Override
    public ItemResponse create(CreateItemRequest request) {
        Item item = new Item();

        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setDescription(request.getDescription());
        item.setPhotoUrl(request.getPhotoUrl());
        item.setSubmissionTime(LocalDateTime.now());

        repository.save(item);

        return map(item);
    }

    @Override
    public ItemResponse getById(Long id) {

        Item item = repository.findById(id).orElseThrow();

        return map(item);
    }

    @Override
    public Page<ItemResponse> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 6);

        return repository.findAllByOrderBySubmissionTimeDesc(pageable).map(this::map);
    }

    private ItemResponse map(Item item) {
        ItemResponse response = new ItemResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setPrice(item.getPrice());
        response.setDescription(item.getDescription());
        response.setSubmissionTime(item.getSubmissionTime());
        response.setPhotoUrl(item.getPhotoUrl());

        return response;
    }
}
