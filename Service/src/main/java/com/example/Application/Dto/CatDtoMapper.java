package com.example.Application.Dto;

import com.example.Application.Models.Cat;
import com.example.Application.Models.Owner;
import com.example.Application.Repositories.CatRepository;
import com.example.Application.Repositories.OwnerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CatDtoMapper {
    @Autowired
    protected CatRepository catRepository;
    @Autowired
    protected OwnerRepository ownerRepository;

    @Mapping(target = "dateOfBirth", source = "cat.dateOfBirth", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "ownerId", source = "cat.owner.ownerId")
    @Mapping(target = "friendIds", source = "cat.friends")
    public abstract CatDto catToDto(Cat cat);

    @Mapping(target = "dateOfBirth", source = "catDto.dateOfBirth", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "owner", source = "catDto.ownerId")
    @Mapping(target = "friends", source = "catDto.friendIds")
    public abstract Cat catToEntity(CatDto catDto);

    public List<Integer> mapFriendEntities(List<Cat> friends) {
        if (friends == null)
            return null;

        return friends.stream()
                .map(Cat::getCatId)
                .collect(Collectors.toList());
    }

    public List<Cat> mapFriendIds(List<Integer> friendIds) {
        if (friendIds == null)
            return null;

        return friendIds.stream()
                .map(id -> {
                    return catRepository.findById(id).orElse(null);
                })
                .collect(Collectors.toList());
    }

    public Owner mapOwnerId(Integer ownerId) {
        return ownerId != null ? ownerRepository.findById(ownerId).orElse(null) : null;
    }

    public Integer mapOwner(Owner owner) {
        return owner != null ? owner.getOwnerId() : null;
    }
}
