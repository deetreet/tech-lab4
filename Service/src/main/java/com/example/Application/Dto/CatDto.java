package com.example.Application.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CatDto {
    private String name;
    private String dateOfBirth;
    private String breed;
    private Integer colorId;
    private Integer ownerId;
    private List<Integer> friendIds;
}
