package com.fpoly.datn.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateMaterialRequest {
    @NotBlank(message = "Tên chất liệu trống")
    @Size(max = 20, message = "Tên chất liệu có độ dài tối đa 20 ký tự")
    private String name;
    private Long id;

    private String description;
    private boolean status;
}