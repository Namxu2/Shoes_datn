package com.fpoly.datn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoleDTO {
    private long id;
    private String name;
    private String description;
    private boolean status;
}
