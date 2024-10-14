package com.fpoly.datn.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSoleRequest {
    @NotBlank(message = "Tên đế giày trống!")
    @Size(max = 50, message = "Tên đế giày có độ dài tối đa 50 ký tự!")
    private String name;
    private long id;
    private String description;
    private boolean status;
}
