package com.example.Application.Controller;

import com.example.Application.Common.CatColors;
import com.example.Application.Dto.CatDto;
import com.example.Application.Services.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to interact with cats
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cat")
public class CatController {

    private final CatService catService;

    /**
     * Method to add a cat to the database
     *
     * @param cat cat to be added
     */
    @PostMapping("/add")
    public void addCat(@RequestBody CatDto cat) {
        catService.addCat(cat);
    }

    /**
     * Method to delete a cat from the database
     *
     * @param catId id of cat to be deleted
     */
    @DeleteMapping("/delete/{catId}")
    @PreAuthorize("hasRole('ADMIN') or @catService.isUserOwnerOfCat(authentication.principal.username, #catId)")
    public void deleteCat(@PathVariable Integer catId) {
        catService.deleteCat(catId);
    }

    /**
     * Method to change the name of a cat
     *
     * @param catId   id of cat to be changed
     * @param newName new name of the cat
     */
    @PutMapping("/changeName/{catId}")
    @PreAuthorize("hasRole('ADMIN') or @catService.isUserOwnerOfCat(authentication.principal.username, #catId)")
    public void changeCatsName(@PathVariable Integer catId, @RequestParam String newName) {
        catService.changeCatsName(catId, newName);
    }

    /**
     * Method to get a cat by its id
     *
     * @param catId id of cat to be found
     * @return Cat with the given id
     */
    @GetMapping("/get/{catId}")
    @PreAuthorize("hasRole('ADMIN') or @catService.isUserOwnerOfCat(authentication.principal.username, #catId)")
    public CatDto getCatById(@PathVariable Integer catId) {
        return catService.getCatById(catId);
    }

    /**
     * Method to get the friends of a cat
     *
     * @param catId id of cat to get friends of
     * @return List of friends of the cat
     */
    @GetMapping("/friends/get/{catId}")
    @PreAuthorize("hasRole('ADMIN') or @catService.isUserOwnerOfCat(authentication.principal.username, #catId)")
    public List<CatDto> getCatsFriends(@PathVariable Integer catId) {
        return catService.getCatsFriends(catId);
    }

    /**
     * Method to make a new friendship between two cats
     *
     * @param firstCatId  id of the first cat
     * @param secondCatId id of the second cat
     */
    @PostMapping("/makeFriendship")
    @PreAuthorize("hasRole('ADMIN') or @catService.isUserOwnerOfCat(authentication.principal.username, #firstCatId)")
    public void makeNewFriendship(@RequestParam Integer firstCatId, @RequestParam Integer secondCatId) {
        catService.makeNewFriendship(firstCatId, secondCatId);
    }

    /**
     * Method to get cats by color
     *
     * @param color color of cats to be found
     * @return List of cats with the given color
     */
    @GetMapping("/get/color/{color}")
    public List<CatDto> getCatsByColor(@PathVariable String color) {
        CatColors catColor = CatColors.valueOf(color.toUpperCase());
        return catService.getCatsByColorId(catColor.ordinal());
    }
}
