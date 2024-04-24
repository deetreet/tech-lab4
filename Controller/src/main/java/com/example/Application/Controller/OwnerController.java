package com.example.Application.Controller;

import com.example.Application.Dto.OwnerDto;
import com.example.Application.Dto.RegistrationRequestDto;
import com.example.Application.Services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to interact with owners
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Method to add any role user to the database
     *
     * @param registrationRequestDto owner to be added
     */
    @PostMapping("/addSomeUser")
    public void addSomeUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        try {
            ownerService.addSomeUser(registrationRequestDto);
        } catch (Exception exception) {
        }
    }

    /**
     * Method to add an owner to the database
     *
     * @param ownerDto owner to be added
     */
    @PostMapping("/registerUser")
    public void addOwner(@RequestBody OwnerDto ownerDto) {
            ownerService.addOwner(ownerDto);
    }

    /**
     * Method to delete an owner from the database
     *
     * @param ownerId id of owner to be deleted
     */
    @DeleteMapping("/delete/{ownerId}")
    @PreAuthorize("hasRole('ADMIN') or @ownerService.isUserItself(authentication.principal.username, #ownerId)")
    public void deleteOwner(@PathVariable Integer ownerId) {
        ownerService.deleteOwner(ownerId);
    }

    /**
     * Method to get an owner by its id
     *
     * @param ownerId id of owner to be found
     * @return Owner with the given id
     */
    @GetMapping("/get/{ownerId}")
    @PreAuthorize("hasRole('ADMIN') or @ownerService.isUserItself(authentication.principal.username, #ownerId)")
    public @ResponseBody OwnerDto getOwnerById(@PathVariable Integer ownerId) {
        return ownerService.getOwnerById(ownerId);
    }
}
