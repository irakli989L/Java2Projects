package org.example.Controller;

import org.example.Dto.ItemResponse;
import org.example.Service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private ItemService itemService;

    @Test
    void index_ShouldReturnPagedItems() {
        ItemResponse responseDto = new ItemResponse();
        responseDto.setId(1L);
        responseDto.setName("OnlyMarketFans Item");
        responseDto.setPrice(49.99);

        Page<ItemResponse> mockPage = new PageImpl<>(List.of(responseDto));

        when(itemService.getAllItems(0, "dateDesc")).thenReturn(mockPage);

        String url = "http://localhost:" + port + "/api/items?page=0&sortBy=dateDesc";
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(itemService, times(1)).getAllItems(0, "dateDesc");
    }
}
