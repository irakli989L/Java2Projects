package org.example.Dto;

import lombok.Data;

@Data
public class CreateItemRequest {
    private String name;
    private Double price;
    private String description;
    private String photoUrl;
}
