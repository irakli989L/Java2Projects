package org.example.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private LocalDateTime submissionTime;
    private String photoUrl;
    private String username;
}
