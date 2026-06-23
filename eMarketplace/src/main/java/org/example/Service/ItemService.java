package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.example.Dto.ItemResponse;
import org.example.Entity.Item;
import org.example.Entity.User;
import org.example.Repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<ItemResponse> getAllItems(int page, String sortStrategy) {
        Sort sort = switch (sortStrategy) {
            case "dateAsc" -> Sort.by("submissionTime").ascending();
            case "priceAsc" -> Sort.by("price").ascending();
            case "priceDesc" -> Sort.by("price").descending();
            default -> Sort.by("submissionTime").descending();
        };

        Pageable pageable = PageRequest.of(page, 6, sort);
        return itemRepository.findAll(pageable).map(this::mapToResponse);
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found."));
        return mapToResponse(item);
    }

    public void createItem(Item item, User creator) {
        if (creator != null) {
            item.setUser(creator);
        } else {
            item.setUser(null);
        }
        itemRepository.save(item);
    }

    private ItemResponse mapToResponse(Item item) {
        ItemResponse res = new ItemResponse();
        res.setId(item.getId());
        res.setName(item.getName());
        res.setPrice(item.getPrice());
        res.setDescription(item.getDescription());
        res.setSubmissionTime(item.getSubmissionTime());
        res.setPhotoUrl(item.getPhotoUrl());
        res.setUsername(item.getUser() != null ? item.getUser().getUsername() : "Anonymous");
        return res;
    }
}
