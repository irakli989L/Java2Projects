package org.example.Service;

import org.example.Dto.ItemResponse;
import org.example.Entity.Item;
import org.example.Entity.User;
import org.example.Repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getAllItems_Success() {
        Item item = new Item();
        item.setId(10L);
        item.setName("Princess");
        item.setPrice(67.0);

        Page<Item> mockPage = new PageImpl<>(List.of(item));
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<ItemResponse> result = itemService.getAllItems(0, "dateDesc");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Princess", result.getContent().get(0).getName());

        verify(itemRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getItemById_ThrowsException_WhenNotFound() {
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.getItemById(99L);
        });

        assertEquals("Item not found.", exception.getMessage());
        verify(itemRepository, times(1)).findById(99L);
    }

    @Test
    void createItem_Success() {
        Item item = new Item();
        item.setName("LoRa Sensor Node");
        User creator = new User();
        creator.setUsername("ikakoshubitttt");

        when(itemRepository.save(item)).thenReturn(item);

        itemService.createItem(item, creator);

        assertEquals(creator, item.getUser());
        verify(itemRepository, times(1)).save(item);
    }
}
