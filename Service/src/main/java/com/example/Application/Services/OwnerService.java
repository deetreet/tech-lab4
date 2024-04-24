package com.example.Application.Services;

import com.example.Application.Dto.OwnerDto;
import com.example.Application.Dto.OwnerDtoMapper;
import com.example.Application.Dto.RegistrationRequestDto;
import com.example.Application.Models.Owner;
import com.example.Application.Repositories.CatRepository;
import com.example.Application.Repositories.OwnerRepository;
import com.example.Application.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class realizing the service to interact with owner entities
 */
@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerDtoMapper ownerDtoMapper;
    private final CatRepository catRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to add any role user to the database
     *
     * @param registrationRequestDto owner to be added
     */
    public void addSomeUser(RegistrationRequestDto registrationRequestDto) throws Exception {
        try {
            Owner ownerFromDB = ownerRepository.findOwnerByName(registrationRequestDto.getName());

            if (ownerFromDB != null) {
                throw new RuntimeException("User already exists");
            }

            Owner owner = new Owner();
            owner.setName(registrationRequestDto.getName());
            owner.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(registrationRequestDto.getDateOfBirth()));
            owner.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));

            owner.setRoles(registrationRequestDto.getRoleIds().stream()
                    .map(id -> {
                        return roleRepository.findById(id).orElse(null);
                    })
                    .collect(Collectors.toList())
            );

            owner.setCats(registrationRequestDto.getCatIds().stream()
                    .map(id -> {
                        return catRepository.findById(id).orElse(null);
                    })
                    .collect(Collectors.toList())
            );

            ownerRepository.save(owner);
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Method to add an owner to the database
     *
     * @param ownerDto owner to be added
     */
    public void addOwner(OwnerDto ownerDto) {
        Owner ownerFromDB = ownerRepository.findOwnerByName(ownerDto.getName());

        if (ownerFromDB != null) {
            throw new RuntimeException("User already exists");
        }

        Owner owner = ownerDtoMapper.ownerToEntity(ownerDto);
        owner.setRoles(List.of(roleRepository.findById(2).orElse(null)));
        owner.setPassword(passwordEncoder.encode(ownerDto.getPassword()));
        ownerRepository.save(owner);
    }

    /**
     * Method to delete an owner from the database
     *
     * @param ownerId id of owner to be deleted
     */
    public void deleteOwner(Integer ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();
        ownerRepository.delete(owner);
    }

    /**
     * Method to get an owner by its id
     *
     * @param ownerId id of owner to be found
     * @return Owner with the given id
     */
    public OwnerDto getOwnerById(Integer ownerId) {
        OwnerDto ownerDto = ownerDtoMapper.ownerToDto(ownerRepository.findById(ownerId).orElseThrow());
        return ownerDto;
    }

    /**
     * Method to get an owner by its name
     *
     * @param name name of owner to be found
     * @return Owner with the given name
     */
    public OwnerDto getOwnerByName(String name) {
        OwnerDto ownerDto = ownerDtoMapper.ownerToDto(ownerRepository.findOwnerByName(name));
        return ownerDto;
    }

    /**
     * Method to check if user manipulates his own entity
     *
     * @param username name of user
     * @param ownerId id of user
     * @return true if user manipulates his own entity, false otherwise
     */
    public boolean isUserItself(String username, Integer ownerId) {
        Owner owner = ownerRepository.findOwnerByName(username);
        return owner.getOwnerId().equals(ownerId);
    }
}
