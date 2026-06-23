package org.example.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.Dto.ItemResponse;
import org.example.Entity.Item;
import org.example.Entity.User;
import org.example.Service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "dateDesc") String sortBy) {
        return ResponseEntity.ok(itemService.getAllItems(page, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> showItem(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(itemService.getItemById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createItem(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        User user = (User) session.getAttribute("currentUser");

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload an item image file.");
        }

        try (var inputStream = file.getInputStream()) {

            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            File dir = uploadPath.toFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);

            Files.copy(inputStream, filePath);

            String photoUrl = "/uploads/" + filename;

            Item item = new Item();
            item.setName(name);
            item.setPrice(price);
            item.setDescription(description);
            item.setPhotoUrl(photoUrl);

            itemService.createItem(item, user);
            return ResponseEntity.ok().build();

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file on disk.");
        }
    }
}
