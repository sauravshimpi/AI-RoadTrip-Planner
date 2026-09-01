package com.codefor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse {

    private String name;

    private String category;

    private Double longitude;

    private Double latitude;
}