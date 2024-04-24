package com.example.Application.Dto;

import com.example.Application.Models.Cat;
import com.example.Application.Models.Owner;
import com.example.Application.Repositories.CatRepository;
import com.example.Application.Repositories.OwnerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OwnerDtoMapper {
    @Autowired
    protected OwnerRepository ownerRepository;
    @Autowired
    protected CatRepository catRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "dateOfBirth", source = "owner.dateOfBirth", dateFormat = "dd-MM-yyyy")
    public abstract OwnerDto ownerToDto(Owner owner);

    @Mapping(target = "cats", source = "ownerDto.catIds")
    @Mapping(target = "dateOfBirth", source = "ownerDto.dateOfBirth", dateFormat = "dd-MM-yyyy")
    public abstract Owner ownerToEntity(OwnerDto ownerDto);

    public Owner mapOwnerId(Integer ownerId) {
        return ownerId != null ? ownerRepository.findById(ownerId).orElse(null) : null;
    }

    public Integer mapOwner(Owner owner) {
        return owner != null ? owner.getOwnerId() : null;
    }

    public List<Cat> mapCatIds(List<Integer> catIds) {
        if (catIds == null)
            return null;

        return catIds.stream()
                .map(id -> {
                    return catRepository.findById(id).orElse(null);
                })
                .collect(Collectors.toList());
    }

    public List<Integer> mapCatEntities(List<Cat> cats) {
        if (cats == null)
            return null;

        return cats.stream()
                .map(Cat::getCatId)
                .collect(Collectors.toList());
    }
}
