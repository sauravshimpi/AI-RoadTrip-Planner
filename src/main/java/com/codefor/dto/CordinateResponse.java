package com.codefor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CordinateResponse {

    private Double longitude;
    private Double latitude;
}