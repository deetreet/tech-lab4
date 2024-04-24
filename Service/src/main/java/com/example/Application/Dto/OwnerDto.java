package com.example.Application.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OwnerDto {
    private String name;
    private String dateOfBirth;
    private String password;
    private List<Integer> catIds;
}
