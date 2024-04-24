package com.example.Application.Services;

import com.example.Application.Dto.CatDto;
import com.example.Application.Dto.CatDtoMapper;
import com.example.Application.Exceptions.MyException;
import com.example.Application.Models.Cat;
import com.example.Application.Models.Owner;
import com.example.Application.Repositories.CatRepository;
import com.example.Application.Repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class realizing the service to interact with cat entities
 */
@RequiredArgsConstructor
@Service
public class CatService {
    private final CatRepository catRepository;
    private final CatDtoMapper catDtoMapper;
    private final OwnerRepository ownerRepository;

    /**
     * Method to add a cat to the database
     *
     * @param catDto cat to be added
     */
    public void addCat(CatDto catDto) {
        if (catDto.getName() == null || catDto.getName().isEmpty()) {
            throw new MyException("Name cannot be empty");
        } else if (catDto.getBreed() == null) {
            throw new MyException("Breed cannot be empty");
        } else if (catDto.getOwnerId() == null) {
            throw new MyException("Owner cannot be empty");
        } else if (catDto.getDateOfBirth() == null) {
            throw new MyException("Date of birth cannot be empty");
        }

        Cat cat = catDtoMapper.catToEntity(catDto);
        catRepository.save(cat);
    }

    /**
     * Method to delete a cat from the database
     *
     * @param catId id of cat to be deleted
     */
    public void deleteCat(Integer catId) {
        Cat cat = catRepository.findById(catId).orElseThrow();
        catRepository.delete(cat);
    }

    /**
     * Method to change the name of a cat
     *
     * @param catId   id of cat to be changed
     * @param newName new name of the cat
     */
    public void changeCatsName(Integer catId, String newName) {
        if (newName == null || newName.isEmpty()) {
            throw new MyException("New name cannot be empty");
        }
        Cat cat = catRepository.findById(catId).orElseThrow();
        cat.setName(newName);
        catRepository.save(cat);
    }

    /**
     * Method to get a cat by its id
     *
     * @param catId id of cat to be found
     * @return Cat with the given id
     */
    public CatDto getCatById(Integer catId) {
        CatDto catDto = catDtoMapper.catToDto(catRepository.findById(catId).orElseThrow());
        return catDto;
    }

    /**
     * Method to get all friends of a cat
     *
     * @param catId id of cat whose friends are to be found
     * @return List of friends of the cat
     */
    public List<CatDto> getCatsFriends(Integer catId) {
        Cat cat = catRepository.findById(catId).orElseThrow();
        List<CatDto> friends = cat
                .getFriends()
                .stream()
                .map(friend -> catDtoMapper.catToDto(friend))
                .toList();

        return friends;
    }

    /**
     * Method to make a new friendship between two cats
     *
     * @param firstCatId  id of the first cat
     * @param secondCatId id of the second cat
     */
    public void makeNewFriendship(Integer firstCatId, Integer secondCatId) {
        Cat firstCat = catRepository.findById(firstCatId).orElseThrow();
        Cat secondCat = catRepository.findById(secondCatId).orElseThrow();
        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }

    public List<CatDto> getCatsByColorId(Integer colorId) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CatDto> catDtos = catRepository.findCatsByColorId(colorId)
                .stream()
                .map(cat -> catDtoMapper.catToDto(cat))
                .toList();

        return catDtos.stream()
                .filter(catDto -> {
                    Optional<Owner> ownerOptional = ownerRepository.findById(catDto.getOwnerId());
                    return ownerOptional.isPresent() && ownerOptional.get().getName().equals(currentUser);
                })
                .collect(Collectors.toList());
    }

    public List<CatDto> getCatsByName(String name) {
        List<CatDto> catDtos = catRepository.findCatsByName(name)
                .stream()
                .map(cat -> catDtoMapper.catToDto(cat))
                .toList();

        return catDtos;
    }

    /**
     * Method to check if user is owner of cat
     *
     * @param username name of user
     * @param catId id of cat
     * @return true if user is owner of cat, false otherwise
     */
    public boolean isUserOwnerOfCat(String username, Integer catId) {
        Owner owner = ownerRepository.findOwnerByName(username);
        return owner.getCats().stream().anyMatch(cat -> cat.getCatId().equals(catId));
    }
}
