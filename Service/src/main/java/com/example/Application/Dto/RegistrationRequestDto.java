package com.example.Application.Dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {
    private String name;
    private String dateOfBirth;
    private String password;
    private List<Integer> roleIds;
    private List<Integer> catIds;
}
